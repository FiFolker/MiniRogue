package potions;

import java.awt.image.BufferedImage;

import main.Game;
import main.Utils;

public class LifePotion extends Potion {

    public LifePotion(Game game) {
        super(game, Utils.loadImage("assets/potions/lifePotion.png"));
        info = "Vie +6 PV";
        name = "Potion de Vie";
        effectValue = 6;
        addButtton();
    }

    @Override
    public void applyEffect() {
        game.selectedClass.addStat(game.selectedClass.lifeString, effectValue);
        game.selectedClass.removePotion(this);
    }
    
}
