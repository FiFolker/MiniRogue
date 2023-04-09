package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class GUI {
	
	Game game;
	int xLine = 300;

	public GUI(Game game){
		this.game = game;

	}

	public void update(){
		
	}

	public void draw(Graphics2D g2){
		g2.setColor(Color.white);
		g2.setFont(game.title);
		g2.drawString("Etage n°"+game.stage+"/"+game.totalStage, game.getWidth()/2, 40);
		g2.setFont(game.secondTitle);
		g2.drawString("Zone n°"+game.zone+"/"+game.zonePerStage[game.stage-1], game.getWidth()/2, 80);
		g2.drawLine(xLine, 0, xLine, game.getHeight());
		g2.setFont(game.sansSerif);
		game.selectedClass.drawCard(g2, 25, 25);
		g2.drawLine(0,  game.selectedClass.size*3/2, xLine, game.selectedClass.size *3/2);
		g2.drawString("Stats", xLine/2-(int)Utils.textToRectangle2D("Stats", g2).getWidth()/2, game.selectedClass.size*3/2+20);
		int yStats = game.selectedClass.size*2;
		for(String s : game.selectedClass.stats.keySet()){
			if(s.equals("XP")){
				g2.drawString(s + " : " + game.selectedClass.stats.get(s) +"/"+game.selectedClass.xpRequired, 10, yStats);
			}else{
				g2.drawString(s + " : " + game.selectedClass.stats.get(s), 10, yStats);
			}
			yStats += 20;
		}
	}

}
