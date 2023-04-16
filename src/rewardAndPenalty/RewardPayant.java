package rewardAndPenalty;

import java.awt.Graphics2D;

import effect.Effect;
import main.ErrorDraw;
import main.Game;
import potions.Potion;

public class RewardPayant extends Reward{

	String erroString;
	int moneyValue;
	public boolean isOnSale = false;
	ErrorDraw errorDraw = new ErrorDraw();

	public RewardPayant(Game game, Potion potion, int price, boolean isOnSale) {
		super(game, potion);
		this.moneyValue = price;
		this.isOnSale = isOnSale;
	}
	
	public RewardPayant(Game game, String key, int value, int price, boolean isOnSale) {
		super(game, key, value);
		this.moneyValue = price;
		this.isOnSale = isOnSale;
	}

	public RewardPayant(Game game, Effect effect, int price, boolean isOnSale) {
		super(game, effect);
		this.moneyValue = price;
		this.isOnSale = isOnSale;
	}

	public boolean canBuy(){
		return game.selectedClass.stats.get(game.selectedClass.moneyString) >= moneyValue;
	}

	public boolean canSell(){
		boolean canSell = false;
		if(key != null){
			canSell = game.selectedClass.stats.get(key) >= value;
		}
		if(potion !=null){
			canSell = game.selectedClass.potions.contains(potion);
		}
		return canSell;
	}
	
	@Override
	public void rewardOrPenalty() {
		if(canBuy() && !isOnSale){
			super.rewardOrPenalty();
			game.selectedClass.substractStat(game.selectedClass.moneyString, moneyValue);
		}else{
			erroString = "Erreur vous n'avez plus d'argent !";
			errorDraw.errorState = true;
		}
		if(canSell() && isOnSale){
			if(potion != null){
				game.selectedClass.removePotion(potion);
				game.selectedClass.addStat(game.selectedClass.moneyString, moneyValue);
			}
			if(key != null){
				game.selectedClass.substractStat(key, value);
				game.selectedClass.addStat(game.selectedClass.moneyString, moneyValue);
			}
		}else if(!canSell()){
			System.out.println("t");
			erroString = "Erreur vous n'avez pas la ressource demandée !";
			errorDraw.errorState = true;
		}
	}

	public void draw(Graphics2D g2){
		if(errorDraw.errorState){
			errorDraw.draw(g2, erroString, game);
		}
	}
	
}
