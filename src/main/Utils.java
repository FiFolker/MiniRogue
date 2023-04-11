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
    
	public static void drawSixDicePossibilities(Game game, Graphics2D g2, String[] text){
		Utils.drawDice(g2, 10, game.choicePlaceY, 1);
		g2.drawString(text[0], 40, game.choicePlaceY+(int)Utils.textToRectangle2D(text[0], g2).getHeight());

		Utils.drawDice(g2, 10, game.choicePlaceY + 30, 2);
		g2.drawString(text[1], 40, game.choicePlaceY+30+(int)Utils.textToRectangle2D(text[1], g2).getHeight());

		Utils.drawDice(g2, 10, game.choicePlaceY+60, 3);
		g2.drawString(text[2], 40, game.choicePlaceY+60+(int)Utils.textToRectangle2D(text[2], g2).getHeight());

		Utils.drawDice(g2, 30 + (int)Utils.textToRectangle2D(text[0], g2).getWidth() + 20, game.choicePlaceY, 4);
		g2.drawString(text[3], 30 + (int)Utils.textToRectangle2D(text[0], g2).getWidth() + 50, game.choicePlaceY+(int)Utils.textToRectangle2D(text[3], g2).getHeight());

		Utils.drawDice(g2,  30 + (int)Utils.textToRectangle2D(text[1], g2).getWidth() + 20, game.choicePlaceY+30, 5);
		g2.drawString(text[4], 30 + (int)Utils.textToRectangle2D(text[1], g2).getWidth() + 50, game.choicePlaceY+30+(int)Utils.textToRectangle2D(text[4], g2).getHeight());

		Utils.drawDice(g2,  30 + (int)Utils.textToRectangle2D(text[2], g2).getWidth() + 20, game.choicePlaceY+60, 6);
		g2.drawString(text[5], 30 + (int)Utils.textToRectangle2D(text[2], g2).getWidth() + 50, game.choicePlaceY+60+(int)Utils.textToRectangle2D(text[5], g2).getHeight());
	}

	public static void drawThreeDicePossibilities(Game game, Graphics2D g2, String[] text){
		Utils.drawDice(g2, 10, game.choicePlaceY, 1);
		Utils.drawDice(g2, 35, game.choicePlaceY, 2);
		
		g2.drawString(text[0], 60, game.choicePlaceY+(int)Utils.textToRectangle2D(text[0], g2).getHeight());

		Utils.drawDice(g2, 10, game.choicePlaceY+30, 3);
		Utils.drawDice(g2, 35, game.choicePlaceY+30, 4);

		g2.drawString(text[1], 60, game.choicePlaceY+30+(int)Utils.textToRectangle2D(text[1], g2).getHeight());


		Utils.drawDice(g2, 10, game.choicePlaceY+60, 5);
		Utils.drawDice(g2, 35, game.choicePlaceY+60, 6);

		g2.drawString(text[2], 60, game.choicePlaceY+60+(int)Utils.textToRectangle2D(text[2], g2).getHeight());
	}

}
