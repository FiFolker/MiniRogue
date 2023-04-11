package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import dices.CharacterDice;
import dices.Dice;
import dices.PoisonDice;
import main.Coordonnees;
import main.Game;
import main.Utils;

public class TrapCard extends UpdateOnRoll{

	String case1;
	String case2;
	String case3;
	String[] key = new String[3];
	int[] penalty = new int[3];
	String[] rewardKey = new String[3];
	int[] rewardValue = new int[3];
	Dice applicableDice = null;
	int caseApplicableDice;
	boolean isTrap = false;
	boolean isFall = false;

	String result = " ";
	String details  = "";

	public TrapCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Piège";
		image = Utils.loadImage("assets/cards/cardBlue.png");
		
		setup();

	}

	public void setup(){ // TO DO THE POTION EFFECT
		int rng = Utils.randomNumber(0, 1);
		if(rng == 0){
			case1 = "Pics vénéneux -1 PV et Poison | +1 XP";
			case2 = "Plancher piégé -" + game.stage + " PV | +2 XP";
			case3 = "Trappe -3 PV et -1 Zone | +1 Perception";

			key[0] = game.selectedClass.lifeString;
			key[1] = game.selectedClass.lifeString;
			key[2] = game.selectedClass.lifeString;

			penalty[0] = 1;
			penalty[1] = game.stage;
			penalty[2] = 3;
			
			rewardValue[0] = 1;
			rewardValue[1] = 2;
			rewardValue[2] = 1;

			rewardKey[0] = game.selectedClass.xpString;
			rewardKey[1] = game.selectedClass.xpString;
			rewardKey[2] = game.selectedClass.xpString;
			caseApplicableDice = 1;
			isFall = true;
			applicableDice = new PoisonDice(game);
		}else if(rng == 1){
			case1 = "Horde de rats -"+game.stage+" Nourriture | +1 Eau Bénite";
			case2 = "Brume acide -1 Armure | +1 Armure";
			case3 = "Pendules -"+game.stage+" PV et Poison | +1 Perception";

			key[0] = game.selectedClass.foodString;
			key[1] = game.selectedClass.armorString;
			key[2] = game.selectedClass.lifeString;

			penalty[0] = game.stage;
			penalty[1] = 1;
			penalty[2] = game.stage;

			rewardValue[0] = 1;
			rewardValue[1] = 1;
			rewardValue[2] = 1;

			rewardKey[0] = game.selectedClass.xpString;
			rewardKey[1] = game.selectedClass.armorString;
			rewardKey[2] = game.selectedClass.xpString;
			caseApplicableDice = 3;
			applicableDice = new PoisonDice(game);
		}
	}

	@Override
	public void updateOnRoll() {
		boolean testSkill = false;
		for(CharacterDice d : game.characterDices){
			if(d.testSkill() && !testSkill){
				testSkill = true;
			}
		}
		if(!testSkill){
			isTrap = true;
			result = "Vous êtes tombé dans le piège :";
			switch(game.dungeonDice.value){
				case 1:
				case 2:
					details += "-" + penalty[0] + " " + key[0];
					applicateDice(1);
					game.selectedClass.substractStat(key[0], penalty[0]);
					break;
				case 3:
				case 4:
					details += "-" + penalty[1] + " " + key[1];
					applicateDice(2);
					game.selectedClass.substractStat(key[1], penalty[1]);
					break;
				case 5:
				case 6:
					details += "-" + penalty[2] + " " + key[2];
					if(isFall){
						details += " -1 Zone";
						game.goDownstair();
					}

					game.selectedClass.substractStat(key[2], penalty[2]);
					applicateDice(3);
					break;
			}
		}else{
			result = "Vous avez évité le piège :";
			switch(game.dungeonDice.value){
				case 1:
				case 2:
					details += "+" + rewardValue[0] + " " + rewardKey[0];
					game.selectedClass.addStat(rewardKey[0], rewardValue[0]);
					break;
				case 3:
				case 4:
					details += "+" + rewardValue[1] + " " + rewardKey[1];
					game.selectedClass.addStat(rewardKey[1], rewardValue[1]);

					break;
				case 5:
				case 6:
					details += "+" + rewardValue[2] + " " + rewardKey[2];
					game.selectedClass.addStat(rewardKey[2], rewardValue[2]);
					break;
			}
		}
		isFinish = true;
		
	}

	public void applicateDice(int caseApp){
		if(applicableDice != null && applicableDice instanceof PoisonDice && caseApplicableDice == caseApp){
			game.poisonDice = (PoisonDice)applicableDice;
			details += " +1 dé de poison";
		}
	}

	@Override
	public void drawAdditional(Graphics2D g2) {

		Utils.drawDice(g2, 10, game.choicePlaceY, 1);
		Utils.drawDice(g2, 35, game.choicePlaceY, 2);
		
		g2.drawString(case1, 60, game.choicePlaceY+(int)Utils.textToRectangle2D(case1, g2).getHeight());

		Utils.drawDice(g2, 10, game.choicePlaceY+30, 3);
		Utils.drawDice(g2, 35, game.choicePlaceY+30, 4);

		g2.drawString(case2, 60, game.choicePlaceY+30+(int)Utils.textToRectangle2D(case2, g2).getHeight());


		Utils.drawDice(g2, 10, game.choicePlaceY+60, 5);
		Utils.drawDice(g2, 35, game.choicePlaceY+60, 6);

		g2.drawString(case3, 60, game.choicePlaceY+60+(int)Utils.textToRectangle2D(case3, g2).getHeight());

		if(isTrap){
			g2.setColor(Color.red);
		}else{
			g2.setColor(Color.green);
		}
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+110);
		g2.drawString(details, game.choicePlaceX-(int)Utils.textToRectangle2D(details, g2).getWidth()/2, game.choicePlaceY+130);
	}
	
}
