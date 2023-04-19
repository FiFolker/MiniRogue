package effect;

import main.Game;

public class Weaken extends Effect {

	public Weaken(Game game, String name, String info) {
		super(game, "Affaiblir", "Perdez 1 XP. Au besoin, perte de dé Aventurier");
	}

	@Override
	public void applyEffect() {
		game.selectedClass.substractStat(game.selectedClass.xpString, 1);
	}
	
}
