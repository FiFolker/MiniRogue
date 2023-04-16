package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import effect.Blessing;
import main.Button;
import main.Coordonnees;
import main.Game;
import main.Utils;
import potions.FrostedPotion;
import potions.Potion;
import rewardAndPenalty.RewardPayant;

public class MerchantCard extends UpdateAlways{

	HashMap<Button, RewardPayant> buyPossibilities = new HashMap<>();
	Button finish;
	int centerLeft;
	int centerRight;
	int width = 145;
	int height = 25;
	int gap = 35;

	public MerchantCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
        name = "Carte Marchand";
		image = Utils.loadImage("assets/cards/cardGreen.png");
		finish = new Button(new Rectangle(game.choicePlaceX-width/2, game.choicePlaceY+245, width, 30), "Terminer");
		centerLeft = (game.gui.xLine/2)/2;
		centerRight =  (game.gui.xLine/2) + (game.gui.xLine/2)/2;
		setupBuyPossibilities();
	}

	private void setupBuyPossibilities() {
		RewardPayant tempRewardPayant;
		// ACHAT
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20, width, height), "1 Pièce -> +1 PV"), new RewardPayant(game, game.selectedClass.lifeString, 1, 1, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "2 Pièces -> +1 Nourriture"), new RewardPayant(game, game.selectedClass.foodString, 1, 2, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "2 Pièces -> + Bénédiction"), new RewardPayant(game, new Blessing(game), 2, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "3 Pièces -> +4 PV"), new RewardPayant(game, game.selectedClass.lifeString, 4, 3, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "5 Pièces -> +1 Armure"), new RewardPayant(game, game.selectedClass.armorString, 1, 5, false));
		tempRewardPayant = new RewardPayant(game, Potion.randomPotion(game), 5, false);
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "5 Pièces -> +1 "+tempRewardPayant.potion.name), tempRewardPayant);
		
		// VENTE
		buyPossibilities.put(new Button(new Rectangle(centerRight-width/2, game.choicePlaceY+20, width, height), "-1 Armure -> +3 Pièces"), new RewardPayant(game, game.selectedClass.armorString, 1, 3, true));
		tempRewardPayant = new RewardPayant(game, Potion.randomPotion(game), 3, true);
		buyPossibilities.put(new Button(new Rectangle(centerRight-width/2, game.choicePlaceY+20+gap, width, height), "-1 " +tempRewardPayant.potion.name+ " -> +3 Pièces"), tempRewardPayant);
	}

	@Override
	public void updateAlways() {
		game.diceHasRolled = true;
		if(!isFinish){
			if(!buyPossibilities.isEmpty()){
				for(Map.Entry<Button, RewardPayant> entry : buyPossibilities.entrySet()){
					Button b = entry.getKey();
					RewardPayant buy = entry.getValue();
					if(b.isClicked()){
						buy.rewardOrPenalty();
						Game.mouseH.leftClicked = false;
					}
				}
			}
		}
		if(finish.isClicked() && !finish.isSelected){
			finish.isSelected = true;
			isFinish = true;
			game.canMove = true;
		}
	}

	@Override
	public void drawAdditional(Graphics2D g2) {
		g2.setFont(game.sansSerif);
		g2.drawString("Achat", centerLeft-(int)Utils.textToRectangle2D("Achat", g2).getWidth()/2, game.choicePlaceY);
		g2.drawString("Vente", centerRight-(int)Utils.textToRectangle2D("Vente", g2).getWidth()/2, game.choicePlaceY);

		if(!buyPossibilities.isEmpty()){
			for(Button b : buyPossibilities.keySet()){
				g2.setFont(Game.defaultFont);
				b.draw(g2);
				buyPossibilities.get(b).draw(g2);
			}
		}
		finish.draw(g2);
	}
	
}
