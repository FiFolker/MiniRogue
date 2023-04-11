package potions;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import cards.EnnemyCard;
import main.Game;
import main.Utils;
import main.Button;

public class FrostedPotion extends Potion {

	public FrostedPotion(Game game) {
		super(game, Utils.loadImage("assets/potions/frostedPotion.png"));
		name = "Givre l'ennemi pendant 1 tour";
		addButtton();
	}

	@Override
	public void applyEffect() {
		if(game.inFight){
			EnnemyCard e = (EnnemyCard)game.currentCard;
			e.ennemy.canFight = false;
			Potion.removePotion(this);
		}else{
			error = true;
            errorString = "Vous devez être en combat pour utiliser cette potion ! ";
		}
	}
	
}
