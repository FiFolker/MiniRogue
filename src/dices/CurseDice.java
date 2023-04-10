package dices;

import main.Game;

public class CurseDice extends Dice{

	Game game;
	public boolean hasBeenApplied = false;

	public CurseDice(int maxValue, Game game) {
		super(maxValue);
		this.game = game;
	}

	public CurseDice(Game game) {
		super(2);
		this.game = game;
		name = "Dé de Malédiction";
	}

	@Override
	public void roll() {
		super.roll();
		hasBeenApplied = false;
	}

	public void curseEffect(){
		if(!hasBeenApplied){
			for(CharacterDice d : game.characterDices){
				d.value --;
			}
			hasBeenApplied = true;
		}
		
	}

	
}
