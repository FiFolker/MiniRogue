package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Coordonnees;
import main.Game;
import main.Utils;

public class BurialCard extends Card{

    public BurialCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
        image = Utils.loadImage("assets/cards/cardGray.png");
        name = "Carte Sépulture";

        //TODO Auto-generated constructor stub
    }
    
}
