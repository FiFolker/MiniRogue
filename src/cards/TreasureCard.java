package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dices.CharacterDice;
import dices.Dice;
import dices.DungeonDice;
import main.Game;

public class TreasureCard extends Card{

	public TreasureCard(Game game, BufferedImage image, Rectangle hitbox, int x, int y) {
		super(game, image, hitbox, x, y);
	}

	@Override
	public void update(ArrayList<Dice> dices, int stage){
		boolean testSkill = false;
		for(Dice d : dices){
			if(d.getClass() == CharacterDice.class){
				CharacterDice c = (CharacterDice)d;
				if(c.testSkill() && !testSkill){
					testSkill = true;
				}
			}
		}
		if(testSkill){
			for(Dice d : dices){
				if(d.getClass() == DungeonDice.class){
					switch(d.value){
						case 3:
						case 1:
							game.selectedClass.addStat(game.selectedClass.xpString, 2);
							System.out.println("+2 d'xp");
							break;
						case 2:
							game.selectedClass.addStat(game.selectedClass.moneyString, 2);
							System.out.println("+2 de gold");
							break;
						case 4:
							game.selectedClass.addStat(game.selectedClass.armorString, 3);
							System.out.println("+3 d'armure");
							break;
						case 5:
							game.selectedClass.addStat(game.selectedClass.foodString, 2);
							System.out.println("+2 de nourriture ");
							break;
						case 6:
							game.selectedClass.addStat(game.selectedClass.moneyString, 8);
							System.out.println("+8 de gold");
							break;
					}
				}
			}
		}
	}
	
}
