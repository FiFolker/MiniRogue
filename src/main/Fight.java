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
    String playerAttack = " ";
	String ennemyAttack = " ";
	String result = "Combat En Cours ...";

    public Fight(Game game, Ennemy ennemy){
        this.game = game;
        this.ennemy = ennemy;
    }

    @Override
    public void update() {
        ennemyAttack = " ";
		playerAttack = " ";
		game.selectedClass.damage = 0;
		if(ennemy.life>0){
			for(CharacterDice d : game.characterDices){
				game.selectedClass.damage += d.value;
				while(d.value >= 5 && ennemy.life-game.selectedClass.damage > 0){
					d.roll();
					game.selectedClass.damage += d.value;
				}
			}
			playerAttack = "Vous avez fait " + game.selectedClass.damage + " dégâts";
			ennemy.life -= game.selectedClass.damage;
		}
		
		if(ennemy.life > 0){
			actionEnnemy(game.dungeonDice.value);
			game.diceHasRolled = false;
			game.canMove = false;
		}else{
			game.diceHasRolled = false;
			result = "Bravo vous avez terrasé " + ennemy.name;
			game.selectedClass.addStat(game.selectedClass.xpString, ennemy.reward);
			game.currentCard.isFinish = true;
			game.inFight = false;
			
		}
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
		Font defaultFont = g2.getFont();
		g2.drawString(ennemy.name, game.choicePlaceX-(int)Utils.textToRectangle2D(ennemy.name, g2).getWidth()/2, game.choicePlaceY);
		g2.drawString("PV : " + ennemy.life+"/"+ennemy.totalLife, game.choicePlaceX-(int)Utils.textToRectangle2D("PV : " + ennemy.life+"/"+ennemy.totalLife, g2).getWidth()/2, game.choicePlaceY+30);
		g2.drawString("Dégats : " + Integer.toString(ennemy.damage), game.choicePlaceX-(int)Utils.textToRectangle2D("Dégats : " + Integer.toString(ennemy.damage), g2).getWidth()/2, game.choicePlaceY+60);
		g2.drawString("Récompense : "+ Integer.toString(ennemy.reward) + " XP", game.choicePlaceX-(int)Utils.textToRectangle2D("Récompense : "+ Integer.toString(ennemy.reward)+ " XP", g2).getWidth()/2 , game.choicePlaceY+90);
		
		g2.drawLine(0, game.choicePlaceY+110, game.gui.xLine, game.choicePlaceY+110);
		g2.setFont(game.sansSerif);
		g2.drawString("Combat", game.choicePlaceX-(int)Utils.textToRectangle2D("Combat", g2).getWidth()/2, game.choicePlaceY+130);
		g2.setFont(defaultFont);
		g2.drawString(playerAttack, game.choicePlaceX-(int)Utils.textToRectangle2D(playerAttack, g2).getWidth()/2, game.choicePlaceY+150);
		g2.setColor(Color.red);
		g2.drawString(ennemyAttack, game.choicePlaceX-(int)Utils.textToRectangle2D(ennemyAttack, g2).getWidth()/2, game.choicePlaceY+170);
		g2.setColor(Color.white);
		g2.drawString(result, game.choicePlaceX-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, game.choicePlaceY+190);
    }

    public void actionEnnemy(int value){
		switch(value){
			case 1:
				ennemyAttack = "Attaque ennemi loupé ! ";
				break;
			case 2:
			case 3:
			case 4:
			case 5:
				ennemyAttack = "Attaque ennemi réussi ! " + ennemy.damage + " dégâts";
				game.selectedClass.damageReceived(ennemy.damage, true);
				addDice();
				break;
			case 6:
				ennemyAttack = "Attaque ennemi Parfaite ! " + ennemy.damage + " dégâts";
				game.selectedClass.damageReceived(ennemy.damage, false);
				addDice();
				break;
		}
	}

    public void addDice(){
		if(ennemy.applicableDice != null){
			if(ennemy.applicableDice instanceof CurseDice){
				game.curseDice = (CurseDice)ennemy.applicableDice;
			}
			if(ennemy.applicableDice instanceof PoisonDice){
				game.poisonDice = (PoisonDice)ennemy.applicableDice;
			}
		}
	}

    

}
