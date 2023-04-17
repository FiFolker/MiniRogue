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
	ErrorDraw errorDraw = new ErrorDraw();
	public int[] position = {98, 146};
	public int size = 32;
	int i = 0;
	
	public BufferedImage icon;
	Game game;

	public Potion(Game game, BufferedImage icon){
		this.game = game;
		this.icon = icon;
	}

	public void addButtton(){
		potionButton = new Button(new Rectangle(0, game.gui.yPotions-size/2, size, size), icon, info, false);
	}

	public abstract void applyEffect();

	@Override
	public void draw(Graphics2D g2) {
		
		potionButton.draw(g2);
		if(errorDraw.errorState){
			errorDraw.draw(g2, errorString, game);
		}
	}

	@Override
	public void update() {
		if(potionButton.isClicked()){
			applyEffect();
			Game.mouseH.leftClicked = false;
		}
	}

	public static Potion randomPotion(Game game){
		Potion returnedPotion = null;
		int rng = Utils.randomNumber(1, 6);
		switch(rng){
			case 1:
				returnedPotion = new FirePotion(game);
				break;
			case 2:
				returnedPotion = new FrostedPotion(game);
				break;
			case 3:
				returnedPotion = new HolyWater(game);
				break;
			case 4:
				returnedPotion = new LifePotion(game);
				break;
			case 5:
				returnedPotion = new PerceptionPotion(game);
				break;
			case 6:
				returnedPotion = new PoisonPotion(game);
				break;
			
		}
		return returnedPotion;
	}

}