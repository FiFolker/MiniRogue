package main;

import java.awt.Graphics2D;
import java.util.Random;

public class Dice {

	int maxValue;
	Random rand = new Random();
	int value;

	public Dice(int maxValue){
		this.maxValue = maxValue;
	}

	public void roll(int nTimes){
		for(int i=0;i<nTimes;i++){
			value += rand.nextInt(maxValue);
		}
	}

	public void roll(){
		value = rand.nextInt(maxValue);
	}

	public void draw(Graphics2D g2, int x, int y){
		g2.drawString(Integer.toString(value), x, y);
	}

	
	
}
