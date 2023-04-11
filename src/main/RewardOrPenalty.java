package main;

import dices.CharacterDice;
import dices.CurseDice;
import dices.Dice;
import dices.DungeonDice;
import dices.PoisonDice;

import java.util.ArrayList;
import java.util.Objects;
import potions.Potion;

public class RewardOrPenalty {

	public int value;
	public String key;
	Potion potion;
	Dice dice;
	ArrayList<Dice> dices;
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

	public RewardOrPenalty(Game game, ArrayList<Dice> dices){
		this.game = game;
		this.dices = dices;

		penaltyString = "";
		rewardString = "";
		
		for(Dice d : dices){
			penaltyString += " + " + d.name ;
			rewardString += " - " + d.name ;
		}
		
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
				result = rewardString;
				break;
			case potionCase:
				game.selectedClass.addPotion(potion);
				result = rewardString;
				break;
			case diceCase:
				applyDice();
				result = rewardString;
				break;
			case diceAndStatsCase:
				applyDice();
				game.selectedClass.addStat(key, value);
				result = rewardString;
				break;
			default:
		}
	}

	public void penalty(){
		switch(currentCase){
			case statsCase:
				game.selectedClass.substractStat(key, value);
				result = penaltyString;
				break;
			case potionCase:
				game.selectedClass.removePotion(potion);
				result = penaltyString;
				break;
			case diceCase:
				applyDice();
				result = penaltyString;
				break;
			case diceAndStatsCase:
				applyDice();
				game.selectedClass.substractStat(key, value);
				result = penaltyString;
				break;
			default:
		}
	}

	public void applyDice(){
		if(dices == null ){
			convertDice(dice);
		}else{
			for(Dice d : dices){
				convertDice(d);
			}
		}
	}

	public void convertDice(Dice d){
		if(d instanceof CurseDice){
			game.curseDice = (CurseDice)d;
		}else if(d instanceof PoisonDice){
			game.poisonDice = (PoisonDice)d;
		}else if(d instanceof CharacterDice){
			game.characterDices.add((CharacterDice)d);
		}
	}
	
}
