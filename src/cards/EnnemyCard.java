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
import main.Game;
import main.Utils;

public class EnnemyCard extends UpdateOnRoll{

	public Ennemy ennemy;
	String playerAttack = " ";
	String ennemyAttack = " ";
	String result = "Combat En Cours ...";

	public EnnemyCard(Game game, Rectangle hitbox, int x, int y, int stage, Coordonnees coord) {
		super(game, hitbox, x, y, coord);
		image = Utils.loadImage("assets/cards/cardRed.png");
		name = "Carte Monstre";
		int rng = Utils.randomNumber(0, 1);
		if(rng == 0){
			switch(stage){ // malédiction
				case 1:
					ennemy = new Ennemy("Rat Géant", 6, 2, 1, new CurseDice(game));
					break;
				case 2:
					ennemy = new Ennemy("Soldat Squelette", 9, 4, 2, new CurseDice(game));
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
					ennemy = new Ennemy("Araignée Géante", 6, 2, 1, new PoisonDice(game));
					break;
				case 2:
					ennemy = new Ennemy("Gobelin", 9, 4, 2, new PoisonDice(game));
					break;
				case 3:
					ennemy = new Ennemy("Arbalétrier", 12, 6, 2, new PoisonDice(game));
					break;
				case 4:
					ennemy = new Ennemy("Garde du Roi", 15, 8, 4, new PoisonDice(game));
					break;
	
			}
		}
	}

	@Override
	public void updateOnRoll(){
		int playerDamage = 0;
		ennemyAttack = " ";
		playerAttack = " ";
		if(ennemy.life>0){
			for(CharacterDice d : game.characterDices){
				if(d instanceof CharacterDice){
					playerDamage += d.value;
					while(d.value >= 5){
						d.roll();
						playerDamage += d.value;
					}
				}
			}
			playerAttack = "Vous avez fait " + playerDamage + " dégâts";
			ennemy.life -= playerDamage;
		}
		
		if(ennemy.life > 0){
			actionEnnemy(game.dungeonDice.value);
			game.diceHasRolled = false;
			game.canMove = false;
		}else{
			game.diceHasRolled = false;
			result = "Bravo vous avez terrasé " + ennemy.name;
			game.selectedClass.addStat(game.selectedClass.xpString, ennemy.reward);
			hasTakenReward = true;
			
		}
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

	@Override
	public void drawAdditional(Graphics2D g2, int x, int y) {
		g2.setColor(Color.white);
		Font defaultFont = g2.getFont();
		g2.drawString(ennemy.name, x-(int)Utils.textToRectangle2D(ennemy.name, g2).getWidth()/2, y);
		g2.drawString("PV : " + ennemy.life+"/"+ennemy.totalLife, x-(int)Utils.textToRectangle2D("PV : " + ennemy.life+"/"+ennemy.totalLife, g2).getWidth()/2, y+30);
		g2.drawString("Dégats : " + Integer.toString(ennemy.damage), x-(int)Utils.textToRectangle2D("Dégats : " + Integer.toString(ennemy.damage), g2).getWidth()/2, y+60);
		g2.drawString("Récompense : "+ Integer.toString(ennemy.reward) + " XP", x-(int)Utils.textToRectangle2D("Récompense : "+ Integer.toString(ennemy.reward)+ " XP", g2).getWidth()/2 , y+90);
		
		g2.drawLine(0, y+110, game.gui.xLine, y+110);
		g2.setFont(game.sansSerif);
		g2.drawString("Combat", x-(int)Utils.textToRectangle2D("Combat", g2).getWidth()/2, y+130);
		g2.setFont(defaultFont);
		g2.drawString(playerAttack, x-(int)Utils.textToRectangle2D(playerAttack, g2).getWidth()/2, y+150);
		g2.setColor(Color.red);
		g2.drawString(ennemyAttack, x-(int)Utils.textToRectangle2D(ennemyAttack, g2).getWidth()/2, y+170);
		g2.setColor(Color.white);
		g2.drawString(result, x-(int)Utils.textToRectangle2D(result, g2).getWidth()/2, y+190);


	}


}
