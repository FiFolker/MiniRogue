package main;

import java.awt.Color;
import java.awt.Graphics2D;

import cards.EnnemyCard;
import cards.GuardianCard;
import cards.TrapCard;

public class Win implements IUpdateAndDraw {
	
	Game game;

	public Win(Game game){
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
		g2.drawString("VOUS AVEZ GAGNÉ !", Utils.getXforCenteredText("VOUS AVEZ GAGNÉ !", g2), game.getHeight()/2);
		g2.setFont(game.secondTitle);
		g2.drawString("Appuyer sur ECHAP pour relancer", Utils.getXforCenteredText("Appuyer sur ECHAP pour relancer", g2), game.getHeight()/2 + 50);
		g2.setFont(game.sansSerif);
		
		g2.drawString("Vous avez vaincu Les Restes d'OG et terminé ce donjon !", Utils.getXforCenteredText("Vous avez vaincu Les Restes d'OG et terminé ce donjon !", g2), game.getHeight()/2 + 70);
		
	}

}
