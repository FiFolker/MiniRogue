package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Button;
import main.Game;
import main.Utils;
import potions.FirePotion;
import potions.FrostedPotion;
import potions.HolyWater;
import potions.LifePotion;
import potions.PerceptionPotion;
import potions.PoisonPotion;
import rewardAndPenalty.Reward;
import rewardAndPenalty.RewardAndPenalty;

public class RewardChoice{
	Game game;
	boolean hasChoiced = false;
	RewardAndPenalty[] rewards = new RewardAndPenalty[6];
	private String result = "Lancez les dés ...";
	Button firstChoice;
	Button secondChoice;
	Rectangle firstRect;
	Rectangle secondRect;
	private Card card;

	public RewardChoice(Game game, Card card) {
		this.game = game;
		this.card = card;

		firstRect = new Rectangle((game.gui.xLine/2)/2 - 62, game.choicePlaceY+220, 125, 30);
		secondRect = new Rectangle((game.gui.xLine/2) + (game.gui.xLine/2)/2 - 62, game.choicePlaceY+220, 125, 30);

		rewards[0] = new Reward(game, new FirePotion(game), game.selectedClass.xpString, 2);
		rewards[1] = new Reward(game, new FrostedPotion(game), game.selectedClass.moneyString, 1);
		rewards[2] = new Reward(game, new PoisonPotion(game), game.selectedClass.xpString, 2);
		rewards[3] = new Reward(game, new LifePotion(game), game.selectedClass.armorString, 1);
		rewards[4] = new Reward(game, new HolyWater(game), game.selectedClass.moneyString, 1);
		rewards[5] = new Reward(game, new PerceptionPotion(game), game.selectedClass.armorString, 1);

	}

	public void update() {
		if(!game.inFight && game.currentPos.equals(card.coord) && !hasChoiced && game.diceHasRolled){
			result = rewards[game.dungeonDice.value-1].result;
			firstChoice = new Button(firstRect, rewards[game.dungeonDice.value-1].potion.name);
			secondChoice = new Button(secondRect, "+ " + rewards[game.dungeonDice.value-1].value + " " + rewards[game.dungeonDice.value-1].key);
		}
		if(firstChoice != null && firstChoice.isClicked() && !hasChoiced){
			rewards[game.dungeonDice.value-1].key = null;
			rewards[game.dungeonDice.value-1].rewardOrPenalty();
			hasChoiced = true;
			firstChoice.isSelected = true;
		}
		if(secondChoice != null && secondChoice.isClicked() && !hasChoiced){
			rewards[game.dungeonDice.value-1].potion = null;
			rewards[game.dungeonDice.value-1].rewardOrPenalty();
			hasChoiced = true;
			secondChoice.isSelected = true;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, game.gui.yChoice, game.gui.xLine, game.getHeight());
		g2.setColor(Color.white);
		Utils.drawSixDicePossibilitiesInCol(game, g2, new String[]{rewards[0].result, rewards[1].result, rewards[2].result, rewards[3].result, rewards[4].result, rewards[5].result});
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+200);
		if(firstChoice != null && secondChoice != null){
			firstChoice.draw(g2);
			secondChoice.draw(g2);
		}
	}
	
}
