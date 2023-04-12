package potions;

import cards.EnnemyCard;
import cards.GuardianCard;
import main.Game;
import main.Utils;

public class PoisonPotion extends Potion{

	public PoisonPotion(Game game) {
		super(game, Utils.loadImage("assets/potions/poisonPotion.png"));
		info = "Poison 4 dégâts par tour";
		name = "Potion de Poison";
		addButtton();
	}

	@Override
	public void applyEffect() {
		if(game.inFight){
			
			if(game.currentCard instanceof EnnemyCard){
				EnnemyCard e = (EnnemyCard)game.currentCard;
				e.ennemy.poisonEffect = true;
			}else if(game.currentCard instanceof GuardianCard){
				GuardianCard g = (GuardianCard)game.currentCard;
				g.ennemy.poisonEffect = true;
			}
			game.selectedClass.removePotion(this);
		}else{
			error = true;
			errorString = "Vous devez être en combat pour utiliser cette potion ! ";
			System.out.println("Vous devez être en combat pour utiliser cette potion ! ");
		}
	}


}