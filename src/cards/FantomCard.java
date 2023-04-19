package cards;

import dices.Dice;
import effect.ArmorPiercing;
import effect.Blindness;
import effect.Weaken;
import ennemy.Ennemy;
import main.Game;
import rewardAndPenalty.Reward;

public class FantomCard extends Ennemy{

	public FantomCard(Game game) {
		super(game);
		switch(game.stage){
			case 1:
				name = "Apparition";
				life = 10;
				damage = 1;
				reward = new Reward(game, game.selectedClass.xpString, 1);
				break;
			case 2:
				name = "Fantôme";
				life = 12;
				damage = 2;
				addEffect(new Weaken(game));
				reward = new Reward(game, game.selectedClass.xpString, 1);
				break;
			case 3:
				name = "Spectre";
				life = 14;
				damage = 3;
				addEffect(new Blindness(game));
				reward = new Reward(game, game.selectedClass.xpString, 2);
				break;
			case 4:
				name = "Revenant";
				life = 16;
				damage = 5;
				addEffect(new ArmorPiercing(game, this));
				reward = new Reward(game, game.selectedClass.xpString, 2);
				break;
		}
		totalLife = life;
	}
	
}
