package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Button implements IUpdateAndDraw{

	public Rectangle button;
	Polygon icon;
	BufferedImage image;
	String toolTipMessage;
	String buttonText;
	public boolean isSelected = false;

	public Button(Rectangle rect, String text){
		this.button = rect;
		this.buttonText = text;
	}

	public Button(Rectangle rect, BufferedImage image){
		this.button = rect;
		this.image = image;
	}

	public Button(Rectangle rect, BufferedImage image, String desc){
		this.button = rect;
		this.image = image;
		this.toolTipMessage = desc;
	}

	public Button(Rectangle rect, Polygon poly){
		this.button = rect;
		this.icon = poly;
	}

	@Override
	public void update(){
		
	}
	@Override
	public void draw(Graphics2D g2){
		
		if(inCollision()){
			g2.setColor(Color.gray);
		}else if(isSelected){
			g2.setColor(Color.green);
		}else{
			g2.setColor(Color.white);
		}
		
		g2.draw(button);

		if(buttonText != null){
			int tempX = button.x + button.width/2;
			int tempY = button.y + button.height/2;
			g2.drawString(buttonText, tempX -(int)Utils.textToRectangle2D(buttonText, g2).getWidth()/2, tempY + (int)Utils.textToRectangle2D(buttonText, g2).getHeight()/3);
		}

		if(icon != null){
			g2.draw(icon);
		}

		if(image != null){
			g2.drawImage(image, button.x, button.y, button.width, button.height, null);
		}

		if(toolTipMessage != null && inCollision()){
			g2.setColor(Color.blue);
			g2.setFont(new Font("test", Font.BOLD, 14));
			g2.drawString(toolTipMessage, Game.mouseH.x, Game.mouseH.y - 56);
		}

	}

	public boolean inCollision(){
		return button.contains(Game.mouseH.location);
	}

	public boolean isClicked(){
		return inCollision() && Game.mouseH.leftClickedOnceTime;
	}

	
	
}
