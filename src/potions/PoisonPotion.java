package potions;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import cards.EnnemyCard;
import main.Game;
import main.Utils;
import main.Button;

public class PoisonPotion extends Potion{

    public PoisonPotion(Game game) {
        super(game, Utils.loadImage("assets/potions/poisonPotion.png"));
        name = "Poison 4 dégâts par tour";
        addButtton();
    }

    @Override
    public void applyEffect() {
        if(game.inFight){
            EnnemyCard e = (EnnemyCard)game.currentCard;
            e.ennemy.poisonEffect = true;
            Potion.removePotion(this);
        }else{
            error = true;
            errorString = "Vous devez être en combat pour utiliser cette potion ! ";
            System.out.println("Vous devez être en combat pour utiliser cette potion ! ");
        }
    }


}