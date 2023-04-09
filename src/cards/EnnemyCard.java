package cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import dices.CharacterDice;
import dices.CurseDice;
import dices.Dice;
import dices.DungeonDice;
import dices.PoisonDice;
import ennemy.Ennemy;
import main.Game;

public class EnnemyCard extends Card{

	public Ennemy ennemy;

	public EnnemyCard(Game game, BufferedImage image, Rectangle hitbox, int x, int y, int stage) {
		super(game, image, hitbox, x, y);
		Random rand = new Random();
		int rng = rand.nextInt(1);
		if(rng == 0){
			switch(stage){ // malédiction
				case 1:
					ennemy = new Ennemy("Rat Géant", 6, 2, 1, null);
					break;
				case 2:
					ennemy = new Ennemy("Soldat Squelette", 9, 4, 2, null);
					break;
				case 3:
					ennemy = new Ennemy("Serpent Ailé", 12, 6, 2, new CurseDice());
					break;
				case 4:
					ennemy = new Ennemy("Garde Maudit", 15, 8, 4, new CurseDice());
					break;
	
			}
		}else if(rng == 1){ // poison
			switch(stage){
				case 1:
					ennemy = new Ennemy("Araignée Géante", 6, 2, 1, null);
					break;
				case 2:
					ennemy = new Ennemy("Gobelin", 9, 4, 2, null);
					break;
				case 3:
					ennemy = new Ennemy("Arbalétrier", 12, 6, 2, new PoisonDice());
					break;
				case 4:
					ennemy = new Ennemy("Garde du Roi", 15, 8, 4, new PoisonDice());
					break;
	
			}
		}
	}

	@Override
	public void update(ArrayList<Dice> dices, int stage){
		int playerDamage = 0;
		System.out.println(ennemy.name + " Vous attaque");
		for(Dice d : dices){
			if(d.getClass() == CharacterDice.class){
				playerDamage += d.value;
				while(d.value >= 5){
					d.roll();
					playerDamage += d.value;
					System.out.println(d.value + " : " + playerDamage);
				}
			}
		}
		System.out.println("Vous avez fait " + playerDamage + " dégats\nL'ennemi à " + (ennemy.life-playerDamage) + "/" + ennemy.totalLife + " pv");
		ennemy.life -= playerDamage;
		if(ennemy.life > 0){
			for(Dice d : dices){
				if(d.getClass() == DungeonDice.class){
					switch(d.value){
						case 1:
							System.out.println("Attaque ennemi loupé ! ");
							break;
						case 2:
						case 3:
						case 4:
						case 5:
							System.out.println("Attaque ennemi réussi ! " + ennemy.damage + " dégats");
							game.selectedClass.damageReceived(ennemy.damage, true);
							if(ennemy.applicableDice != null){
								game.dices.add(ennemy.applicableDice);
							}
							break;
						case 6:
							System.out.println("Attaque ennemi Parfaite ! " + ennemy.damage + " dégats");
							game.selectedClass.damageReceived(ennemy.damage, false);
							if(ennemy.applicableDice != null){
								game.dices.add(ennemy.applicableDice);
							}
							break;
					}
				}
			}
			//game.canMove = false;
			//game.canRoll = true;
		}else{
			System.out.println("Bravo vous avez terrasé " + ennemy.name);
			game.selectedClass.addStat(game.selectedClass.xpString, ennemy.reward);
		}
		
	}

}
