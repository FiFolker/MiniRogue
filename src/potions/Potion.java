package potions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Button;
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
	boolean error = false;
	public Button potionButton;
	public int currentNumber = 0;
	public static int number = 0;
	public int size = 32;
	int i = 0;
	
	public BufferedImage icon;
	Game game;

	public Potion(Game game, BufferedImage icon){
		this.game = game;
		this.icon = icon;
		number ++;
		currentNumber = number;
	}

	public void addButtton(){
		potionButton = new Button(new Rectangle(62 + 20 + (size+size/2)*(game.selectedClass.potions.size()), game.gui.yPotions-size/2, size, size), icon, info, false);
	}

	public abstract void applyEffect();

	@Override
	public void draw(Graphics2D g2) {
		
		potionButton.draw(g2);
		if(error){
			g2.setColor(Color.red);
			g2.setFont(game.sansSerif);
			g2.drawString(errorString, (game.getWidth() - game.gui.xLine)/2 - (int)Utils.textToRectangle2D(errorString, g2).getWidth()/16, 830);
			i ++;
			if(i >= 180){
				i = 0;
				error = false;
			}
		}
	}

	@Override
	public void update() {
		if(potionButton.isClicked()){
			applyEffect();
		}
	}

}