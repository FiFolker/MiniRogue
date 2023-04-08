package classes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Classe {
	
	BufferedImage icon;
	String name;
	int life, food, armor, money;
	String info;

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
		g2.drawImage(icon, x, y, null);
		g2.drawString(info, x, y+icon.getHeight());
	}

}
