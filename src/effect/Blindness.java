package effect;

import main.Game;

public class Blindness extends Effect{

    public Blindness(Game game, String name, String info) {
        super(game, "Aveuglement", "Vous ne pouvez pas réveler les salles suivantes avant de les avoir choisies.");
    }

    @Override
    public void applyEffect() {
        game.selectedClass.blindness = true;
    }
    
}
