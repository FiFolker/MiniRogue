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
	Game game;
	public String result;
	public String rewardString;
	public String penaltyString;
	private String currentCase;
	private final String statsCase = "statsCase";
	private final String diceAndStatsCase = "diceAndStatsCase";
	private final String diceCase = "diceCase";
	private final String potionCase = "potionCase";

	public RewardOrPenalty(Game game, String key, int value){
		this.game = game;
		this.value = value;
		this.key = key;
		rewardString = "+ " + value + " " + key;
		penaltyString = "- " + value + " " + key;
		currentCase = statsCase;
	}

	public RewardOrPenalty(Game game, Potion potion){
		this.game = game;
		this.potion = potion;
		rewardString = "+ " + potion.name ;
		penaltyString = "- " + potion.name ;
		currentCase = potionCase;
	}

	public RewardOrPenalty(Game game, Dice dice){
		this.game = game;
		this.dice = dice;
		penaltyString = "+ " + dice.name ;
		rewardString = "- " + dice.name ;
		currentCase = diceCase;
	}

	public RewardOrPenalty(Game game, Dice dice, String key, int value){
		this.game = game;
		this.dice = dice;
		this.value = value;
		this.key = key;
		rewardString = "+ " + dice.name + " + " + value + " " +key;
		penaltyString = "+ " + dice.name + " - " + value + " " +key;
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
