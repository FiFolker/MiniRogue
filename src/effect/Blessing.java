package effect;

import main.Game;

public class Blessing extends Effect{

    public Blessing(Game game) {
        super(game, "Bénédiction", "Soigne les afflictions");
        
    }

    @Override
    public void applyEffect() {
        game.curseDice = null;
        game.poisonDice = null;
    }
    
}
