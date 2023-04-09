package dices;

public class CurseDice extends Dice{

	public CurseDice(int maxValue) {
		super(maxValue);
	}

	public CurseDice() {
		super(2);
		name = "Dé de Malédiction";
	}
	
}
