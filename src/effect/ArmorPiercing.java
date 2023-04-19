package effect;

import ennemy.Ennemy;
import main.Game;

public class ArmorPiercing extends Effect {

    Ennemy ennemy;

    public ArmorPiercing(Game game, Ennemy ennemy) {
        super(game, "Inarêtable", "Outrepasse l'armrure au moment de l'attaque");
        this.ennemy = ennemy;
    }

    @Override
    public void applyEffect() {
        ennemy.pierceTheArmor= true;
    }
    
}
