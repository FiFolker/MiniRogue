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
import potions.FirePotion;
import potions.FrostedPotion;
import potions.LifePotion;
import potions.PerceptionPotion;
import potions.PoisonPotion;
import rewardAndPenalty.Reward;
import rewardAndPenalty.RewardAndPenalty;

public class TreasureCard extends UpdateOnRoll{

	String defaultRewardString = "";
	RewardAndPenalty[] rewards = new RewardAndPenalty[6];
	Reward defaultReward;

	public TreasureCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Trésor";
		image = Utils.loadImage("assets/cards/cardYellow.png");
		needSkillTest = true;
		defaultReward = new Reward(game, game.selectedClass.moneyString, 1);
		setupRewards();
	}

	public void setupRewards(){
		int rng = Utils.randomNumber(0, 1);
		if(rng == 0){
			rewards[0] = new Reward(game, game.selectedClass.armorString, 1);
			rewards[1] = new Reward(game, game.selectedClass.armorString, 1);
			rewards[2] = new Reward(game, new FirePotion(game));
			rewards[3] = new Reward(game, new PoisonPotion(game));
			rewards[4] = new Reward(game, new LifePotion(game));
			rewards[5] = new Reward(game, new FrostedPotion(game));
		}else if(rng == 1){
			rewards[0] = new Reward(game, game.selectedClass.xpString, 2);
			rewards[1] = new Reward(game, game.selectedClass.moneyString, 1);
			rewards[2] = new Reward(game, game.selectedClass.moneyString, 2);
			rewards[3] = new Reward(game, new PerceptionPotion(game));
			rewards[4] = new Reward(game, game.selectedClass.xpString, 2);
			rewards[5] = new Reward(game, game.selectedClass.xpString, 2); // autre
		}
		
	}

	@Override
	public void updateOnRoll(){
		boolean testSkill = false;
		defaultReward.rewardOrPenalty();
		defaultRewardString = defaultReward.result;
		for(CharacterDice d : game.characterDices){
			if(d.testSkill() && !testSkill){
				testSkill = true;
			}
		}
		if(testSkill){
			result = "Vous avez gagné ";
			
			switch(game.dungeonDice.value){
				
				case 1:
					rewards[0].rewardOrPenalty();
					result += rewards[0].result + " ";
					break;
				case 2:
					rewards[1].rewardOrPenalty();
					result += rewards[1].result;
					break;
				case 3:
					rewards[2].rewardOrPenalty();
					result += rewards[2].result;
					break;
				case 4:
					rewards[3].rewardOrPenalty();
					result += rewards[3].result;
					break;
				case 5:
					rewards[4].rewardOrPenalty();
					result += rewards[4].result;
					break;
				case 6:
					rewards[5].rewardOrPenalty();
					result += rewards[5].result;
					break;
			}
		}else{
			result = "Échec ... Vous avez échoué au test de compétence";
		}
		isFinish = true;

	}


	@Override
	public void drawAdditional(Graphics2D g2) {

		Utils.drawSixDicePossibilities(game, g2, new String[]{rewards[0].result, rewards[1].result, rewards[2].result, rewards[3].result, rewards[4].result, rewards[5].result});

		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+130);
		g2.drawString(defaultRewardString, game.choicePlaceX-(int)Utils.textToRectangle2D(defaultRewardString, g2).getWidth()/2, game.choicePlaceY+150);
	}
	
}
