package dices;

import java.awt.Color;

public class CharacterDice extends Dice{

	public CharacterDice(int maxValue) {
		super(maxValue);
	}

	public CharacterDice() {
        super(6);
		color = Color.white;
		name = "Dé Personnage";
    }

	public boolean testSkill(){
		return value >= 5;
	}
	
}
