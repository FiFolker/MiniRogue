package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dices.CurseDice;
import dices.Dice;
import dices.PoisonDice;
import effect.Blessing;
import main.Button;
import main.Coordonnees;
import main.ErrorDraw;
import main.Game;
import main.Utils;
import rewardAndPenalty.Penalty;
import rewardAndPenalty.Reward;
import rewardAndPenalty.RewardAndPenalty;

public class SanctuaryCard extends UpdateAlways{

	String[] possibilitiesString = new String[6];
	Button payPiece;

	RewardAndPenalty[] rewardsOrPenalties = new RewardAndPenalty[6];
	private boolean hasPayed;
	ErrorDraw errorDraw = new ErrorDraw();

	public SanctuaryCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Sanctuaire";
		image = Utils.loadImage("assets/cards/cardBlue.png");
		setupRewardAndPenalties();
		setupButton();
	}

	private void setupButton() {
		payPiece = new Button(new Rectangle(game.choicePlaceX-50, game.choicePlaceY+160, 100, 20), "Payer 1 pièce");
		payPiece.toolTipMessage = "Payer 1 pièce pour ajouter 1 au dé donjon";
	}

	private void setupRewardAndPenalties(){

		ArrayList<Dice> penalties1Dices = new ArrayList<>();
		penalties1Dices.add(new CurseDice(game));
		penalties1Dices.add(new PoisonDice(game));
		rewardsOrPenalties[0] = new Penalty(game, penalties1Dices);
		rewardsOrPenalties[1] = new Penalty(game, new CurseDice(game));
		rewardsOrPenalties[2] = new Penalty(game, new CurseDice(game));

		rewardsOrPenalties[3] = new Reward(game, game.selectedClass.lifeString, 2);
		rewardsOrPenalties[3].addEffect(new Blessing(game));
		rewardsOrPenalties[4] = new Reward(game, game.selectedClass.lifeString, 1);
		rewardsOrPenalties[5] = new Reward(game, game.selectedClass.lifeString, 1);

		
	}

	@Override
	public void updateAlways() {
		result = "";
		if(game.currentPos.equals(this.coord) && !isFinish && game.diceHasRolled){
			if(hasPayed){
				game.dungeonDice.value ++;
			}
			
			rewardsOrPenalties[game.dungeonDice.value-1].rewardOrPenalty();
			result += rewardsOrPenalties[game.dungeonDice.value-1].result + " ";
			isFinish = true;
		}
		if(!isFinish){
			if(payPiece.isClicked() && game.selectedClass.stats.get(game.selectedClass.moneyString) > 0 && !payPiece.isSelected){
				game.selectedClass.substractStat(game.selectedClass.moneyString, 1);
				payPiece.isSelected = true;
				hasPayed = true;
			}else if(payPiece.isClicked() && game.selectedClass.stats.get(game.selectedClass.moneyString) <= 0 && !payPiece.isSelected){
				errorDraw.errorState = true;
			}
		}
	}

	@Override
	public void drawAdditional(Graphics2D g2) {
		Utils.drawSixDicePossibilities(game, g2, new String[]{rewardsOrPenalties[0].result, rewardsOrPenalties[1].result, rewardsOrPenalties[2].result, rewardsOrPenalties[3].result, rewardsOrPenalties[4].result, rewardsOrPenalties[5].result});
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+130);
		payPiece.draw(g2);
		if(errorDraw.errorState){
			errorDraw.draw(g2, "Vous devez avoir de l'argent pour effectuer cette action", game);
		}

	}



	
	
}
