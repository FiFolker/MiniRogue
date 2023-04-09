package dices;

public class CharacterDice extends Dice{

	public CharacterDice(int maxValue) {
		super(maxValue);
	}

	public CharacterDice() {
        super(6);
		name = "Dé Personnage";
    }

	public boolean testSkill(){
		return value >= 5;
	}
	
}
