package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
import dices.CurseDice;
import dices.Dice;
import dices.DungeonDice;
import dices.PoisonDice;

public class Game extends JPanel implements Runnable{

	// IMPORTATION DE CLASSES UTILES AU JEU
	JFrame frame;
	Thread gameThread;
	public static MouseHandler mouseH = new MouseHandler();
	public KeyHandler keyH = new KeyHandler();
	Card[][] cardBoard = new Card[3][3];
	Classe[] classes = new Classe[4];
	Card cardHovered = null;
	Menu menu;
	Lose lose;
	public Classe selectedClass;
	public GUI gui = new GUI(this);
	Button diceButton;
	public Coordonnees currentPos;
	public Card currentCard;
	public ArrayList<CharacterDice> characterDices = new ArrayList<>();
	public ArrayList<CurseDice> curseDices = new ArrayList<>();
	public ArrayList<PoisonDice> poisonDices = new ArrayList<>();
	public DungeonDice dungeonDice= new DungeonDice();

	public static final int SCREEN_WIDTH = 1280;

	// VARIABLE DE JEU
	public int gameState; 
	public final int menuState = 0;
	public final int playState = 1;
	public final int loseState = 2;
	public Font sansSerif = new Font("Sans-Serif", Font.BOLD, 15);
	public Font title = new Font("Sans-Serif", Font.BOLD, 48);
	public Font secondTitle = new Font("Sans-Serif", Font.PLAIN, 28);
	BufferedImage token;
	int currentClasse = 0;
	public int stage = 1;
	int totalStage = 4;
	int zone = 1;
	int[] zonePerStage = {2, 2, 3, 3};
	int topPos = 50;
	int leftPos = 450;
	public boolean diceHasRolled = false;
	public boolean canMove = false;
	public int choicePlaceX  = gui.xLine/2;
	public int choicePlaceY  = gui.yChoice+30;
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
		this.setFocusable(true);

		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
		this.addKeyListener(keyH);
		this.setBackground(Color.black);

		gameState = menuState;
		diceButton = new Button(new Rectangle(655, 850, 200, 50), "Lancer Dé");
		
		loadGame();

		menu = new Menu(this);
		lose = new Lose(this);
	}

	public void loadGame(){
		stage = 1;
		zone = 1;

		characterDices.clear();
		characterDices.add(new CharacterDice());

		token = Utils.loadImage("assets/jeton/player.png");
		classes[0] = new Classe(this, Utils.loadImage("assets/classes/rogue.png"), "Voleur", 7, 3, 0, 8);
		classes[1] = new Classe(this, Utils.loadImage("assets/classes/mage.png"), "Magicien", 4, 5, 0, 3);
		classes[2] = new Classe(this, Utils.loadImage("assets/classes/swordman.png"), "Epeiste", 10, 3, 3, 5);
		classes[3] = new Classe(this, Utils.loadImage("assets/classes/adventurer.png"), "Aventurier", 10, 3, 3, 5);
	
	}

	public void loadBoardCards(){
		
		currentPos = new Coordonnees(0, 0);
		for(int lig = 0;lig<cardBoard.length; lig++){
			for(int col=0; col<cardBoard[lig].length;col++){
				

				int x = leftPos+(col*sizeWidthCard)+10;
				int y = topPos+(lig*sizeHeightCard)+10;
				if(lig == cardBoard.length-1 && col == cardBoard[lig].length-1){
					cardBoard[lig][col] = new GuardianCard(this, new Rectangle(x,y,sizeWidthCard, sizeHeightCard), x, y, new Coordonnees(lig, col));
				}else{
					cardBoard[lig][col] = randomCard(x, y, new Coordonnees(lig, col));
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


			if(diceButton.isClicked() ^ keyH.spacePressed && !diceHasRolled){ // lancé de dé
				for(CharacterDice d : characterDices){
					d.roll();
				}

				for(CurseDice d : curseDices ){
					d.roll();
				}

				dungeonDice.roll();


				diceHasRolled = true;
				canMove = true;
				keyH.spacePressed = false;
			}

			currentCard.update();

		}else if(gameState == loseState){
			lose.update();
		}
		if(mouseH.leftClickedOnceTime){
			mouseH.leftClickedOnceTime = false;
		}
		
	}


	public void movementOnTheBoard(){
		for(int lig=0;lig<cardBoard.length;lig++){
			for(int col=0;col<cardBoard[lig].length;col++){
				if(cardBoard[lig][col].isClicked() && !cardBoard[lig][col].isReveal && moveIsOk(new Coordonnees(lig, col)) && canMove){// 
					cardBoard[lig][col].revealCard();
					
					currentPos.ligne = lig;
					currentPos.colonne = col;
					canMove = false;
					diceHasRolled = false;

					currentCard = cardBoard[currentPos.ligne][currentPos.colonne];

					if(lig == cardBoard.length-1 && col == cardBoard[lig].length - 1){
						if(zone == zonePerStage[stage-1] && stage < totalStage){
							stage ++;
							zone = 1;
							loadBoardCards();
						}else if(zone < zonePerStage[stage-1]){
							if(selectedClass.stats.get(selectedClass.foodString) > 0){
								selectedClass.substractStat(selectedClass.foodString, 1);
							}
							if(selectedClass.stats.get(selectedClass.foodString) == 0 ){
								selectedClass.substractStat(selectedClass.lifeString, 3);
							}
							zone ++;
							loadBoardCards();
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
		}else if(gameState == playState){
			cardHovered = null;
			if(cardBoard[0] != null){
				for(int col=0;col<cardBoard.length;col++){
					for(int lig=0;lig<cardBoard[col].length;lig++){
						if(!cardBoard[lig][col].isHover()){
							cardBoard[lig][col].draw(g2);
						}
						if(cardBoard[lig][col].isHover()){
							cardHovered = cardBoard[lig][col];
						}
					}
				}
			}

			if(cardHovered != null){
				cardHovered.draw(g2);
			}

			int xDice = 10;

			for(CharacterDice d : characterDices){
				d.draw(g2, diceButton.button.x+diceButton.button.width + xDice, diceButton.button.y+diceButton.button.height/2);
				xDice += Utils.textToRectangle2D(d.name, g2).getWidth() + 10;
			}

			dungeonDice.draw(g2, diceButton.button.x+diceButton.button.width + xDice, diceButton.button.y+diceButton.button.height/2);
			xDice += Utils.textToRectangle2D(dungeonDice.name, g2).getWidth() + 10;
			for(PoisonDice d : poisonDices){
				d.draw(g2, diceButton.button.x+diceButton.button.width + xDice, diceButton.button.y+diceButton.button.height/2);
				xDice += Utils.textToRectangle2D(d.name, g2).getWidth() + 10;
			}
			for(CurseDice d : curseDices){
				d.draw(g2, diceButton.button.x+diceButton.button.width + xDice, diceButton.button.y+diceButton.button.height/2);
				xDice += Utils.textToRectangle2D(d.name, g2).getWidth() + 10;
			}

			g2.drawImage(selectedClass.icon, currentCard.hitbox.x + currentCard.hitbox.width - 80,
			currentCard.hitbox.y + currentCard.hitbox.height - 80, 64, 64, null);

			diceButton.draw(g2);

			gui.draw(g2);
		}else if(gameState == loseState){
			lose.draw(g2);
		}
	}

	public boolean moveIsOk(Coordonnees coord){
		boolean isOk = false;
		if(coord.estDansPlateau() && coord.ligne - 1 == currentPos.ligne ^ coord.colonne -1 == currentPos.colonne){
			isOk = true;
		}

		return isOk;
	}
	

	public Card randomCard(int x, int y, Coordonnees coord){
		Card returnedCard = null;

		int rng = Utils.randomNumber(0, 7);
		switch(rng){
			case 0:
				returnedCard = new BurialCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			case 1:
				returnedCard = new EnnemyCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, stage, coord);
				break;
			case 2:
				returnedCard = new FireCampCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			case 3:
				returnedCard = new MerchantCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			case 4:
				returnedCard = new ObjectCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			case 5:
				returnedCard = new SanctuaryCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			case 6:
				returnedCard = new TrapCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			case 7:
				returnedCard = new TreasureCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
			default:
				returnedCard = new Card(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
				break;
		}

		return returnedCard;

	}
	
}
