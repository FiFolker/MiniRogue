package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


import dices.CurseDice;
import dices.Dice;
import dices.PoisonDice;
import effect.ArmorPiercing;
import ennemy.Boss;
import ennemy.Ennemy;
import main.Coordonnees;
import main.Fight;
import main.Game;
import main.Utils;
import rewardAndPenalty.Reward;

public class GuardianCard extends UpdateAlways{

	public Ennemy ennemy;
	RewardChoice rewardChoice;
	Fight fight;
	int life;
	int damage;
	Reward reward;
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
			ennemy = new Boss(game, "Les Restes d'OG", new int[]{25, 20}, new int[]{8, 9}, null, new Dice[]{new CurseDice(game), new PoisonDice(game)}, 2);
		}else{
			reward = new Reward(game, game.selectedClass.xpString, 0);
			switch(game.stage){
				case 1:
					life = 12;
					damage = 2;
					reward.value = 2;
					break;
				case 2:
					life = 16;
					damage = 4;
					reward.value = 3;
					break;
				case 3:
					life = 20;
					damage = 6;
					reward.value = 4;
					break;
				default :
					life = 12;
					damage = 2;
					reward.value = 2;
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
			if(!(ennemy instanceof Boss)){
				rewardChoice.draw(g2);
			}
			if(rewardChoice.hasChoiced || ennemy instanceof Boss && ennemy.finish){
				timer ++;
				g2.setColor(Color.white);
				g2.setFont(game.sansSerif);
				g2.drawString("En train de descendre ...", game.gui.xLine + ((game.getWidth() - game.gui.xLine)/2 - (int)Utils.textToRectangle2D("En train de descendre ...", g2).getWidth()/2), game.textPlaceY);
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
