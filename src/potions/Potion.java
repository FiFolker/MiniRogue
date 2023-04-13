package potions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Button;
import main.ErrorDraw;
import main.Game;
import main.IUpdateAndDraw;
import main.Utils;

public abstract class Potion implements IUpdateAndDraw{

	public int effectValue;
	public String effectKey;
	public String name;
	public String info;
	public Rectangle rect;
	public String errorString;
	public Button potionButton;
	public static int number = 0;
	public int size = 32;
	int i = 0;
	
	public BufferedImage icon;
	Game game;

	public Potion(Game game, BufferedImage icon){
		this.game = game;
		this.icon = icon;
	}

	public void addButtton(){
		potionButton = new Button(new Rectangle(62 + 20 + (size+size/2)*(game.selectedClass.potions.size()), game.gui.yPotions-size/2, size, size), icon, info, false);
	}

	public abstract void applyEffect();

	@Override
	public void draw(Graphics2D g2) {
		
		potionButton.draw(g2);
		if(ErrorDraw.errorState){
			ErrorDraw.draw(g2, errorString, game);
		}
	}

	@Override
	public void update() {
		if(potionButton.isClicked()){
			applyEffect();
		}
	}

}