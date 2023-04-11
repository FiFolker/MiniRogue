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
	public Rectangle rect;
	public String errorString;
	boolean error = false;
	Button potionButton;
	public int currentNumber = 0;
	public static int number = 0;
	int size = 32;
	int i = 0;
	
	BufferedImage icon;
	Game game;

	public Potion(Game game, BufferedImage icon){
		this.game = game;
		this.icon = icon;
		number ++;
		currentNumber = number;

	}

	public abstract void applyEffect();

	public static void removePotion(Potion potion){
		number --;
		int i = 0;
		while(i < potion.game.selectedClass.potions.length && potion.game.selectedClass.potions[i] != null && potion.game.selectedClass.potions[i] != potion){
			i++;
		}
		if(potion.game.selectedClass.potions[i] == potion){
			potion.game.selectedClass.potions[i] = null;
		}
		if(i == 0 && potion.game.selectedClass.potions[1] != null){
			potion.game.selectedClass.potions[0] = potion.game.selectedClass.potions[1];
			potion.game.selectedClass.potions[0].currentNumber = Potion.number;
			potion.game.selectedClass.potions[0].potionButton.button.x = 62 + 20 + (potion.size+potion.size/2)*(potion.currentNumber-1);
			potion.game.selectedClass.potions[1] = null;
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		
		potionButton.draw(g2);
		if(error){
			g2.setColor(Color.red);
			g2.setFont(game.sansSerif);
			g2.drawString(errorString, 10, game.gui.yPotions + (int)Utils.textToRectangle2D(errorString, g2).getHeight() + 10);
			i ++;
			if(i >= 60){
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