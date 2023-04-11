package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dices.CharacterDice;
import dices.Dice;
import dices.PoisonDice;
import main.Coordonnees;
import main.Game;
import main.RewardOrPenalty;
import main.Utils;
import potions.HolyWater;
import potions.PerceptionPotion;
import potions.Potion;

public class TrapCard extends UpdateOnRoll{

	String case1;
	String case2;
	String case3;
	boolean isTrap = false;
	boolean isFall = false;
	RewardOrPenalty[] rewards = new RewardOrPenalty[3];
	RewardOrPenalty[] penalties = new RewardOrPenalty[3];

	String details  = "";

	public TrapCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Piège";
		image = Utils.loadImage("assets/cards/cardBlue.png");
		needSkillTest = true;
		setup();

	}

	public void setup(){ // TO DO THE POTION EFFECT
		int rng = Utils.randomNumber(0, 1);
		if(rng == 0){
			case1 = "Pics vénéneux -1 PV et Poison | +1 XP";
			case2 = "Plancher piégé -" + game.stage + " PV | +2 XP";
			case3 = "Trappe -3 PV et -1 Zone | +1 Perception";

			rewards[0] = new RewardOrPenalty(game, game.selectedClass.xpString, 1);
			rewards[1] = new RewardOrPenalty(game, game.selectedClass.xpString, 1);
			rewards[2] = new RewardOrPenalty(game, new PerceptionPotion(game));

			penalties[0] = new RewardOrPenalty(game, new PoisonDice(game), game.selectedClass.lifeString, 1);
			penalties[1] = new RewardOrPenalty(game, game.selectedClass.lifeString, game.stage);
			penalties[2] = new RewardOrPenalty(game, game.selectedClass.lifeString, 3);

			isFall = true;
		}else if(rng == 1){
			case1 = "Horde de rats -"+game.stage+" Nourriture | +1 Eau Bénite";
			case2 = "Brume acide -1 Armure | +1 Armure";
			case3 = "Pendules -"+game.stage+" PV et Poison | +1 Perception";

			rewards[0] = new RewardOrPenalty(game, new HolyWater(game));
			rewards[1] = new RewardOrPenalty(game, game.selectedClass.armorString, 1);
			rewards[2] = new RewardOrPenalty(game, new PerceptionPotion(game));

			penalties[0] = new RewardOrPenalty(game, game.selectedClass.foodString, game.stage);
			penalties[1] = new RewardOrPenalty(game, game.selectedClass.armorString, 1);
			penalties[2] = new RewardOrPenalty(game, new PoisonDice(game), game.selectedClass.lifeString, game.stage);

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
					penalties[0].penalty();
					details += penalties[0].result;
					break;
				case 3:
				case 4:
					penalties[1].penalty();
					details += penalties[1].result;
					break;
				case 5:
				case 6:
					penalties[2].penalty();
					details += penalties[2].result;	
					if(isFall){
						details += " -1 Zone";
						game.goDownstair();
					}
					break;
			}
		}else{
			result = "Vous avez évité le piège :";
			switch(game.dungeonDice.value){
				case 1:
				case 2:
					rewards[0].reward();
					details += rewards[0].result;
					break;
				case 3:
				case 4:
					rewards[1].reward();
					details += rewards[1].result;
					break;
				case 5:
				case 6:
					rewards[2].reward();
					details += rewards[2].result;
					break;
			}
		}
		isFinish = true;
		
	}

	@Override
	public void drawAdditional(Graphics2D g2) {

		Utils.drawThreeDicePossibilities(game, g2, new String[]{case1, case2, case3});

		if(isTrap){
			g2.setColor(Color.red);
		}else{
			g2.setColor(Color.green);
		}
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+110);
		g2.drawString(details, game.choicePlaceX-(int)Utils.textToRectangle2D(details, g2).getWidth()/2, game.choicePlaceY+130);

		
	}
	
}
