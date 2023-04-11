package dices;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.Utils;

public class Dice {

	public int maxValue;
	Random rand = new Random();
	public int value;
	public String name = "dé";
	Color color;

	public Dice(int maxValue, String name){
		this.maxValue = maxValue;
		this.name = name;
	}
	
	public Dice(int maxValue){
		this.maxValue = maxValue;
	}

	public void roll(int nTimes){
		for(int i=0;i<nTimes;i++){
			value += rand.nextInt(maxValue);
		}
	}

	public void roll(){
		value = Utils.randomNumber(1, maxValue);
	}

	public void draw(Graphics2D g2, int x, int y){
		g2.setColor(Color.white);
		g2.drawString(name, x, y);
		Utils.drawDice(g2, x+(int)Utils.textToRectangle2D(name, g2).getWidth()/2, y+10, value, color);
	}
	
}
