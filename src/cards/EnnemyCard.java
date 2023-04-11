package cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import dices.CharacterDice;
import dices.CurseDice;
import dices.Dice;
import dices.DungeonDice;
import dices.PoisonDice;
import ennemy.Ennemy;
import main.Coordonnees;
import main.Fight;
import main.Game;
import main.Utils;

public class EnnemyCard extends UpdateOnRoll{

	public Ennemy ennemy;
	Fight fight;
	String playerAttack = " ";
	String ennemyAttack = " ";
	String result = "Combat En Cours ...";

	public EnnemyCard(Game game, Rectangle hitbox, int x, int y, int stage, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		image = Utils.loadImage("assets/cards/cardRed.png");
		name = "Carte Monstre";
		setup(stage);
	}

	public void setup(int stage){
		
		int rng = Utils.randomNumber(0, 1);
		if(rng == 0){
			switch(stage){ // malédiction
				case 1:
					ennemy = new Ennemy("Rat Géant", 6, 2, 1, null);
					break;
				case 2:
					ennemy = new Ennemy("Soldat Squelette", 9, 4, 2, null);
					break;
				case 3:
					ennemy = new Ennemy("Serpent Ailé", 12, 6, 2, new CurseDice(game));
					break;
				case 4:
					ennemy = new Ennemy("Garde Maudit", 15, 8, 4, new CurseDice(game));
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
					ennemy = new Ennemy("Arbalétrier", 12, 6, 2, new PoisonDice(game));
					break;
				case 4:
					ennemy = new Ennemy("Garde du Roi", 15, 8, 4, new PoisonDice(game));
					break;
	
			}
		}

		fight = new Fight(game, ennemy);
	}

	@Override
	public void updateOnRoll(){
		fight.update();
	}

	

	@Override
	public void drawAdditional(Graphics2D g2) {
		fight.draw(g2);
	}


}
