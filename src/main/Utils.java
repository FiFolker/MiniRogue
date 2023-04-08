package main;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Utils {

    public static int getXforCenteredText(String text, Graphics2D g2){
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return Game.SCREEN_WIDTH/2 - length/2;
	}
	
	public static Rectangle2D textToRectangle2D(String txt, Graphics2D g2){
		return g2.getFontMetrics().getStringBounds(txt, g2);
	}
    
}
