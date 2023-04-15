package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class ErrorDraw {
	private int timer = 0;
	public boolean errorState = false;


	public void draw(Graphics2D g2, String errorString, Game game) {
		errorState = true;
		if(errorState){
			g2.setColor(Color.red);
			g2.setFont(game.sansSerif);
			g2.drawString(errorString, game.gui.xLine + ((game.getWidth() - game.gui.xLine)/2 - (int)Utils.textToRectangle2D(errorString, g2).getWidth()/2), 830);
			timer ++;
			if(timer >= 180){
				timer = 0;
				errorState = false;
			}
		}
	}
	
}
