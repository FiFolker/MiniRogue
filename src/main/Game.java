package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Card;
import controls.MouseHandler;

public class Game extends JPanel implements Runnable{

	JFrame frame;
	Thread gameThread;
	public static MouseHandler mouseH = new MouseHandler();
	HashMap<String, Button> buttons = new HashMap<>();
	Card[] cards = new Card[9];
	Card cardHovered = null;

	public static final int SCREEN_WIDTH = 1280;

	public int gameState; 
	public final int menuState = 0;
	public final int playState = 1;
	private String playButton = "playButton";
	private String exitButton = "exitButton";
	public Font sansSerif = new Font("Sans-Serif", Font.BOLD, 15);
	public Font title = new Font("Sans-Serif", Font.BOLD, 48);
	private Dimension sizeOfButton = new Dimension(200,25);

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
		buttons.put(playButton, new Button(new Rectangle(632-sizeOfButton.width/2, 470 - sizeOfButton.height/2, sizeOfButton.width, sizeOfButton.height), "Jouer"));
		buttons.put(exitButton, new Button(new Rectangle(632-sizeOfButton.width/2, 470- sizeOfButton.height/2 + sizeOfButton.height + 10, sizeOfButton.width, sizeOfButton.height), "Quitter"));
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
			for(String s: buttons.keySet()){
				if(buttons.get(s).isClicked() && s.equals(playButton)){
					gameState = playState;
					loadCards();
				}
				if(buttons.get(s).isClicked() && s.equals(exitButton)){
					System.exit(0);
				}
			}
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
			g2.setFont(title);
			g2.drawString("MINI ROGUE", getXforCenteredText("MINI ROGUE", g2), 400);
			g2.setFont(sansSerif);

			for(Button b : buttons.values()){
				b.draw(g2);
			}
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

	public int getXforCenteredText(String text, Graphics2D g2){
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return Game.SCREEN_WIDTH/2 - length/2;
	}
	
}
