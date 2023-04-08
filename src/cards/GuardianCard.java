package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GuardianCard extends Card{

    public GuardianCard(BufferedImage image, String name, Rectangle hitbox, int x, int y) {
        super(image, name, hitbox, x, y);

        try {
            backCard = ImageIO.read(new File("assets/cards/guardianBackCard.png"));
        } catch (IOException e) {
			System.out.println("erreur dans le load de la BackCard du Guardian" + e);
        }
        currentImage = backCard;
    }
    
}
