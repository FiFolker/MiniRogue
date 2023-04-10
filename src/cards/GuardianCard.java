package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Coordonnees;
import main.Game;
import main.Utils;

public class GuardianCard extends Card{

    public GuardianCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
        name = "Carte Guardien";
        backCard = Utils.loadImage("assets/cards/guardianBackCard.png");
        image = Utils.loadImage("assets/cards/cardRed.png");
        currentImage = backCard;
    }
    
}
