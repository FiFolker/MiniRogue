package potions;


import java.awt.Rectangle;

import cards.EnnemyCard;
import cards.GuardianCard;
import main.Button;
import main.ErrorDraw;
import main.Game;
import main.Utils;

public class FirePotion extends Potion {

	public FirePotion(Game game){
		super(game, Utils.loadImage("assets/potions/firePotion.png"));
		info = "Feu 7 dégâts";
		name = "Potion de Feu";
		effectValue = 7;
		addButtton();
	}

	@Override
	public void applyEffect() {
		if(game.inFight){
			if(game.currentCard instanceof EnnemyCard){
				EnnemyCard e = (EnnemyCard)game.currentCard;
				e.ennemy.life -= effectValue;
			}else if(game.currentCard instanceof GuardianCard){
				GuardianCard g = (GuardianCard)game.currentCard;
				g.ennemy.life -= effectValue;
			}
			game.selectedClass.removePotion(this);
		}else{
			ErrorDraw.errorState = true;
			errorString = "Vous devez être en combat pour utiliser cette potion ! ";
		}
		
	}

}
