package rewardAndPenalty;

import java.util.ArrayList;

import dices.CharacterDice;
import dices.CurseDice;
import dices.Dice;
import dices.PoisonDice;
import effect.Effect;
import main.Game;
import potions.Potion;

public abstract class RewardAndPenalty {
    public int value;
	public String key;
	public Potion potion;
	Dice dice;
	ArrayList<Dice> dices;
	Game game;
	public String result;
	public Effect effect;


    public RewardAndPenalty(Game game, String key, int value){
		this.game = game;
		this.value = value;
		this.key = key;
	}

	public RewardAndPenalty(Game game, Potion potion){
		this.game = game;
		this.potion = potion;
	}

	public RewardAndPenalty(Game game, Dice dice){
		this.game = game;
		this.dice = dice;
	}

	public RewardAndPenalty(Game game, ArrayList<Dice> dices){
		this.game = game;
		this.dices = dices;
		
	}

	public RewardAndPenalty(Game game, Dice dice, String key, int value){
		this.game = game;
		this.dice = dice;
		this.value = value;
		this.key = key;
	}

	public RewardAndPenalty(Game game, Potion potion, String key, int value){
		this.game = game;
		this.potion = potion;
		this.value = value;
		this.key = key;
	}

	public RewardAndPenalty(Game game, Effect effect){
		this.effect = effect;
		result = "+ " + effect.name;
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
		result += " + " + effect.name;
    }

    public abstract void rewardOrPenalty();
}
