package main;

import java.awt.Color;
import java.awt.Graphics2D;

import cards.EnnemyCard;

public class Lose implements IUpdateAndDraw {
	
	Game game;
	EnnemyCard e;

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
		if(game.currentCard.getClass() == EnnemyCard.class){
			e = (EnnemyCard)game.currentCard;
		}
		g2.drawString("Vous avez été tué par " + e.ennemy.name, Utils.getXforCenteredText("Vous avez été tué par " + e.ennemy.name, g2), game.getHeight()/2 + 70);
	}

}
