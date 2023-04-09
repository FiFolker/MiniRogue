package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import dices.Dice;
import main.Game;
import main.Utils;

public class Card {

	BufferedImage backCard;
	BufferedImage image;
	BufferedImage currentImage;
	String name = "defaultCard";
	public Rectangle hitbox;
	Game game;
	int x,y;
	private int zoom = 15;
	public boolean isReveal = false;

	public Card(Game game, BufferedImage image, Rectangle hitbox, int x, int y) {
		this.game = game;
		try {
			backCard = ImageIO.read(new File("assets/cards/backCard.png"));
		} catch (IOException e) {
			System.out.println("erreur dans le load de la BackCard " + e);
		}
		currentImage = backCard;
		this.image = image;
		this.hitbox = hitbox;
		this.x = x;
		this.y = y;
	}

	public boolean isHover(){
		return hitbox.contains(Game.mouseH.location);
	}

	public boolean isClicked(){
		return isHover() && Game.mouseH.leftClickedOnceTime;
	}

	public void revealCard(){
		currentImage = image;
		isReveal = true;
	}

	public void draw(Graphics2D g2){
		
		if(isHover()){
			g2.drawImage(currentImage, x-zoom/2, y-zoom/2, hitbox.width+zoom, hitbox.height+zoom, null);
		}else{
			g2.drawImage(currentImage, x, y, hitbox.width, hitbox.height, null);
		}
		g2.drawString(name, x + hitbox.width/2 - (int)Utils.textToRectangle2D(name, g2).getWidth()/2 , y + 30);
	}

	public void updateOnRoll(ArrayList<Dice> dices, int stage) {}
	public void updateAlways(){}

	public void additionalDraw(Graphics2D g2, int x, int y){}
	
}
