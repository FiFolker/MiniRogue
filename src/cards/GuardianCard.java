package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;


import dices.CurseDice;
import dices.PoisonDice;
import effect.ArmorPiercing;
import ennemy.Boss;
import ennemy.Ennemy;
import main.Coordonnees;
import main.Fight;
import main.Game;
import main.Utils;

public class GuardianCard extends UpdateOnRoll{

	public Ennemy ennemy;
	Fight fight;
	String playerAttack = " ";
	String ennemyAttack = " ";
	int life;
	int damage;
	int reward;
	int timer = 0;

	public GuardianCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		name = "Carte Guardien";
		result = "Combat En Cours ...";
		backCard = Utils.loadImage("assets/cards/guardianBackCard.png");
		image = Utils.loadImage("assets/cards/cardRed.png");
		currentImage = backCard;
		setupEnnemy();
	}

	public void setupEnnemy(){
		int rng = Utils.randomNumber(1, 3);
		if(game.stage == game.totalStage){
			ennemy = new Boss(game, "Les Restes d'OG", 25, 8, 0, new CurseDice(game));
		}else{
			switch(game.stage){
				case 1:
					life = 12;
					damage = 2;
					reward = 2;
					break;
				case 2:
					life = 16;
					damage = 4;
					reward = 3;
					break;
				case 3:
					life = 20;
					damage = 6;
					reward = 4;
					break;
				default :
					life = 12;
					damage = 2;
					reward = 2;
			}
			switch(rng){
				case 1:
					ennemy = new Ennemy(game, "Le Protecteur Du Roi", life, damage, reward, null);
					ennemy.addEffect(new ArmorPiercing(game, ennemy));
					break;
				case 2:
					ennemy = new Ennemy(game, "Le Dragon Maudit", life, damage, reward, new CurseDice(game));
					break;
				case 3:
					ennemy = new Ennemy(game, "La Reigne Araignées", life, damage, reward, new PoisonDice(game));
					break;
			}
		}
		fight = new Fight(game, ennemy);
	}

	@Override
	public void updateOnRoll() {
		fight.update();
		
	}

	@Override
	public void drawAdditional(Graphics2D g2) {
		fight.draw(g2);
		if(!game.inFight && game.currentCard.equals(this)){
			timer ++;
			if(timer  >= 120){
				game.goDownstair();
				timer = 0;
			}
		}
	}
	
}
