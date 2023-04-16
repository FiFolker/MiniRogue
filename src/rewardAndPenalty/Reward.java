package rewardAndPenalty;

import dices.Dice;
import effect.Effect;
import main.Game;

import java.util.ArrayList;
import potions.Potion;

public class Reward extends RewardAndPenalty{


	public Reward(Game game, String key, int value){
		super(game, key, value);
		result = "+ " + value + " " + key;
	}

	public Reward(Game game, Potion potion){
		super(game, potion);
		result = "+ " + potion.name ;
	}

	public Reward(Game game, Dice dice){
		super(game, dice);
		result = "+ " + dice.name ;
	}

	public Reward(Game game, ArrayList<Dice> dices){
		super(game, dices);

		result = "";
		
		for(Dice d : dices){
			result += " + " + d.name ;
		}
		
	}

	public Reward(Game game, Dice dice, String key, int value){
		super(game, dice, key, value);
		result = "+ " + dice.name + " + " + value + " " +key;
	}

	public Reward(Game game, Potion potion, String key, int value){
		super(game, potion, key, value);
		result = "+ " + potion.name + " / + " + value + " " +key;
	}

	public Reward(Game game, Effect effect){
		super(game, effect);
		result = "+ " + effect.name;
	}

	@Override
	public void rewardOrPenalty(){

		applyDice();
		if(effect != null){
			effect.applyEffect();
		}
		if(potion != null){
			game.selectedClass.addPotion(potion);

		}
		if(key != null){
			game.selectedClass.addStat(key, value);
		}
	}

	
}
