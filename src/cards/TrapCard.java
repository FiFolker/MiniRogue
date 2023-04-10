package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Coordonnees;
import main.Game;
import main.Utils;

public class TrapCard extends Card{

    public TrapCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
        name = "Carte Piège";
        image = Utils.loadImage("assets/cards/cardBlue.png");

    }
    
}
