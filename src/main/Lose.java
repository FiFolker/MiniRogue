package main;

import java.awt.Color;
import java.awt.Graphics2D;

import cards.EnnemyCard;
import cards.GuardianCard;
import cards.TrapCard;

public class Lose implements IUpdateAndDraw {
	
	Game game;

	public Lose(Game game){
		this.game = game;
	}

	@Override
	public void update() {
		if(game.keyH.escapePressed){
			game.gameState = game.menuState;
		}	
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.white);
		g2.setFont(game.title);
		g2.drawString("VOUS ÊTES MORT !", Utils.getXforCenteredText("VOUS ÊTES MORT !", g2), game.getHeight()/2);
		g2.setFont(game.secondTitle);
		g2.drawString("Appuyer sur ECHAP pour relancer", Utils.getXforCenteredText("Appuyer sur ECHAP pour relancer", g2), game.getHeight()/2 + 50);
		g2.setFont(game.sansSerif);
		if(game.currentCard instanceof EnnemyCard){
			EnnemyCard e = (EnnemyCard)game.currentCard;
			g2.drawString("Vous avez été tué par " + e.ennemy.name, Utils.getXforCenteredText("Vous avez été tué par " + e.ennemy.name, g2), game.getHeight()/2 + 70);
		}else if(game.currentCard instanceof GuardianCard){
			GuardianCard g = (GuardianCard)game.currentCard;
			g2.drawString("Vous avez été tué par " + g.ennemy.name, Utils.getXforCenteredText("Vous avez été tué par " + g.ennemy.name, g2), game.getHeight()/2 + 70);
		}else if(game.currentCard instanceof TrapCard){
			g2.drawString("Vous êtes tombé dans un piège !", Utils.getXforCenteredText("Vous êtes tombé dans un piège !", g2), game.getHeight()/2 + 70);
		}else if(game.poisonDice != null){
			g2.drawString("Vous êtes mort de poison !", Utils.getXforCenteredText("Vous êtes mort de poison !", g2), game.getHeight()/2 + 70);
		}
	}

}
