package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Coordonnees;
import main.Game;
import main.Utils;

public class SanctuaryCard extends Card{

    public SanctuaryCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
        name = "Carte Sanctuaire";
		image = Utils.loadImage("assets/cards/cardBlue.png");

        //TODO Auto-generated constructor stub
    }
    
}
