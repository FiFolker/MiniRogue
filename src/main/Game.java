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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.BurialCard;
import cards.Card;
import cards.EnnemyCard;
import cards.FireCampCard;
import cards.GuardianCard;
import cards.MerchantCard;
import cards.ObjectCard;
import cards.SanctuaryCard;
import cards.TrapCard;
import cards.TreasureCard;
import classes.Classe;
import controls.KeyHandler;
import controls.MouseHandler;
import dices.CharacterDice;
import dices.Dice;
import dices.DungeonDice;

public class Game extends JPanel implements Runnable{

	// IMPORTATION DE CLASSES UTILES AU JEU
	JFrame frame;
	Thread gameThread;
	public static MouseHandler mouseH = new MouseHandler();
	public static KeyHandler keyH = new KeyHandler();
	Card[][] cardBoard = new Card[3][3];
	Classe[] classes = new Classe[4];
	Card cardHovered = null;
	Menu menu;
	public Classe selectedClass;
	GUI gui = new GUI(this);
	Button diceButton;
	Coordonnees currentPos;
	Card currentCard;
	public ArrayList<Dice> dices = new ArrayList<>();

	public static final int SCREEN_WIDTH = 1280;

	// VARIABLE DE JEU
	public int gameState; 
	public final int menuState = 0;
	public final int playState = 1;
	public Font sansSerif = new Font("Sans-Serif", Font.BOLD, 15);
	public Font title = new Font("Sans-Serif", Font.BOLD, 48);
	public Font secondTitle = new Font("Sans-Serif", Font.PLAIN, 28);
	BufferedImage token;
	int currentClasse = 0;
	int stage = 1;
	int totalStage = 4;
	int zone = 1;
	int[] zonePerStage = {2, 2, 3, 3};
	int topPos = 50;
	int leftPos = 450;
	public boolean diceHasRolled = false;
	public boolean canMove = false;
	public boolean canRoll = true;
	int sizeWidthCard = 202;
	int sizeHeightCard = 250;

	// FPS
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
		this.addKeyListener(keyH);
		this.setBackground(Color.black);

		gameState = menuState;
		diceButton = new Button(new Rectangle(655, 850, 200, 50), "Lancer Dé");
		dices.add(new CharacterDice());
		dices.add(new DungeonDice());
		try {
			token = ImageIO.read(new File("assets/jeton/player.png"));
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
		
		currentPos = new Coordonnees(0, 0);
		for(int i = 0;i<cardBoard.length; i++){
			for(int j=0; j<cardBoard[i].length;j++){
				

				int x = leftPos+(i*sizeWidthCard)+10;
				int y = topPos+(j*sizeHeightCard)+10;
				if(i == cardBoard.length-1 && j == cardBoard[i].length-1){
					BufferedImage image = null;
					try {
						image = ImageIO.read(new File("assets/cards/cardRed.png"));
					} catch (IOException e) {
						System.out.println("erreur dans le load de l'image");
						e.printStackTrace();
					}
					cardBoard[i][j] = new GuardianCard(this, image, new Rectangle(x,y,sizeWidthCard, sizeHeightCard), x, y);
				}else{
					cardBoard[i][j] = randomCard(x, y);
				}
			}
			
			
		}
		currentCard = cardBoard[0][0];
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
			movementOnTheBoard();
			selectedClass.update();
			if(diceButton.isClicked() && canRoll){
				for(Dice d : dices){
					d.roll();
				}
				diceHasRolled = true;
				canRoll = false;
				Game.mouseH.leftClickedOnceTime = false;
				
			}
			if(diceHasRolled){
				currentCard.update(dices, stage);
				if(currentCard.getClass() == EnnemyCard.class){
					EnnemyCard e = (EnnemyCard)currentCard;
					if(e.ennemy.life > 0){
						diceHasRolled = false;
						canRoll = true;
					}else{
						canMove = true;
						diceHasRolled = false;
					}
				}else{
					canMove = true;
					diceHasRolled = false;
				}
				
			}
			selectedClass.update();
		}
		if(mouseH.leftClickedOnceTime){
			mouseH.leftClickedOnceTime = false;
		}

		
	}

	public void movementOnTheBoard(){
		for(int col=0;col<cardBoard.length;col++){
			for(int lig=0;lig<cardBoard[col].length;lig++){
				if(cardBoard[col][lig].isClicked() && !cardBoard[col][lig].isReveal && moveIsOk(new Coordonnees(lig, col)) && canMove){// 
					cardBoard[col][lig].revealCard();
					
					currentPos.ligne = lig;
					currentPos.colonne = col;
					canMove = false;
					canRoll = true;

					currentCard = cardBoard[currentPos.colonne][currentPos.ligne];

					if(col == cardBoard.length-1 && lig == cardBoard[col].length - 1){
						if(zone == zonePerStage[stage-1] && stage < totalStage){
							stage ++;
							zone = 1;
							loadCards();
						}else if(zone < zonePerStage[stage-1]){
							zone ++;
							loadCards();
						}
						
					}
				}
			}
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
				for(int col=0;col<cardBoard.length;col++){
					for(int lig=0;lig<cardBoard[col].length;lig++){
						if(!cardBoard[col][lig].isHover()){
							cardBoard[col][lig].draw(g2);
						}
						if(cardBoard[col][lig].isHover()){
							cardHovered = cardBoard[col][lig];
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
				xDice += Utils.textToRectangle2D(d.name, g2).getWidth() + 10;
			}
			g2.drawImage(selectedClass.icon, currentCard.hitbox.x + currentCard.hitbox.width - 80,
			currentCard.hitbox.y + currentCard.hitbox.height - 80, 64, 64, null);
			diceButton.draw(g2);
			gui.draw(g2);
		}
	}

	public boolean moveIsOk(Coordonnees coord){
		boolean isOk = false;
		if(coord.estDansPlateau() && coord.ligne - 1 == currentPos.ligne ^ coord.colonne -1 == currentPos.colonne){
			isOk = true;
		}

		return isOk;
	}
	

	public Card randomCard(int x, int y){
		Card returnedCard = null;
		Random rand = new Random();
		BufferedImage image = null;
		int rng = rand.nextInt(7);
		switch(rng){
			case 0:
				try {
					image = ImageIO.read(new File("assets/cards/cardGray.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new BurialCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			case 1:
				try {
					image = ImageIO.read(new File("assets/cards/cardRed.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new EnnemyCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, stage);
				break;
			case 2:
				try {
					image = ImageIO.read(new File("assets/cards/cardBlue.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new FireCampCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			case 3:
				try {
					image = ImageIO.read(new File("assets/cards/cardGreen.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new MerchantCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			case 4:
				try {
					image = ImageIO.read(new File("assets/cards/cardBlue.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new ObjectCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			case 5:
				try {
					image = ImageIO.read(new File("assets/cards/cardBlue.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new SanctuaryCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			case 6:
				try {
					image = ImageIO.read(new File("assets/cards/cardBlue.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new TrapCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			case 7:
				try {
					image = ImageIO.read(new File("assets/cards/cardYellow.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new TreasureCard(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
			default:
				try {
					image = ImageIO.read(new File("assets/cards/cardBlue.png"));
				} catch (IOException e) {
					System.out.println("erreur dans le load de l'image");
					e.printStackTrace();
				}
				returnedCard = new Card(this, image, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y);
				break;
		}

		return returnedCard;

	}
	
}
