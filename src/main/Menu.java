package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.HashMap;

import potions.FirePotion;

public class Menu implements IUpdateAndDraw{

	Game game;

	HashMap<String, Button> buttons = new HashMap<>();
	private final String playButton = "playButton";
	private final String exitButton = "exitButton";
	private final String rightButton = "rightButton";
	private final String leftButton = "leftButton";
	private final String helpButton = "helpButton";
	private Dimension sizeOfButton = new Dimension(200,25);


	public Menu(Game game){
		this.game = game;
		setup();
	}

	public void setup(){
		Rectangle playRect = new Rectangle(632-sizeOfButton.width/2, 470 - sizeOfButton.height/2, sizeOfButton.width, sizeOfButton.height);
		Rectangle helpRect = new Rectangle(632-sizeOfButton.width/2, 470 - sizeOfButton.height/2+ sizeOfButton.height + 10, sizeOfButton.width, sizeOfButton.height);
		Rectangle exitRect = new Rectangle(632-sizeOfButton.width/2, 470- sizeOfButton.height/2 + sizeOfButton.height*2 + 20, sizeOfButton.width, sizeOfButton.height);
		buttons.put(playButton, new Button(playRect, "Jouer"));
		buttons.put(helpButton, new Button(helpRect, "Aide"));
		buttons.put(exitButton, new Button(exitRect, "Quitter"));

		Rectangle leftRect = new Rectangle(634-80, 600, 16, game.classes[game.currentClasse].size);
		Rectangle rightRect = new Rectangle(634+64, 600, 16, game.classes[game.currentClasse].size);
		buttons.put(leftButton, new Button(leftRect, new Polygon(new int[]{leftRect.x+leftRect.width, leftRect.x, leftRect.x+leftRect.width}, new int[]{leftRect.y, leftRect.y+leftRect.height/2, leftRect.y+leftRect.height}, 3)));
		buttons.put(rightButton, new Button(rightRect, new Polygon(new int[]{rightRect.x, rightRect.x+rightRect.width, rightRect.x}, new int[]{rightRect.y, rightRect.y+rightRect.height/2, rightRect.y+rightRect.height}, 3)));
	}

	@Override
	public void update(){
		for(String s: buttons.keySet()){
			if(buttons.get(s).isClicked()){
				
				switch(s){
					case playButton:
						game.gameState = game.playState;
						game.selectedClass = game.classes[game.currentClasse];
						game.selectedClass.potions[0] = new FirePotion(game);
						game.selectedClass.potions[1] = new FirePotion(game);
						game.loadGame();
						game.loadBoardCards();
						break;
					case exitButton:
						System.exit(0);
						break;
					case helpButton:
						break;
					case rightButton:
						if(game.currentClasse+1 > game.classes.length-1){
							game.currentClasse = 0;
						}else{
							game.currentClasse ++;
						}
						break;
					case leftButton:
						if(game.currentClasse-1 < 0){
							game.currentClasse = game.classes.length-1;
						}else{
							game.currentClasse --;
						}
						break;
				}
				Game.mouseH.leftClickedOnceTime = false;
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2){
		g2.setFont(game.title);
		g2.drawString("MINI ROGUE", Utils.getXforCenteredText("MINI ROGUE", g2), 400);
		g2.setFont(game.sansSerif);
		game.classes[game.currentClasse].drawIcon(g2, game.getWidth()/2 - game.classes[game.currentClasse].size/2, 600);
		for(Button b : buttons.values()){
			b.draw(g2);
		}
	}
	
}
