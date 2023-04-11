package main;

import dices.CharacterDice;
import dices.CurseDice;
import dices.Dice;
import dices.DungeonDice;
import dices.PoisonDice;

import java.util.Objects;
import potions.Potion;

public class RewardOrPenalty {

	public int value;
	public String key;
	Potion potion;
	Dice dice;
	int rewardCase;
	Game game;
	public String result;
	private String currentCase;
	private final String statsCase = "statsCase";
	private final String diceAndStatsCase = "diceAndStatsCase";
	private final String diceCase = "diceCase";
	private final String potionCase = "potionCase";

	public RewardOrPenalty(Game game, String key, int value, int rewardCase){
		this.game = game;
		this.value = value;
		this.key = key;
		this.rewardCase = rewardCase;
		currentCase = statsCase;
	}

	public RewardOrPenalty(Game game, Potion potion, int rewardCase){
		this.game = game;
		this.potion = potion;
		this.rewardCase = rewardCase;
		currentCase = potionCase;
	}

	public RewardOrPenalty(Game game, Dice dice, int rewardCase){
		this.game = game;
		this.dice = dice;
		this.rewardCase = rewardCase;
		currentCase = diceCase;
	}

	public RewardOrPenalty(Game game, Dice dice, String key, int value, int rewardCase){
		this.game = game;
		this.dice = dice;
		this.value = value;
		this.key = key;
		this.rewardCase = rewardCase;
		currentCase = statsCase;
	}

	public void reward(){
		switch(currentCase){
			case statsCase:
				game.selectedClass.addStat(key, value);
				result = "+ " + value + " " + key;
				break;
			case potionCase:
				game.selectedClass.addPotion(potion);
				result = "+ " + potion.name;
				break;
			case diceCase:
				applyDice();
				result = "+ " + dice.name;
				break;
			case diceAndStatsCase:
				applyDice();
				game.selectedClass.addStat(key, value);
				result = "+ " + dice.name + " + " + value + " " +key;
				break;
			default:
		}
	}

	public void penalty(){
		switch(currentCase){
			case statsCase:
				game.selectedClass.substractStat(key, value);
				result = "- " + value + " " + key;
				break;
			case potionCase:
				game.selectedClass.removePotion(potion);
				result = "+ " + potion.name;
				break;
			case diceCase:
				applyDice();
				result = "+ " + dice.name;
				break;
			case diceAndStatsCase:
				applyDice();
				game.selectedClass.substractStat(key, value);
				result = "+ " + dice.name + " - " + value + " " +key;
				break;
			default:
		}
	}

	public void applyDice(){
		if(dice instanceof CurseDice){
			game.curseDice = (CurseDice)dice;
		}else if(dice instanceof PoisonDice){
			game.poisonDice = (PoisonDice)dice;
		}else if(dice instanceof CharacterDice){
			game.characterDices.add((CharacterDice)dice);
		}
	}
	
}
