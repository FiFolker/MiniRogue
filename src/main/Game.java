package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Card;
import cards.GuardianCard;
import classes.Classe;
import controls.MouseHandler;

public class Game extends JPanel implements Runnable{

	JFrame frame;
	Thread gameThread;
	public static MouseHandler mouseH = new MouseHandler();
	Card[][] cardBoard = new Card[3][3];
	Classe[] classes = new Classe[4];
	Card cardHovered = null;
	Menu menu;
	Classe selectedClass;
	GUI gui = new GUI(this);
	Button diceButton;
	Coordonnees currentPos;
	public ArrayList<Dice> dices = new ArrayList<>();

	public static final int SCREEN_WIDTH = 1280;

	public int gameState; 
	public final int menuState = 0;
	public final int playState = 1;
	public Font sansSerif = new Font("Sans-Serif", Font.BOLD, 15);
	public Font title = new Font("Sans-Serif", Font.BOLD, 48);
	int currentClasse = 0;
	int stage = 0;
	int totalStage = 4;

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
		diceButton = new Button(new Rectangle(655, 850, 200, 50), "Lancer Dé");
		dices.add(new Dice(6));
		try {
			classes[0] = new Classe(this, ImageIO.read(new File("assets/classes/rogue.png")), "Voleur", 7, 3, 0, 8);
			classes[1] = new Classe(this, ImageIO.read(new File("assets/classes/mage.png")), "Magicien", 4, 5, 0, 3);
			classes[2] = new Classe(this, ImageIO.read(new File("assets/classes/swordman.png")), "Epeiste", 10, 3, 3, 5);
			classes[3] = new Classe(this, ImageIO.read(new File("assets/classes/adventurer.png")), "Aventurier", 10, 3, 3, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		menu = new Menu(this);
	}

	public void loadCards(){
		int topPos = 50;
		int leftPos = 450;
		currentPos = new Coordonnees(0, 0);
		for(int i = 0;i<cardBoard.length; i++){
			for(int j=0; j<cardBoard[i].length;j++){
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File("assets/cards/cardBlue.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}

				int sizeWidth = image.getWidth()*2;
				int sizeHeight = image.getHeight()*2;

				int x = leftPos+(i*sizeWidth)+10;
				int y = topPos+(j*sizeHeight)+10;
				if(i == 2 && j ==2){
					cardBoard[i][j] = new GuardianCard(image, "Test "+i, new Rectangle(x,y,sizeWidth, sizeHeight), x, y);
				}else{
					cardBoard[i][j] = new Card(image, "Test "+i, new Rectangle(x,y,sizeWidth, sizeHeight), x, y);
				}
			}
			
			
		}
		cardBoard[0][0].revealCard();
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
			for(int i=0;i<cardBoard.length;i++){
				for(int j=0;j<cardBoard[i].length;j++){
					if(cardBoard[i][j].isClicked() && !cardBoard[i][j].isReveal && isAdjacentToCurrentPos(new Coordonnees(i, j))){
						cardBoard[i][j].revealCard();
						currentPos.ligne = i;
						currentPos.colonne = j;
					}
				}
			}
			selectedClass.update();
			if(diceButton.isClicked()){
				for(Dice d : dices){
					d.roll();
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
			if(cardBoard[0] != null){
				for(int i=0;i<cardBoard.length;i++){
					for(int j=0;j<cardBoard[i].length;j++){
						if(!cardBoard[i][j].isHover()){
							cardBoard[i][j].draw(g2);
						}
						if(cardBoard[i][j].isHover()){
							cardHovered = cardBoard[i][j];
						}
					}
				}
			}
			if(cardHovered != null){
				cardHovered.draw(g2);
			}
			int xDice = 10;
			for(Dice d : dices){
				d.draw(g2, diceButton.button.x+diceButton.button.width + xDice, diceButton.button.y+diceButton.button.height/2);
				xDice += 10;
			}
			diceButton.draw(g2);
			gui.draw(g2);
		}
	}

	/**
	 * Renvoie les coordonnées de la case suivante, en suivant une direction donnée.
	 *
	 * @param c coordoonée de laquelle on part
	 * @param d la direction à suivre
	 * @return les coordonnées de la case suivante
	 */
	static Coordonnees suivante(Coordonnees c, Direction d) {
		return new Coordonnees(c.ligne + Direction.mvtVertic(d),
				c.colonne + Direction.mvtHoriz(d));
	}


	public boolean isAdjacentToCurrentPos(Coordonnees coord){
		boolean isAdjacent = false;
		Direction[] cardinales = Direction.cardinales();
		int i = 0;
		while(i<cardinales.length && !isAdjacent){
			Coordonnees voisine = Game.suivante(coord, cardinales[i]);
			if(coord.estDansPlateau() && voisine.equals(currentPos)){
				isAdjacent = true;
			}
			i++;
		}
		return isAdjacent;
	}
	
	
}
