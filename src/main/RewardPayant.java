package main;

import effect.Effect;
import potions.Potion;

public class RewardPayant extends RewardOrPenalty{

	int moneyValue;
	boolean isOnSale = false;

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
			canSell = game.selectedClass.stats.get(key) >= moneyValue;
		}
		if(potion !=null){
			canSell = game.selectedClass.potions.contains(potion);
		}

		return canSell;
	}
	
	@Override
	public void reward() {
		if(canBuy() && !isOnSale){
			super.reward();
			game.selectedClass.substractStat(game.selectedClass.moneyString, moneyValue);
		}
		if(canSell() && isOnSale){
			super.reward();
			if(potion != null){
				game.selectedClass.removePotion(potion);
				game.selectedClass.addStat(game.selectedClass.moneyString, moneyValue);
			}
			if(key != null){
				game.selectedClass.substractStat(key, value);
				game.selectedClass.addStat(game.selectedClass.moneyString, moneyValue);
			}
		}
		
	}
	
}
