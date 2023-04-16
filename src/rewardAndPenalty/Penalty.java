package rewardAndPenalty;

import java.util.ArrayList;

import dices.Dice;
import main.Game;

public class Penalty extends RewardAndPenalty{
    


	public Penalty(Game game, String key, int value){
		super(game, key, value);
		result = "- " + value + " " + key;
	}

	public Penalty(Game game, Dice dice){
		super(game, dice);
		result = "+ " + dice.name ;
	}

	public Penalty(Game game, ArrayList<Dice> dices){
		super(game, dices);

		result = "";
		
		for(Dice d : dices){
			result += " + " + d.name ;
		}
		
	}

	public Penalty(Game game, Dice dice, String key, int value){
		super(game, dice, key, value);
		result = "+ " + dice.name + " - " + value + " " +key;
	}


	@Override
    public void rewardOrPenalty(){
			
		applyDice();

		if(effect != null){
			effect.applyEffect();
		}
		if(potion != null){
			game.selectedClass.removePotion(potion);

		}
		if(key != null){
			game.selectedClass.substractStat(key, value);
		}
	}

}
