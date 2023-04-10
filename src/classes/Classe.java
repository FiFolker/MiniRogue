package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import dices.CharacterDice;
import dices.Dice;
import main.Game;
import main.Utils;

public class Classe {
	
	Game game;
	public BufferedImage icon;
	String name;
	String info;
	public int xpRequired = 6;
	public int size = 128;
	public HashMap<String, Integer> stats;
	public HashMap<String, Integer> maxStats;
	public String lifeString = "Vie";
	public String foodString = "Nourriture";
	public String armorString = "Armure";
	public String moneyString = "Argent";
	public String levelString = "Niveau";
	public String xpString = "XP";

	// TABLEAU DE 2 POTIONS et 1 OBJET ici et 2 SPELL

	public Classe(Game game, BufferedImage icon, String name, int life, int food, int armor, int money) {
		this.game = game;
		this.icon = icon;
		this.name = name;
		stats = new HashMap<>();
		maxStats = new HashMap<>();
		maxStats.put(this.lifeString, 20);
		maxStats.put(this.foodString, 4);
		maxStats.put(this.armorString, 4);
		maxStats.put(this.moneyString, 10);
		maxStats.put(this.xpString, 23);
		maxStats.put(this.levelString, 3);

		stats.put(this.lifeString, life);
		stats.put(this.foodString, food);
		stats.put(this.armorString, armor);
		stats.put(this.moneyString, money);
		stats.put(this.levelString, 0);
		stats.put(this.xpString, 0);
		info = "Vie : " + life + " | Nourriture : " + food + " | Armure : " + armor + " | Argent : " + money; 
	} 

	public void drawIcon(Graphics2D g2, int x, int y){
		g2.drawImage(icon, x, y, size, size, null);
		g2.drawString(info, (int)Utils.getXforCenteredText(info, g2), y+size+20);
		g2.drawString(name, (int)Utils.getXforCenteredText(name, g2), y-10);
	}

	public void drawCard(Graphics2D g2, int x, int y){
		g2.drawImage(icon, x, y, size, size, null);
		g2.setColor(Color.white);
		g2.drawRect(x, y, size, size);
		g2.drawString(name, x+size+10, y+size/2);
	}

	public void update(){
		if(this.stats.get(xpString) == xpRequired && this.stats.get(levelString) < this.maxStats.get(levelString)){
			game.characterDices.add(new CharacterDice());
			this.addStat(levelString, 1);
			xpRequired += xpRequired*2;
		}else if(this.stats.get(xpString) >= this.maxStats.get(xpString)){
			this.stats.replace(xpString, this.maxStats.get(xpString));
			xpRequired = 23;
			this.addStat(lifeString, 1);
		}
		if(this.stats.get(lifeString) <= 0){
			game.gameState = game.loseState;
		}
	}

	public void addStat(String key, int value){
		while(this.stats.get(key) < this.maxStats.get(key) && value > 0){
			this.stats.replace(key, this.stats.get(key)+1);
			value --;
		}
	}

	public void damageReceived(int damage, boolean armor){
		if(armor){
			while(this.stats.get(armorString) > 0 && damage > 0){
				substractStat(armorString, 1);
				damage --;
			}
		}
		substractStat(lifeString, damage);
		
	}

	public void substractStat(String key, int value){
		while(this.stats.get(key) > 0 && value > 0){
			this.stats.replace(key, this.stats.get(key)-1);
			value --;
		}
	}

}
