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
import main.RewardOrPenalty;
import main.Utils;

public class SanctuaryCard extends UpdateAlways{

	String[] possibilitiesString = new String[6];
	Button payPiece;

	RewardOrPenalty[] rewards = new RewardOrPenalty[3];
	RewardOrPenalty[] penalties = new RewardOrPenalty[3];
	private boolean hasPayed;

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
		possibilitiesString[0] = "Honni soyez-vous !";
		possibilitiesString[1] = "Malédiction !";
		possibilitiesString[2] = "Pauvre mortel";
		possibilitiesString[3] = "Terrible esprit !";
		possibilitiesString[4] = "Un coeur sombre";
		possibilitiesString[5] = "petite foi";

		rewards[0] = new RewardOrPenalty(game, game.selectedClass.lifeString, 2);
		rewards[0].addEffect(new Blessing(game));
		rewards[1] = new RewardOrPenalty(game, game.selectedClass.lifeString, 1);
		rewards[2] = new RewardOrPenalty(game, game.selectedClass.lifeString, 1);

		ArrayList<Dice> penalties1Dices = new ArrayList<>();
		penalties1Dices.add(new CurseDice(game));
		penalties1Dices.add(new PoisonDice(game));
		penalties[0] = new RewardOrPenalty(game, penalties1Dices);
		penalties[1] = new RewardOrPenalty(game, new CurseDice(game));
		penalties[2] = new RewardOrPenalty(game, new CurseDice(game));
	}

	@Override
	public void updateAlways() {
		result = "";
		if(game.currentPos.equals(this.coord) && !isFinish && game.diceHasRolled){
			if(hasPayed){
				game.dungeonDice.value ++;
			}
			switch(game.dungeonDice.value){
				case 1:
					penalties[0].penalty();
					result += penalties[0].result + " ";
					break;
				case 2:
					penalties[1].penalty();
					result += penalties[1].result;
					break;
				case 3:
					penalties[2].penalty();
					result += penalties[2].result;
					break;
				case 4:
					rewards[0].reward();
					result += rewards[0].result;
					break;
				case 5:
					rewards[1].reward();
					result += rewards[1].result;
					break;
				case 6:
					rewards[2].reward();
					result += rewards[2].result;
					break;
			}

			isFinish = true;
		}
		if(!isFinish){
			if(payPiece.isClicked() && game.selectedClass.stats.get(game.selectedClass.moneyString) > 0 && !payPiece.isSelected){
				game.selectedClass.substractStat(game.selectedClass.moneyString, 1);
				payPiece.isSelected = true;
				hasPayed = true;
			}else if(payPiece.isClicked() && game.selectedClass.stats.get(game.selectedClass.moneyString) <= 0 && !payPiece.isSelected){
				ErrorDraw.errorState = true;
			}
		}
	}

	@Override
	public void drawAdditional(Graphics2D g2) {
		Utils.drawSixDicePossibilities(game, g2, new String[]{penalties[0].penaltyString, penalties[1].penaltyString, penalties[2].penaltyString, rewards[0].rewardString, rewards[1].rewardString, rewards[2].rewardString});
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+130);
		payPiece.draw(g2);
		if(ErrorDraw.errorState){
			ErrorDraw.draw(g2, "Vous devez avoir de l'argent pour effectuer cette action", game);
		}

	}



	
	
}
