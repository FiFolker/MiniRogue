package potions;


import java.awt.Rectangle;

import cards.EnnemyCard;
import main.Button;
import main.Game;
import main.Utils;

public class FirePotion extends Potion {

	public FirePotion(Game game){
		super(game, Utils.loadImage("assets/potions/firePotion.png"));
		name = "Feu 7 dégâts";
		effectValue = 7;
		potionButton = new Button(new Rectangle(62 + 20 + (size+size/2)*(currentNumber-1), game.gui.yPotions-size/2, size, size), icon, name, false);

	}

	@Override
	public void applyEffect() {
		if(game.inFight){
			EnnemyCard e = (EnnemyCard)game.currentCard;
			e.ennemy.life -= effectValue;
			Potion.removePotion(this);
		}else{
			error = true;
            errorString = "Vous devez être en combat pour utiliser cette potion ! ";
			System.out.println("Vous devez être en combat pour utiliser cette potion !");
		}
		
	}

}
