package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import dices.CharacterDice;
import main.Button;
import main.ErrorDraw;
import main.Game;
import main.Utils;
import potions.HolyWater;
import potions.Potion;

public class Classe {
	
	Game game;
	public BufferedImage icon;
	String name;
	String info;
	public int damage = 0;
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
	public String damageString = "damage";
	public boolean replacePotionBox = false;
	Potion replacePotion;
	public ArrayList<Potion> potions = new ArrayList<>();
	String errorString;
	int timer = 0;
	public String playerAttack = "";
	ErrorDraw errorDraw = new ErrorDraw();

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
		g2.drawString(info, Utils.getXforCenteredText(info, g2), y+size+20);
		g2.drawString(name, Utils.getXforCenteredText(name, g2), y-10);
	}

	public void drawCard(Graphics2D g2, int x, int y){
		g2.drawImage(icon, x, y, size, size, null);
		g2.setColor(Color.white);
		g2.drawRect(x, y, size, size);
		g2.drawString(name, x+size+10, y+size/2);
	}

	public void update(){
		if(this.stats.get(xpString) >= xpRequired  && this.stats.get(xpString) < this.maxStats.get(xpString) && this.stats.get(levelString) < this.maxStats.get(levelString)){
			game.characterDices.add(new CharacterDice());
			this.addStat(levelString, 1);
			xpRequired += xpRequired*2;
		}
		if(xpRequired >= this.maxStats.get(xpString)){
			xpRequired = 23;
		}
		if(this.stats.get(xpString) >= this.maxStats.get(xpString)){
			this.stats.replace(xpString, this.maxStats.get(xpString));
			xpRequired = 23;
			this.addStat(lifeString, 1);
		}
		if(this.stats.get(lifeString) <= 0){
			game.gameState = game.loseState;
		}
		int i = 0;
		while(i < potions.size() && !potions.isEmpty() ){
			potions.get(i).update();
			i++;
		}
	}

	public void addStat(String key, int value){
		while(this.stats.get(key) < this.maxStats.get(key) && value > 0){
			this.stats.replace(key, this.stats.get(key)+1);
			value --;
		}
	}

	public void damageReceived(int damage, boolean pierceTheArmor){
		if(!pierceTheArmor){
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

	public void draw(Graphics2D g2){
		int i = 0;
		while(i < potions.size() && !potions.isEmpty() ){
			potions.get(i).draw(g2);
			i++;
		}
		if(errorDraw.errorState){
			errorDraw.draw(g2, errorString, game);
		}

		if(replacePotionBox){
			replacePotionBoxChoice(g2, replacePotion);
		}
		
	}

	public void replacePotionBoxChoice(Graphics2D g2, Potion potion){
		Rectangle box = new Rectangle((game.getWidth() - game.gui.xLine)/2-50, game.getHeight()/2-150, 400, 200);
			g2.draw(box);
			g2.setColor(Color.black);
			g2.fillRect(box.x+1, box.y+1, box.width-1, box.height-1);
			g2.setColor(Color.white);
			g2.drawString("Choisissez la Potion à jeter", box.x + box.width/2 -(int)Utils.textToRectangle2D("Choisissez la Potion à jeter", g2).getWidth()/2, box.y + 20);

			g2.drawString("Nouvelle Potion : ", box.x + box.width/2 -(int)Utils.textToRectangle2D("Nouvelle Potion : ", g2).getWidth()/2, box.y + 40);
			g2.drawImage(potion.icon, box.x + box.width/2 - potion.potionButton.button.width/2, box.y + box.height/2 - potion.potionButton.button.height - 20, null);
			g2.drawString("Potions actuel : ", box.x + box.width/2 -(int)Utils.textToRectangle2D("Potions actuel : ", g2).getWidth()/2, box.y + 110);

			Button firstPotion = new Button(new Rectangle(box.x + box.width/2 - potions.get(0).potionButton.button.width - 5, box.y + box.height/2 + 20, potions.get(0).potionButton.button.width, potions.get(0).potionButton.button.height), potions.get(0).icon);
			Button secondPotion = new Button(new Rectangle(box.x + box.width/2 + 5, box.y + box.height/2+ 20, potions.get(potions.size()-1).potionButton.button.width, potions.get(potions.size()-1).potionButton.button.height), potions.get(potions.size()-1).icon);

			Button cancelButton = new Button(new Rectangle(box.x + box.width/2 - 50, box.y + box.height - 35, 100, 25), "Annuler");

			firstPotion.draw(g2);
			secondPotion.draw(g2);
			cancelButton.draw(g2);

			if(firstPotion.isClicked()){
				removePotion(potions.get(0));
				potion.potionButton.button.x = potion.position[0];
				addPotion(potion);
				replacePotionBox = false;
			}else if(secondPotion.isClicked()){
				removePotion(potions.get(1));
				potion.potionButton.button.x = potion.position[1];
				addPotion(potion);
				replacePotionBox = false;
			}else if(cancelButton.isClicked()){
				replacePotionBox = false;
			}
	}


	public void addPotion(Potion potion) {
		if(potions.size() < 2){
			if(!potions.isEmpty()){
				if(potions.get(potions.size()-1).getClass() != potion.getClass()){
					potions.add(potion);
				}else{
					errorString = "Vous ne pouvez posséder qu'une seule potion par type !";
				}
			}else{
				potions.add(potion);
			}
			potions.get(potions.size()-1).potionButton.button.x = potions.get(potions.size()-1).position[potions.size()-1];
		}else if(potions.size() == 2 && potions.get(0).getClass() != potion.getClass() && potions.get(1).getClass() != potion.getClass()){
			replacePotion = potion;
			replacePotionBox = true;
		}
    }

	public void removePotion(Potion potion){
		Potion.number --;
		int i = 0;
		if(!potions.contains(potion)){
			errorString = "Vous ne possédez pas " + potion.name + " !";
			errorDraw.errorState = true;
		}else{
			while(i < potions.size() && !potions.isEmpty() && potions.get(i) != potion){
				i++;
			}
			if(potions.get(i) == potion){
				potions.remove(potion);
			}
			if(i == 0 && !potions.isEmpty() && potions.get(0) != null){
				potions.get(0).potionButton.button.x = potion.position[0];
			}
		}
		
	}

}
