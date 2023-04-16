package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Coordonnees;
import main.ErrorDraw;
import main.Game;
import main.Utils;
import potions.HolyWater;
import rewardAndPenalty.Penalty;
import rewardAndPenalty.Reward;
import rewardAndPenalty.RewardAndPenalty;

public class BurialCard extends UpdateAlways{

	RewardAndPenalty[] rewardsOrPenalties = new RewardAndPenalty[5];
	ErrorDraw errorDraw;

	public BurialCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		image = Utils.loadImage("assets/cards/cardGray.png");
		name = "Carte Sépulture";
		setupResult();
		errorDraw = new ErrorDraw();
	}

	private void setupResult() {
		rewardsOrPenalties[0] = new Reward(game, new HolyWater(game));
		rewardsOrPenalties[1] = new Reward(game, game.selectedClass.xpString, 1);
		rewardsOrPenalties[2] = new Reward(game, game.selectedClass.moneyString, 1);
		rewardsOrPenalties[3] = new Reward(game, game.selectedClass.foodString, 1);
		rewardsOrPenalties[4] = new Penalty(game, game.selectedClass.foodString, 1);
	}

	@Override
	public void updateAlways() {
		result = "";
		if(game.currentPos.equals(this.coord) && !isFinish && game.diceHasRolled){
			rewardsOrPenalties[game.dungeonDice.value-1].rewardOrPenalty();
			result += rewardsOrPenalties[game.dungeonDice.value-1].result + " ";
			isFinish = true;
		}
	}

	@Override
	public void drawAdditional(Graphics2D g2) {
		Utils.drawSixDicePossibilities(game, g2, new String[]{rewardsOrPenalties[0].result, rewardsOrPenalties[1].result, rewardsOrPenalties[2].result, rewardsOrPenalties[3].result, rewardsOrPenalties[4].result, "Fantôme"});
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+130);
		if(errorDraw.errorState){
			errorDraw.draw(g2, "Vous devez avoir de l'argent pour effectuer cette action", game);
		}
	}

   
	
}
