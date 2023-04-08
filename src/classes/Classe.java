package classes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Utils;

public class Classe {
	
	BufferedImage icon;
	String name;
	int life, food, armor, money;
	String info;
	public int size = 128;

	public Classe(BufferedImage icon, String name, int life, int food, int armor, int money) {
		this.icon = icon;
		this.name = name;
		this.life = life;
		this.food = food;
		this.armor = armor;
		this.money = money;
		info = "Vie : " + life + " | Nourriture : " + food + " | Armure : " + armor + " | Argent : " + money; 
	} 

	public void drawIcon(Graphics2D g2, int x, int y){
		g2.drawImage(icon, x, y, size, size, null);
		g2.drawString(info, (int)Utils.getXforCenteredText(info, g2), y+size+20);
		g2.drawString(name, (int)Utils.getXforCenteredText(name, g2), y-10);
	}

}
