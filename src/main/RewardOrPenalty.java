package main;

import dices.CharacterDice;
import dices.CurseDice;
import dices.Dice;
import dices.DungeonDice;
import dices.PoisonDice;
import effect.Blessing;
import effect.Effect;

import java.util.ArrayList;
import java.util.Objects;
import potions.Potion;

public class RewardOrPenalty {

	public int value;
	public String key;
	public Potion potion;
	Dice dice;
	ArrayList<Dice> dices;
	Game game;
	public String result;
	public String rewardString;
	public String penaltyString;
	public Effect effect;

	public RewardOrPenalty(Game game, String key, int value){
		this.game = game;
		this.value = value;
		this.key = key;
		rewardString = "+ " + value + " " + key;
		penaltyString = "- " + value + " " + key;
	}

	public RewardOrPenalty(Game game, Potion potion){
		this.game = game;
		this.potion = potion;
		rewardString = "+ " + potion.name ;
		penaltyString = "- " + potion.name ;
	}

	public RewardOrPenalty(Game game, Dice dice){
		this.game = game;
		this.dice = dice;
		penaltyString = "+ " + dice.name ;
		rewardString = "- " + dice.name ;
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
		
	}

	public RewardOrPenalty(Game game, Dice dice, String key, int value){
		this.game = game;
		this.dice = dice;
		this.value = value;
		this.key = key;
		rewardString = "+ " + dice.name + " + " + value + " " +key;
		penaltyString = "+ " + dice.name + " - " + value + " " +key;
	}

	public RewardOrPenalty(Game game, Potion potion, String key, int value){
		this.game = game;
		this.potion = potion;
		this.value = value;
		this.key = key;
		rewardString = "+ " + potion.name + " / + " + value + " " +key;
		penaltyString = "+ " + potion.name + " / - " + value + " " +key;
	}

	public RewardOrPenalty(Game game, Effect effect){
		this.game = game;
		this.effect = effect;
		rewardString = "+ " + effect.name;
		penaltyString = "- " + effect.name;
	}

	public void reward(){

		result = rewardString;

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

	public void penalty(){
		result = penaltyString;
			
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

	public void applyDice(){
		if(dice != null ){
			convertDice(dice);
		}else if(dices != null){
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

    public void addEffect(Effect effect) {
		this.effect = effect;
		rewardString += " + " + effect.name;
		penaltyString += " - " + effect.name;
    }
	
}
