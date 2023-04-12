package dices;

import java.awt.Color;

import main.Game;

public class PoisonDice extends Dice{

	Game game;
	public boolean hasBeenApplied = false;

	public PoisonDice(int maxValue, Game game) {
		super(maxValue);
		this.game = game;
	}
	public PoisonDice(Game game) {
		super(2);
		this.game = game;
		color = Color.green;
		name = "Poison";
	}
	
	@Override
	public void roll() {
		super.roll();
		hasBeenApplied = false;
	}

	public void poisonEffect(){
		if(!hasBeenApplied){
			game.selectedClass.substractStat(game.selectedClass.lifeString, 1);
			hasBeenApplied = true;
		}
		
	}

}
