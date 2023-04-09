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
	BufferedImage icon;
	String name;
	String info;
	public int xpRequired = 6;
	public int size = 128;
	public HashMap<String, Integer> stats;
	public String lifeString = "Vie";
	public String foodString = "Nourriture";
	public String armorString = "Armure";
	public String moneyString = "Argent";
	public String levelString = "Niveau";
	public String xpString = "XP";

	public Classe(Game game, BufferedImage icon, String name, int life, int food, int armor, int money) {
		this.game = game;
		this.icon = icon;
		this.name = name;
		stats = new HashMap<>();
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
		if(this.stats.get(xpString) == xpRequired){
			game.dices.add(new CharacterDice());
			this.addStat(levelString, 1);
			xpRequired += xpRequired*2;
		}
	}

	public void addStat(String key, int value){
		this.stats.replace(key, this.stats.get(key)+value);
	}

}
