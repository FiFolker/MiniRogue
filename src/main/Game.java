package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Card;
import classes.Classe;
import controls.MouseHandler;

public class Game extends JPanel implements Runnable{

	JFrame frame;
	Thread gameThread;
	public static MouseHandler mouseH = new MouseHandler();
	Card[] cards = new Card[9];
	Classe[] classes = new Classe[3];
	Card cardHovered = null;
	Menu menu;

	public static final int SCREEN_WIDTH = 1280;

	public int gameState; 
	public final int menuState = 0;
	public final int playState = 1;
	public Font sansSerif = new Font("Sans-Serif", Font.BOLD, 15);
	public Font title = new Font("Sans-Serif", Font.BOLD, 48);
	int currentClasse = 0;

	int FPS = 60;

	public Game(JFrame frame) {
		this.frame = frame;
		setup();
	}

	public Point getCenterOfPanel(){
		return new Point(this.getWidth()/2, this.getHeight()/2);
	}

	public void setup(){
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
		this.setBackground(Color.black);
		gameState = menuState;
		try {
			classes[0] = new Classe(ImageIO.read(new File("assets/classes/rogue.png")), "Voleur", 7, 3, 0, 8);
			classes[1] = new Classe(ImageIO.read(new File("assets/classes/mage.png")), "Magicien", 4, 5, 0, 3);
			classes[2] = new Classe(ImageIO.read(new File("assets/classes/swordman.png")), "Epeiste", 10, 3, 3, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu = new Menu(this);
	}

	public void loadCards(){
		int topPos = 300;
		int leftPos = 500;
		int line = 1;
		int col = 0;
		for(int i = 0;i<cards.length; i++){
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("assets/cards/cardBlue.png"));
			} catch (IOException e) {
				System.out.println("erreur dans le load de l'image");
				e.printStackTrace();
			}

			int x = leftPos+col*image.getWidth()+10;
			int y = topPos+line*image.getHeight()+10;
			cards[i] = new Card(image, "Test "+i, new Rectangle(x,y,image.getWidth(), image.getHeight()), x, y);

			col ++;
			if(i == 2){
				line = 2;
				col = 0;
			}
			if(i == 5){
				line = 3;
				col = 0;
			}
			
		}
	}

	public void startGameThread(){
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long timer = 0;
		long currentTime = 0;
		long lastTime = System.nanoTime();

		while(gameThread != null){
			
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta >= 1){
				
				update();
				repaint();
				delta --;
			}
			
			if(timer >= 1000000000){
				timer = 0;
			}

		}
	}

	public void update(){
		if(gameState == menuState){
			menu.update();
		}else if (gameState == playState){
			for(Card c : cards){
				if(c.isClicked()){
					c.revealCard();
				}
			}
		}
		if(mouseH.leftClickedOnceTime){
			mouseH.leftClickedOnceTime = false;
		}

		
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		if(gameState == menuState){
			menu.draw(g2);
		}
		if(gameState == playState){
			if(cards[0] != null){
				for(Card c : cards){
					if(!c.isHover()){
						c.draw(g2);
					}
					if(c.isHover()){
						cardHovered = c;
					}
				}
			}
			if(cardHovered != null){
				cardHovered.draw(g2);
			}
			
		}
	}

	
	
}
