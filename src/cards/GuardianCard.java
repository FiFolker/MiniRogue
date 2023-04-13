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

public class GuardianCard extends UpdateAlways{

	public Ennemy ennemy;
	RewardChoice rewardChoice;
	Fight fight;
	int life;
	int damage;
	int reward;
	int timer = 0;
	private boolean isLastZone;
	public static boolean protectorHasSpawned = false;
	public static boolean spiderHasSpawned = false;
	public static boolean dragonHasSpawned = false;

	public GuardianCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord, boolean isLastZone) {
		super(game, hitbox, x, y, coord);
		name = "Carte Guardien";
		result = "Combat En Cours ...";
		backCard = Utils.loadImage("assets/cards/guardianBackCard.png");
		image = Utils.loadImage("assets/cards/cardRed.png");
		currentImage = backCard;
		this.isLastZone = isLastZone;
		this.rewardChoice = new RewardChoice(game, this);
		if(isLastZone){
			setupEnnemy();
		}
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
			
			if(rng == 1 && !protectorHasSpawned){
				ennemy = new Ennemy(game, "Le Protecteur Du Roi", life, damage, reward, null);
				GuardianCard.protectorHasSpawned = true;
				ennemy.addEffect(new ArmorPiercing(game, ennemy));
			}else if(rng == 2 && !dragonHasSpawned){
				ennemy = new Ennemy(game, "Le Dragon Maudit", life, damage, reward, new CurseDice(game));
				GuardianCard.dragonHasSpawned = true;
			}else if(rng == 3 && !spiderHasSpawned){
				ennemy = new Ennemy(game, "La Reigne Araignées", life, damage, reward, new PoisonDice(game));
				GuardianCard.spiderHasSpawned = true;
			}else if(spiderHasSpawned && rng == 3 || protectorHasSpawned && rng == 1 || dragonHasSpawned && rng == 2){
				setupEnnemy();
			}
			
		}
		fight = new Fight(game, ennemy);
	}

	@Override
	public void updateAlways() {
		if(game.currentPos.equals(this.coord) && game.inFight && game.diceHasRolled && isLastZone){
			fight.update();
			isFinish = false;
		}
		rewardChoice.update();
		
	}

	@Override
	public void drawAdditional(Graphics2D g2) {
		if(isLastZone){
			fight.draw(g2);
		}
		if(!game.inFight && game.currentCard.equals(this)){
			rewardChoice.draw(g2);
			if(rewardChoice.hasChoiced){
				timer ++;
				if(timer  >= 120){
					game.goDownstair();
					timer = 0;
				}
			}
			
		}
	}

	public static void reset() {
		protectorHasSpawned = false;
		dragonHasSpawned= false;
		spiderHasSpawned = false;
	}
	
}
