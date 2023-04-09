package dices;

public class PoisonDice extends Dice{

	public PoisonDice(int maxValue) {
		super(maxValue);
	}
	public PoisonDice() {
		super(2);
		name = "Dé de Poison";
	}
	
}
