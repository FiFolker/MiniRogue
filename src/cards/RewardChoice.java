package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Button;
import main.Game;
import main.RewardOrPenalty;
import main.Utils;
import potions.FirePotion;
import potions.FrostedPotion;
import potions.HolyWater;
import potions.LifePotion;
import potions.PerceptionPotion;
import potions.PoisonPotion;

public class RewardChoice{
	Game game;
	boolean hasChoiced = false;
	RewardOrPenalty[] rewards = new RewardOrPenalty[6];
	private String result = "Lancez les dés ...";
	Button firstChoice;
	Button secondChoice;
	Rectangle firstRect;
	Rectangle secondRect;
	private int i = 0;
	private Card card;

	public RewardChoice(Game game, Card card) {
		this.game = game;
		this.card = card;

		firstRect = new Rectangle(25, game.choicePlaceY+220, 150, 30);
		secondRect = new Rectangle(200, game.choicePlaceY+220, 150, 30);

		rewards[0] = new RewardOrPenalty(game, new FirePotion(game), game.selectedClass.xpString, 2);
		rewards[1] = new RewardOrPenalty(game, new FrostedPotion(game), game.selectedClass.moneyString, 1);
		rewards[2] = new RewardOrPenalty(game, new PoisonPotion(game), game.selectedClass.xpString, 2);
		rewards[3] = new RewardOrPenalty(game, new LifePotion(game), game.selectedClass.armorString, 1);
		rewards[4] = new RewardOrPenalty(game, new HolyWater(game), game.selectedClass.moneyString, 1);
		rewards[5] = new RewardOrPenalty(game, new PerceptionPotion(game), game.selectedClass.armorString, 1);

	}

	public void update() {
		if(!game.inFight && game.currentPos.equals(card.coord) && !hasChoiced && game.diceHasRolled){
			switch(game.dungeonDice.value){
				case 1:
					i = 0;
					break;
				case 2:
					i = 1;
					break;
				case 3:
					i = 2;
					break;
				case 4:
					i = 3;
					break;
				case 5:
					i = 4;
					break;
				case 6:
					i = 5;
					break;
			}
	
			result = rewards[i].rewardString;
			firstChoice = new Button(firstRect, rewards[i].potion.name);
			secondChoice = new Button(secondRect, "+ " + rewards[i].value + " " + rewards[i].key);
		}
		if(firstChoice != null && firstChoice.isClicked() && !hasChoiced){
			rewards[i].key = null;
			rewards[i].reward();
			hasChoiced = true;
			firstChoice.isSelected = true;
		}
		if(secondChoice != null && secondChoice.isClicked() && !hasChoiced){
			rewards[i].potion = null;
			rewards[i].reward();
			hasChoiced = true;
			secondChoice.isSelected = true;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, game.gui.yChoice, game.gui.xLine, game.getHeight());
		g2.setColor(Color.white);
		Utils.drawSixDicePossibilitiesInCol(game, g2, new String[]{rewards[0].rewardString, rewards[1].rewardString, rewards[2].rewardString, rewards[3].rewardString, rewards[4].rewardString, rewards[5].rewardString});
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+200);
		if(firstChoice != null && secondChoice != null){
			firstChoice.draw(g2);
			secondChoice.draw(g2);
		}
	}
	
}
