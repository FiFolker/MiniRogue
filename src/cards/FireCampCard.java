package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import dices.Dice;
import main.Button;
import main.Coordonnees;
import main.Game;
import main.Utils;

public class FireCampCard extends UpdateAlways{

	HashMap<String, Button> listButtons;
	final String xpButton = "xpButton";
	final String foodButton = "foodButton";
	final String healButton = "healButton";
	int width = 200;
	int height = 50;
	int gap = 10;
	public FireCampCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		image = Utils.loadImage("assets/cards/cardBlue.png");
		name = "Carte Feu de Camp";
		listButtons = new HashMap<>();
		setup();
	}

	public void setup(){
		Rectangle xpRect = new Rectangle(game.gui.xLine/2-width/2, game.gui.yChoice+30+height*listButtons.size()-1, width, height);
		listButtons.put(xpButton, new Button(xpRect, "Prendre une arme +1 XP"));

		Rectangle foodRect = new Rectangle(game.gui.xLine/2-width/2, game.gui.yChoice+30+height*listButtons.size()-1+gap*listButtons.size()-1, width, height);
		listButtons.put(foodButton, new Button(foodRect, "Prendre la viande +1 Nourriture"));

		Rectangle healRect = new Rectangle(game.gui.xLine/2-width/2, game.gui.yChoice+30+height*listButtons.size()-1+gap*listButtons.size()-1, width, height);
		listButtons.put(healButton, new Button(healRect, "Potion de soins +2 HP"));
	}

	@Override
	public void updateAlways(){
		game.diceHasRolled = true;
		for(String str : listButtons.keySet()){
			if(listButtons.get(str).isClicked()){
				listButtons.get(str).isSelected = true;
				switch(str){
					case xpButton:
						game.selectedClass.addStat(game.selectedClass.xpString, 1);
						break;
					case foodButton:
						game.selectedClass.addStat(game.selectedClass.foodString, 1);
						break;
					case healButton:
						game.selectedClass.addStat(game.selectedClass.lifeString, 2);
						break;
				}
				hasTakenReward = true;
				game.canMove = true;
				Game.mouseH.leftClickedOnceTime = false;
			}
		}
	}

	@Override
	public void drawAdditional(Graphics2D g2, int x, int y) {
		for(Button b : listButtons.values()){
			b.draw(g2);
		}
	}

}
