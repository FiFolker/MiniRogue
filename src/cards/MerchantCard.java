package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import effect.Blessing;
import main.Button;
import main.Coordonnees;
import main.Game;
import main.Utils;
import potions.FirePotion;
import potions.FrostedPotion;
import potions.HolyWater;
import potions.LifePotion;
import potions.PerceptionPotion;
import potions.PoisonPotion;
import potions.Potion;
import rewardAndPenalty.RewardPayant;

public class MerchantCard extends UpdateAlways{

	HashMap<Button, RewardPayant> buyPossibilities = new HashMap<>();
	Button currentButton = null;
	Button finish;
	private String buyPotion = "5 Pièces -> +1 Potion";
	private String sellPotion = "-1 Potion -> +3 Pièces";
	int width = 145;
	int height = 25;
	int gap = 35;
	public boolean haveToChoicePotion = false;

	public MerchantCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
        name = "Carte Marchand";
		image = Utils.loadImage("assets/cards/cardGreen.png");
		finish = new Button(new Rectangle(game.choicePlaceX-width/2, game.choicePlaceY+245, width, 30), "Terminer");
		setupBuyPossibilities();
	}

	private void setupBuyPossibilities() {
		// ACHAT
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20, width, height), "1 Pièce -> +1 PV"), new RewardPayant(game, game.selectedClass.lifeString, 1, 1, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "2 Pièces -> +1 Nourriture"), new RewardPayant(game, game.selectedClass.foodString, 1, 2, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "2 Pièces -> + Bénédiction"), new RewardPayant(game, new Blessing(game), 2, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "3 Pièces -> +4 PV"), new RewardPayant(game, game.selectedClass.lifeString, 4, 3, false));
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), "5 Pièces -> +1 Armure"), new RewardPayant(game, game.selectedClass.armorString, 1, 5, false));
		
		buyPossibilities.put(new Button(new Rectangle(centerLeft-width/2, game.choicePlaceY+20+(gap*buyPossibilities.size()), width, height), buyPotion), null);
		
		// VENTE
		buyPossibilities.put(new Button(new Rectangle(centerRight-width/2, game.choicePlaceY+20, width, height), "-1 Armure -> +3 Pièces"), new RewardPayant(game, game.selectedClass.armorString, 1, 3, true));
		buyPossibilities.put(new Button(new Rectangle(centerRight-width/2, game.choicePlaceY+20+gap, width, height), sellPotion), null);
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
						if(buy != null){
							buy.rewardOrPenalty();
						}else{
							currentButton = b;
							haveToChoicePotion = true;
						}
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
				if(buyPossibilities.get(b) != null){
					buyPossibilities.get(b).draw(g2);
				}
			}
		}
		
		finish.draw(g2);
	}

	public void choicePotion(Graphics2D g2){
		ArrayList<Potion> listOfPotions = new ArrayList<>();
		if(currentButton.buttonText.equals(buyPotion)){
			listOfPotions.add(new FirePotion(game));
			listOfPotions.add(new FrostedPotion(game));
			listOfPotions.add(new HolyWater(game));
			listOfPotions.add(new LifePotion(game));
			listOfPotions.add(new PerceptionPotion(game));
			listOfPotions.add(new PoisonPotion(game));
		}else if(currentButton.buttonText.equals(sellPotion)){
			listOfPotions = new ArrayList<>(game.selectedClass.potions);
		}
		Button[] listOfButtons = new Button[listOfPotions.size()];

		Rectangle box = new Rectangle((game.getWidth() - game.gui.xLine)/2-50, game.getHeight()/2-150, 400, 200);
		g2.draw(box);
		g2.setColor(Color.black);
		g2.fillRect(box.x+1, box.y+1, box.width-1, box.height-1);
		g2.setColor(Color.white);
		g2.drawString("Choisissez la Potion", box.x + box.width/2 -(int)Utils.textToRectangle2D("Choisissez la Potion", g2).getWidth()/2, box.y + 20);
		
		for(int i=0; i<listOfButtons.length; i++){
			listOfButtons[i] = new Button( new Rectangle(box.x + box.width/2 - ((listOfPotions.size()*listOfPotions.get(i).size)+(10*listOfPotions.size()))/2 + listOfPotions.get(i).size*i + 10*i, box.y + box.height/2 - listOfPotions.get(i).size, listOfPotions.get(i).size, listOfPotions.get(i).size), listOfPotions.get(i).icon);
			listOfButtons[i].draw(g2);
			if(listOfButtons[i].isClicked()){
				if(currentButton.buttonText.equals(buyPotion)){
					buyPossibilities.replace(currentButton, new RewardPayant(game, listOfPotions.get(i), 5, false));
					buyPossibilities.get(currentButton).rewardOrPenalty();
				}else if(currentButton.buttonText.equals(sellPotion)){
					buyPossibilities.replace(currentButton, new RewardPayant(game, listOfPotions.get(i), 3, true));
					buyPossibilities.get(currentButton).rewardOrPenalty();
				}
				buyPossibilities.replace(currentButton, null);
				haveToChoicePotion = false;
				Game.mouseH.leftClicked = false;
			}
		}
		
		Button cancelButton = new Button(new Rectangle(box.x + box.width/2 - 50, box.y + box.height - 35, 100, 25), "Annuler");
		
		cancelButton.draw(g2);

		if(cancelButton.isClicked()){
			haveToChoicePotion = false;
			Game.mouseH.leftClicked = false;
		}
	}
	
}
