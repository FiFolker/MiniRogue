package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;

public class TrapCard extends Card{

    public TrapCard(Game game, BufferedImage image, Rectangle hitbox, int x, int y) {
        super(game, image, hitbox, x, y);
        name = "Carte Piège";
    }
    
}
