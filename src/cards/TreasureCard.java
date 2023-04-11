package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dices.CharacterDice;
import dices.Dice;
import dices.DungeonDice;
import main.Coordonnees;
import main.Game;
import main.Utils;

public class TreasureCard extends UpdateOnRoll{

	String result = "Lancez les dé ...";

	public TreasureCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Trésor";
		image = Utils.loadImage("assets/cards/cardYellow.png");
		needSkillTest = true;
	}

	@Override
	public void updateOnRoll(){
		boolean testSkill = false;
		for(CharacterDice d : game.characterDices){
			if(d.testSkill() && !testSkill){
				testSkill = true;
			}
		}
		if(testSkill){
			result = "Vous avez gagné ";
			
			switch(game.dungeonDice.value){
				case 3:
				case 1:
					game.selectedClass.addStat(game.selectedClass.xpString, 2);
					result += "+2 d'xp";
					break;
				case 2:
					game.selectedClass.addStat(game.selectedClass.moneyString, 2);
					result += "+2 de gold";
					break;
				case 4:
					game.selectedClass.addStat(game.selectedClass.armorString, 3);
					result += "+3 d'armure";
					break;
				case 5:
					game.selectedClass.addStat(game.selectedClass.foodString, 2);
					result += "+2 de nourriture ";
					break;
				case 6:
					game.selectedClass.addStat(game.selectedClass.moneyString, 8);
					result += "+8 de gold";
					break;
			}
			isFinish = true;
		}else{
			result = "Échec ... Vous avez échoué au test de compétence";
		}
	}


	@Override
	public void drawAdditional(Graphics2D g2) {
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY);
	}
	
}
