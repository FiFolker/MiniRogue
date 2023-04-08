package classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import main.Dice;
import main.Game;
import main.Utils;

public class Classe {
	
	Game game;
	BufferedImage icon;
	String name;
	String info;
	public int xp = 0;
	public int xpRequired = 6;
	public int level = 0;
	public int size = 128;
	public HashMap<String, Integer> stats;

	public Classe(Game game, BufferedImage icon, String name, int life, int food, int armor, int money) {
		this.game = game;
		this.icon = icon;
		this.name = name;
		stats = new HashMap<>();
		stats.put("Vie", life);
		stats.put("Nourriture", food);
		stats.put("Armure", armor);
		stats.put("Argent", money);
		stats.put("Niveau", level);
		stats.put("XP", xp);
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
		if(xp == xpRequired){
			game.dices.add(new Dice(6));
			level ++;
			xpRequired += xpRequired*2;
		}
	}

}
