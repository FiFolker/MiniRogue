package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.CubicCurve2D;
import java.awt.image.BufferedImage;
import java.security.Guard;
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
import dices.DungeonDice;
import dices.PoisonDice;
import potions.FirePotion;

public class Game extends JPanel implements Runnable{

	// IMPORTATION DE CLASSES UTILES AU JEU
	JFrame frame;
	transient Thread gameThread;
	public static transient MouseHandler mouseH = new MouseHandler();
	public transient KeyHandler keyH = new KeyHandler();
	transient Card[][] cardBoard = new Card[3][3];
	transient Classe[] classes = new Classe[4];
	transient Card cardHovered = null;
	transient Menu menu;
	transient Lose lose;
	transient Win win;
	public transient Classe selectedClass;
	public transient GUI gui = new GUI(this);
	transient Button diceButton;
	public transient Coordonnees currentPos;
	public transient Card currentCard;
	public transient ArrayList<CharacterDice> characterDices = new ArrayList<>();
	public transient CurseDice curseDice = null;
	public transient PoisonDice poisonDice = null;
	public transient DungeonDice dungeonDice= new DungeonDice();

	public static final int SCREEN_WIDTH = 1280;

	// VARIABLE DE JEU
	public int gameState; 
	public final int menuState = 0;
	public final int playState = 1;
	public final int loseState = 2;
	public final int winState = 3;
	public Font sansSerif = new Font("Sans-Serif", Font.BOLD, 15);
	public Font title = new Font("Sans-Serif", Font.BOLD, 48);
	public Font secondTitle = new Font("Sans-Serif", Font.PLAIN, 28);
	transient BufferedImage token;
	int currentClasse = 0;
	public int stage = 1;
	public int totalStage = 4;
	int zone = 1;
	int[] zonePerStage = {2, 2, 3, 3};
	int topPos = 50;
	int leftPos = 450;
	public boolean diceHasRolled = false;
	public boolean canMove = false;
	public boolean inFight = false;
	public boolean perceptionEffect = false;
	public int choicePlaceX  = gui.xLine/2;
	public int choicePlaceY  = gui.yChoice+30;
	int sizeWidthCard = 202;
	int sizeHeightCard = 250;
	int gap = 10;
	public int textPlaceY = 830;

	// STATIC 
	public static Font defaultFont = new Font("Dialog", Font.PLAIN, 12);

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
		win = new Win(this);
	}

	public void loadGame(){
		stage = 1;
		zone = 1;

		curseDice = null;
		poisonDice = null;

		GuardianCard.reset();

		characterDices.clear();
		characterDices.add(new CharacterDice());

		token = Utils.loadImage("assets/jeton/player.png");
		classes[0] = new Classe(this, Utils.loadImage("assets/classes/rogue.png"), "Voleur", 1000, 1000, 1000, 1000);//7, 3, 0, 8
		classes[1] = new Classe(this, Utils.loadImage("assets/classes/mage.png"), "Magicien", 4, 5, 0, 3);
		classes[2] = new Classe(this, Utils.loadImage("assets/classes/swordman.png"), "Epeiste", 10, 3, 3, 5);
		classes[3] = new Classe(this, Utils.loadImage("assets/classes/adventurer.png"), "Aventurier", 10, 3, 3, 5);
	
	}

	public void loadBoardCards(){
		
		currentPos = new Coordonnees(0, 0);
		for(int lig = 0;lig<cardBoard.length; lig++){
			for(int col=0; col<cardBoard[lig].length;col++){
				

				int x = leftPos+(col*sizeWidthCard)+gap;
				int y = topPos+(lig*sizeHeightCard)+gap;
				if(lig == cardBoard.length-1 && col == cardBoard[lig].length-1){
					cardBoard[lig][col] = new GuardianCard(this, new Rectangle(x,y,sizeWidthCard, sizeHeightCard), x, y, new Coordonnees(lig, col), zone == zonePerStage[stage-1]);
				}else{
					cardBoard[lig][col] = randomCard(x, y, new Coordonnees(lig, col));
				}
			}
			
			
		}
		currentCard = cardBoard[0][0];
		currentPos = new Coordonnees(0, 0);
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
		}else if (gameState == playState && !selectedClass.replacePotionBox){
			movementOnTheBoard();

			if(keyH.escapePressed){
				gameState = menuState;
			}

			if(diceButton.isClicked() ^ keyH.spacePressed && !diceHasRolled){ // lancé de dé
				if(!perceptionEffect){
					for(CharacterDice d : characterDices){
						d.roll();
					}
				}
				perceptionEffect = false;
				

				if(curseDice != null){
					curseDice.roll();
					if(curseDice.value == 2){
						curseDice.curseEffect();
					}
				}

				if(poisonDice != null){
					poisonDice.roll();
					if(poisonDice.value == 2 ){
						poisonDice.poisonEffect();
					}
				}

				dungeonDice.roll();


				diceHasRolled = true;
				canMove = true;
				keyH.spacePressed = false;
			}

			if(currentCard.isFinish && !currentCard.coord.equals(new Coordonnees(2, 2)) && !selectedClass.blindness){
				revealNextCard();
			}


			selectedClass.update();

			currentCard.update();

		}else if(gameState == loseState){
			lose.update();
		}else if(gameState == winState){
			win.update();
		}
		
		
	}


	public void movementOnTheBoard(){
		for(int lig=0;lig<cardBoard.length;lig++){
			for(int col=0;col<cardBoard[lig].length;col++){
				if(cardBoard[lig][col].isClicked() && moveIsOk(new Coordonnees(lig, col)) && canMove){// 
					cardBoard[lig][col].revealCard();
					
					currentPos.ligne = lig;
					currentPos.colonne = col;
					canMove = false;
					diceHasRolled = false;

					currentCard = cardBoard[currentPos.ligne][currentPos.colonne];
					if(currentCard instanceof EnnemyCard || currentCard instanceof GuardianCard){
						inFight = true;
					}else{
						inFight = false;
					}

					if(lig == cardBoard.length-1 && col == cardBoard[lig].length - 1){
						goDownstair();
					}
					
				}
			}
		}
	}

	public void goDownstair(){
		canMove = false;
		diceHasRolled = false;
		
		if(zone == zonePerStage[stage-1] && stage < totalStage){
			if(!inFight){
				currentPos.ligne = 0;
				currentPos.colonne = 0;
				stage ++;
				zone = 1;
				loadBoardCards();
			}
		}else if(zone < zonePerStage[stage-1]){
			currentPos.ligne = 0;
			currentPos.colonne = 0;
			if(selectedClass.stats.get(selectedClass.foodString) > 0){
				selectedClass.substractStat(selectedClass.foodString, 1);
			}
			if(selectedClass.stats.get(selectedClass.foodString) == 0 ){
				selectedClass.substractStat(selectedClass.lifeString, 3);
			}
			zone ++;
			loadBoardCards();
		}else if(stage == totalStage && !inFight){
			gameState = winState;
		}
		currentCard = cardBoard[currentPos.ligne][currentPos.colonne];
		
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		if(gameState == menuState){
			menu.draw(g2);
		}else if(gameState == playState){
			g2.setFont(Game.defaultFont);
			gui.draw(g2);

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
				if(cardHovered.isReveal){ // le subtefurge AKA la solution D
					g2.setColor(Color.black);
					g2.fillRect(0, gui.yChoice+15, gui.xLine, this.getHeight());
				}
				cardHovered.draw(g2);
			}

			int yDice = 10;

			dungeonDice.draw(g2, leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)+(getWidth()-(leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)))/2 - (int)Utils.textToRectangle2D(dungeonDice.name, g2).getWidth()/2, topPos + 50 + yDice);
			yDice += Utils.textToRectangle2D(dungeonDice.name, g2).getHeight() + Utils.diceSize  + gap*2;

			for(CharacterDice d : characterDices){
				d.draw(g2, leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)+(getWidth()-(leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)))/2 - (int)Utils.textToRectangle2D(d.name, g2).getWidth()/2, topPos + 50 + yDice);
				yDice += Utils.textToRectangle2D(d.name, g2).getHeight() + Utils.diceSize  + gap*2;
			}

			

			if(curseDice != null){
				curseDice.draw(g2, leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)+(getWidth()-(leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)))/2 - (int)Utils.textToRectangle2D(curseDice.name, g2).getWidth()/2, topPos + 50 + yDice);
				yDice += Utils.textToRectangle2D(curseDice.name, g2).getHeight() + Utils.diceSize  + gap*2;
			}
			
			if(poisonDice != null){
				poisonDice.draw(g2, leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)+(getWidth()-(leftPos+(sizeWidthCard*cardBoard.length)+(gap*cardBoard.length-1)))/2 - (int)Utils.textToRectangle2D(poisonDice.name, g2).getWidth()/2, topPos + 50 + yDice);
			}
			
			
			g2.drawImage(selectedClass.icon, currentCard.hitbox.x + currentCard.hitbox.width - 80,
			currentCard.hitbox.y + currentCard.hitbox.height - 80, 64, 64, null);

			diceButton.draw(g2);

			if(currentCard instanceof MerchantCard){
				MerchantCard m = (MerchantCard)currentCard;
				if(m.haveToChoicePotion){
					m.choicePotion(g2);
				}
			}

			selectedClass.draw(g2);
		}else if(gameState == loseState){
			lose.draw(g2);
		}else if(gameState == winState){
			win.draw(g2);
		}
	}

	public void revealNextCard(){
		Coordonnees coordRight = new Coordonnees(currentPos.ligne, currentPos.colonne+1);
		Coordonnees coordBot = new Coordonnees(currentPos.ligne+1, currentPos.colonne);

		if(coordBot.estDansPlateau()){
			cardBoard[coordBot.ligne][coordBot.colonne].revealCard();
		}
		if(coordRight.estDansPlateau()){
			cardBoard[coordRight.ligne][coordRight.colonne].revealCard();
		}
		if(coordBot.estDansPlateau() && coordBot.equals(new Coordonnees(2, 2)) && zone != zonePerStage[stage-1]){
			cardBoard[coordBot.ligne][coordBot.colonne].unrevealCard();
		}
		if(coordRight.estDansPlateau() && coordRight.equals(new Coordonnees(2, 2)) && zone != zonePerStage[stage-1]){
			cardBoard[coordRight.ligne][coordRight.colonne].unrevealCard();
		}
	}

	public boolean moveIsOk(Coordonnees coord){
		boolean isOk = false;
		if(coord.estDansPlateau() && coord.ligne - 1 == currentPos.ligne && coord.colonne == currentPos.colonne || coord.ligne == currentPos.ligne && coord.colonne -1 == currentPos.colonne){
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
				returnedCard = new EnnemyCard(this, new Rectangle(x, y, sizeWidthCard, sizeHeightCard), x, y, coord);
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
