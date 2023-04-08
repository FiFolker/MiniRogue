package main;

import java.util.Random;

public class Dice {

	int maxValue;
	Random rand = new Random();

	public Dice(int maxValue){
		this.maxValue = maxValue;
	}

	public int roll(int nTimes){
		int total = 0;
		for(int i=0;i<nTimes;i++){
			total += roll();
		}
		return total;
	}

	public int roll(){
		return rand.nextInt(maxValue);
	}

	
	
}
