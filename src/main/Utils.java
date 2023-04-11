package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

public class Utils {

    public static int getXforCenteredText(String text, Graphics2D g2){
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return Game.SCREEN_WIDTH/2 - length/2;
	}
	
	public static Rectangle2D textToRectangle2D(String txt, Graphics2D g2){
		return g2.getFontMetrics().getStringBounds(txt, g2);
	}

	public static int randomNumber(int min, int max){
		return (int)Math.floor(Math.random() * (max - min + 1) + min);
	}

	public static BufferedImage loadImage(String path){
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(path));
		}catch(Exception e){
			System.out.println("Erreur dans le loadImaage : " + e);
		}
		return image;
	}

	public static void drawDice(Graphics2D g2, int x, int y, int value){
		g2.drawRect(x, y, 20, 20);
		g2.drawString(Integer.toString(value), x+7, y+(int)Utils.textToRectangle2D(Integer.toString(value), g2).getHeight());
	}

	public static void drawDice(Graphics2D g2, int x, int y, int value, Color color){
		g2.setColor(Color.white);
		g2.drawRect(x, y, 20, 20);
		g2.setColor(color);
		g2.fillRect(x+1, y+1, 19, 19);
		if(color.equals(Color.white)){
			g2.setColor(Color.black);
		}else{
			g2.setColor(Color.white);
		}
		g2.drawString(Integer.toString(value), x+7, y+(int)Utils.textToRectangle2D(Integer.toString(value), g2).getHeight());
	}
    
}
