package ennemy;

import javax.xml.stream.events.EndElement;

import dices.CurseDice;
import dices.PoisonDice;
import dices.Dice;
import effect.Effect;
import main.Game;
import rewardAndPenalty.Reward;
import rewardAndPenalty.RewardAndPenalty;

public class Ennemy {

	Game game;
	public int life;
	public RewardAndPenalty reward;
	public String name;
	public int damage;
	public Dice applicableDice;
	public int totalLife;
	public boolean canFight = true;
	public boolean poisonEffect = false;
	public Effect effect;
	public boolean pierceTheArmor = false;
	public String ennemyAttack = "";
	public boolean finish = true;

	public Ennemy(Game game, String name, int life, int damage, Reward reward, Dice applicableDice){
		this.life = life;
		this.game = game;
		totalLife = this.life;
		this.reward = reward;
		this.damage = damage;
		this.name = name;
		this.applicableDice = applicableDice;

	}
	public Ennemy(Game game){
		this.game = game;
	}

	public void addEffect(Effect effect){
		this.effect = effect;
	}

	public void actionEnnemy(int value){
		switch(value){
			case 1:
				ennemyAttack = "Attaque ennemi loupé ! ";
				break;
			case 2:
			case 3:
			case 4:
			case 5:
				ennemyAttack = "Attaque ennemi réussi ! " + damage + " dégâts";
				if(effect != null){
					effect.applyEffect();
				}
				game.selectedClass.damageReceived(damage, pierceTheArmor);
				
				addDice();
				break;
			case 6:
				ennemyAttack = "Attaque ennemi Parfaite ! " + damage + " dégâts";
				if(effect != null){
					effect.applyEffect();
				}
				game.selectedClass.damageReceived(damage, true);
				
				addDice();
				break;
			default:
				
		}
	}

	public void addDice(){
		if(applicableDice != null){
			if(applicableDice instanceof CurseDice){
				game.curseDice = (CurseDice)applicableDice;
			}
			if(applicableDice instanceof PoisonDice){
				game.poisonDice = (PoisonDice)applicableDice;
			}
		}
	}

	public void nextPhase(){}
	
}
