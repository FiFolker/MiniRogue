package potions;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import cards.EnnemyCard;
import main.Game;
import main.Utils;
import main.Button;
import main.ErrorDraw;

public class FrostedPotion extends Potion {

	public FrostedPotion(Game game) {
		super(game, Utils.loadImage("assets/potions/frostedPotion.png"));
		info = "Givre l'ennemi pendant 1 tour";
		name = "Potion de Givre";
		addButtton();
	}

	@Override
	public void applyEffect() {
		if(game.inFight){
			EnnemyCard e = (EnnemyCard)game.currentCard;
			e.ennemy.canFight = false;
			game.selectedClass.removePotion(this);
		}else{
			ErrorDraw.errorState = true;
            errorString = "Vous devez être en combat pour utiliser cette potion ! ";
		}
	}
	
}
