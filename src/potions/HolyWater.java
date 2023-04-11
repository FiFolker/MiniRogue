package potions;


import main.Game;
import main.Utils;

public class HolyWater extends Potion {

	public HolyWater(Game game) {
		super(game, Utils.loadImage("assets/potions/holyWater.png"));
		name = "Eau Bénite soigne vos afflictions";
		addButtton();
	}

	@Override
	public void applyEffect() {
		game.curseDice = null;
		game.poisonDice = null;
		Potion.removePotion(this);
	}
	
}
