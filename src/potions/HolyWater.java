package potions;


import main.Game;
import main.Utils;

public class HolyWater extends Potion {

	public HolyWater(Game game) {
		super(game, Utils.loadImage("assets/potions/holyWater.png"));
		info = "Eau Bénite soigne vos afflictions";
		name = "Eau Bénite";
		addButtton();
	}

	@Override
	public void applyEffect() {
		game.curseDice = null;
		game.poisonDice = null;
		game.selectedClass.removePotion(this);
	}
	
}
