package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

public class GuardianCard extends Card{

    public GuardianCard(Game game, BufferedImage image, Rectangle hitbox, int x, int y) {
        super(game, image, hitbox, x, y);
        name = "Carte Guardien";

        try {
            backCard = ImageIO.read(new File("assets/cards/guardianBackCard.png"));
        } catch (IOException e) {
			System.out.println("erreur dans le load de la BackCard du Guardian" + e);
        }
        currentImage = backCard;
    }
    
}
