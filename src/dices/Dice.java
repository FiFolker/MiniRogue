package dices;

import java.awt.Graphics2D;
import java.util.Random;

import main.Utils;

public class Dice {

	public int maxValue;
	Random rand = new Random();
	public int value;
	public String name = "dé";

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
		value = rand.nextInt(maxValue-1)+1;
	}

	public void draw(Graphics2D g2, int x, int y){
		g2.drawString(name, x, y);
		g2.drawString(Integer.toString(value), x + (int)Utils.textToRectangle2D(name, g2).getWidth()/2, y+(int)Utils.textToRectangle2D(name, g2).getHeight());

	}

	
	
}
