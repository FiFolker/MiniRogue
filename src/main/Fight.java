package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import dices.CharacterDice;
import dices.CurseDice;
import dices.PoisonDice;
import ennemy.Ennemy;

public class Fight implements IUpdateAndDraw{
	
	Game game;
	Ennemy ennemy;
	String historique = "";
	String result = "Combat En Cours ...";
	int turn = 0;
	int currentTurn = Integer.MIN_VALUE;

	public Fight(Game game, Ennemy ennemy){
		this.game = game;
		this.ennemy = ennemy;
	}

	@Override
	public void update() {
		ennemy.ennemyAttack = " ";
		game.selectedClass.playerAttack = " ";
		historique = "";
		game.selectedClass.damage = 0;
		if(ennemy.life>0){ // tour joueur

			if(ennemy.poisonEffect){
                game.selectedClass.damage += 4;
				historique += " +4P ";
            }

			for(CharacterDice d : game.characterDices){
				game.selectedClass.damage += d.value;
				historique += " +"+d.value;
				while(d.value >= 5 && ennemy.life-game.selectedClass.damage > 0){
					d.roll();
					game.selectedClass.damage += d.value;
					historique += " +"+d.value;
				}
				
				
			}

			ennemy.life -= game.selectedClass.damage;
			game.selectedClass.playerAttack = "Vous avez fait " + game.selectedClass.damage + " dégâts";

		}
		
		if(!ennemy.canFight && currentTurn == Integer.MIN_VALUE){
			currentTurn = turn;
		}

		if(turn - currentTurn == 1){
			currentTurn = Integer.MIN_VALUE;
			ennemy.canFight = true;
		}

		if(!ennemy.canFight){
			ennemy.ennemyAttack = "L'ennemi est gelé !";
			game.diceHasRolled = false;
		}

		if(ennemy.life > 0 && ennemy.canFight){ // tour ennemi
			ennemy.actionEnnemy(game.dungeonDice.value);
			game.diceHasRolled = false;
			game.canMove = false;
		}else if(ennemy.life <= 0){
			game.diceHasRolled = false;
			result = "Bravo vous avez terrasé " + ennemy.name;
			game.selectedClass.addStat(game.selectedClass.xpString, ennemy.reward);
			game.currentCard.isFinish = true;
			game.selectedClass.playerAttack	 = " ";
			ennemy.ennemyAttack  = " ";
			game.inFight = false;
			
		}
		turn ++;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.white);
		Font defaultFont = g2.getFont();
		g2.drawString(ennemy.name, game.choicePlaceX-(int)Utils.textToRectangle2D(ennemy.name, g2).getWidth()/2, game.choicePlaceY);
		g2.drawString("PV : " + ennemy.life+"/"+ennemy.totalLife, game.choicePlaceX-(int)Utils.textToRectangle2D("PV : " + ennemy.life+"/"+ennemy.totalLife, g2).getWidth()/2, game.choicePlaceY+30);
		String damage = "Dégats : " + Integer.toString(ennemy.damage);
		if(ennemy.applicableDice != null){
			damage += " + " + ennemy.applicableDice.name;
		}else if(ennemy.effect != null){
			damage += " + " + ennemy.effect.name;
		}
		g2.drawString(damage, game.choicePlaceX-(int)Utils.textToRectangle2D(damage, g2).getWidth()/2, game.choicePlaceY+60);

		g2.drawString("Récompense : "+ Integer.toString(ennemy.reward) + " XP", game.choicePlaceX-(int)Utils.textToRectangle2D("Récompense : "+ Integer.toString(ennemy.reward)+ " XP", g2).getWidth()/2 , game.choicePlaceY+90);
		
		g2.drawLine(0, game.choicePlaceY+110, game.gui.xLine, game.choicePlaceY+110);
		g2.setFont(game.sansSerif);
		g2.drawString("Combat", game.choicePlaceX-(int)Utils.textToRectangle2D("Combat", g2).getWidth()/2, game.choicePlaceY+130);
		g2.setFont(defaultFont);
		g2.drawString(game.selectedClass.playerAttack, game.choicePlaceX-(int)Utils.textToRectangle2D(game.selectedClass.playerAttack, g2).getWidth()/2, game.choicePlaceY+150);
		g2.setColor(Color.red);
		g2.drawString(ennemy.ennemyAttack, game.choicePlaceX-(int)Utils.textToRectangle2D(ennemy.ennemyAttack, g2).getWidth()/2, game.choicePlaceY+170);
		g2.setColor(Color.white);
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+190);
		g2.drawString("Historique Dégâts", game.choicePlaceX-(int)Utils.textToRectangle2D("Historique Dégâts", g2).getWidth()/2, game.choicePlaceY+220);
		g2.drawString(historique, game.choicePlaceX-(int)Utils.textToRectangle2D(historique, g2).getWidth()/2, game.choicePlaceY+240);
	}

	

	

	

}
