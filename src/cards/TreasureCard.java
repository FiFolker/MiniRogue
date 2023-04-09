package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dices.CharacterDice;
import dices.Dice;
import main.Game;

public class TreasureCard extends Card{

	public TreasureCard(Game game, BufferedImage image, Rectangle hitbox, int x, int y) {
		super(game, image, hitbox, x, y);
	}

	@Override
	public void update(ArrayList<Dice> dices, int stage){
		for(Dice d : dices){
			if(d.getClass() == CharacterDice.class){
				CharacterDice c = (CharacterDice)d;
				if(c.testSkill()){
					
				}
			}
		}
	}
	
}
