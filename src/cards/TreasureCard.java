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
import main.RewardOrPenalty;
import main.Utils;
import potions.FirePotion;
import potions.FrostedPotion;
import potions.LifePotion;
import potions.PerceptionPotion;
import potions.PoisonPotion;

public class TreasureCard extends UpdateOnRoll{

	String result = "Lancez les dé ...";
	RewardOrPenalty[] rewards = new RewardOrPenalty[6];

	public TreasureCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Trésor";
		image = Utils.loadImage("assets/cards/cardYellow.png");
		needSkillTest = true;
		setupRewards();
	}

	public void setupRewards(){
		int rng = Utils.randomNumber(0, 1);
		if(rng == 0){
			rewards[0] = new RewardOrPenalty(game, game.selectedClass.armorString, 1);
			rewards[1] = new RewardOrPenalty(game, game.selectedClass.armorString, 1);
			rewards[2] = new RewardOrPenalty(game, new FirePotion(game));
			rewards[3] = new RewardOrPenalty(game, new PoisonPotion(game));
			rewards[4] = new RewardOrPenalty(game, new LifePotion(game));
			rewards[5] = new RewardOrPenalty(game, new FrostedPotion(game));
		}else if(rng == 1){
			rewards[0] = new RewardOrPenalty(game, game.selectedClass.xpString, 2);
			rewards[1] = new RewardOrPenalty(game, game.selectedClass.moneyString, 1);
			rewards[2] = new RewardOrPenalty(game, game.selectedClass.moneyString, 2);
			rewards[3] = new RewardOrPenalty(game, new PerceptionPotion(game));
			rewards[4] = new RewardOrPenalty(game, game.selectedClass.xpString, 2);
			rewards[5] = new RewardOrPenalty(game, game.selectedClass.xpString, 2); // autre
		}
		
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
				
				case 1:
					rewards[0].reward();
					result += rewards[0].result;
					break;
				case 2:
					rewards[1].reward();
					result += rewards[1].result;
					break;
				case 3:
					rewards[2].reward();
					result += rewards[2].result;
					break;
				case 4:
					rewards[3].reward();
					result += rewards[3].result;
					break;
				case 5:
					rewards[4].reward();
					result += rewards[4].result;
					break;
				case 6:
					rewards[5].reward();
					result += rewards[5].result;
					break;
			}
			isFinish = true;
		}else{
			result = "Échec ... Vous avez échoué au test de compétence";
		}
	}


	@Override
	public void drawAdditional(Graphics2D g2) {

		Utils.drawDice(g2, 10, game.choicePlaceY, 1);
		g2.drawString(rewards[0].rewardString, 40, game.choicePlaceY+(int)Utils.textToRectangle2D(rewards[0].rewardString, g2).getHeight());

		Utils.drawDice(g2, 10, game.choicePlaceY + 30, 2);
		g2.drawString(rewards[1].rewardString, 40, game.choicePlaceY+30+(int)Utils.textToRectangle2D(rewards[1].rewardString, g2).getHeight());

		Utils.drawDice(g2, 10, game.choicePlaceY+60, 3);
		g2.drawString(rewards[2].rewardString, 40, game.choicePlaceY+60+(int)Utils.textToRectangle2D(rewards[2].rewardString, g2).getHeight());

		Utils.drawDice(g2, 30 + (int)Utils.textToRectangle2D(rewards[0].rewardString, g2).getWidth() + 20, game.choicePlaceY, 4);
		g2.drawString(rewards[3].rewardString, 30 + (int)Utils.textToRectangle2D(rewards[0].rewardString, g2).getWidth() + 50, game.choicePlaceY+(int)Utils.textToRectangle2D(rewards[3].rewardString, g2).getHeight());

		Utils.drawDice(g2,  30 + (int)Utils.textToRectangle2D(rewards[1].rewardString, g2).getWidth() + 20, game.choicePlaceY+30, 5);
		g2.drawString(rewards[4].rewardString, 30 + (int)Utils.textToRectangle2D(rewards[1].rewardString, g2).getWidth() + 50, game.choicePlaceY+30+(int)Utils.textToRectangle2D(rewards[4].rewardString, g2).getHeight());

		Utils.drawDice(g2,  30 + (int)Utils.textToRectangle2D(rewards[2].rewardString, g2).getWidth() + 20, game.choicePlaceY+60, 6);
		g2.drawString(rewards[5].rewardString, 30 + (int)Utils.textToRectangle2D(rewards[2].rewardString, g2).getWidth() + 50, game.choicePlaceY+60+(int)Utils.textToRectangle2D(rewards[5].rewardString, g2).getHeight());

		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+130);
	}
	
}
