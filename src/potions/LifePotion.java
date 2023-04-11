package potions;

import java.awt.image.BufferedImage;

import main.Game;
import main.Utils;

public class LifePotion extends Potion {

    public LifePotion(Game game) {
        super(game, Utils.loadImage("assets/potions/lifePotion.png"));
        name = "Vie +6 PV";
        effectValue = 6;
        addButtton();
    }

    @Override
    public void applyEffect() {
        game.selectedClass.addStat(game.selectedClass.lifeString, effectValue);
        Potion.removePotion(this);
    }
    
}
