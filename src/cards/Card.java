package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Coordonnees;
import main.Game;
import main.IUpdateAndDraw;
import main.Utils;

public class Card implements IUpdateAndDraw{

	BufferedImage backCard;
	BufferedImage image;
	BufferedImage currentImage;
	String result = "Lancez les dés ...";
	public String name = "defaultCard";
	public Rectangle hitbox;
	public Coordonnees coord;
	Game game;
	int x,y;
	private int zoom = 15;
	public boolean isReveal = false;
	public boolean isFinish = false;
	public boolean needSkillTest = false;
	int centerLeft;
	int centerRight;

	public Card(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		this.game = game;
		centerLeft = (game.gui.xLine/2)/2;
		centerRight =  (game.gui.xLine/2) + (game.gui.xLine/2)/2;
		backCard = Utils.loadImage("assets/cards/backCard.png");
		image = Utils.loadImage("assets/cards/cardBlue.png");
		currentImage = backCard;
		this.coord = coord;
		this.hitbox = hitbox;
		this.x = x;
		this.y = y;
	}


	public boolean isHover(){
		return hitbox.contains(Game.mouseH.location);
	}

	public boolean isClicked(){
		return isHover() && Game.mouseH.leftClicked;
	}

	public void revealCard(){
		currentImage = image;
		isReveal = true;
	}

	public void unrevealCard(){
		currentImage = backCard;
		isReveal = false;
	}

	@Override
	public void update(){}

	@Override
	public void draw(Graphics2D g2){
		g2.setColor(Color.white);
		g2.setFont(Game.defaultFont);
		if(isHover()){
			g2.drawImage(currentImage, x-zoom/2, y-zoom/2, hitbox.width+zoom, hitbox.height+zoom, null);
		}else{
			g2.drawImage(currentImage, x, y, hitbox.width, hitbox.height, null);
		}
		g2.drawString(name, x + hitbox.width/2 - (int)Utils.textToRectangle2D(name, g2).getWidth()/2 , y + 30);
	}

	
}
