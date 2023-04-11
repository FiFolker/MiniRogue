package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class GUI implements IUpdateAndDraw{
	
	Game game;
	public int xLine = 320;
	int yStats = 212;
	int yInv = 406;
	public int yPotions = yInv +35;
	public int yObject = yInv +85;
	public int yChoice = 546;
	public String potions = "Potions :";
	public String object = "Objet :";

	public GUI(Game game){
		this.game = game;

	}

	@Override
	public void update(){
		
	}
	
	@Override
	public void draw(Graphics2D g2){
		g2.setColor(Color.white);
		g2.setFont(game.title);
		g2.drawString("Etage n°"+game.stage+"/"+game.totalStage, game.getWidth()/2, 40);
		g2.setFont(game.secondTitle);
		g2.drawString("Zone n°"+game.zone+"/"+game.zonePerStage[game.stage-1], game.getWidth()/2 + 275, 40);
		g2.drawLine(xLine, 0, xLine, game.getHeight());
		g2.setFont(game.sansSerif);
		game.selectedClass.drawCard(g2, 25, 25);
		g2.drawLine(0,  game.selectedClass.size*3/2, xLine, yStats - 20);
		g2.drawString("Stats", xLine/2-(int)Utils.textToRectangle2D("Stats", g2).getWidth()/2, yStats);
		int yStatsbyStat = game.selectedClass.size*2;
		for(String s : game.selectedClass.stats.keySet()){
			if(s.equals("XP")){
				g2.drawString(s + " : " + game.selectedClass.stats.get(s) +" / "+game.selectedClass.xpRequired, 10, yStatsbyStat);
			}else{
				g2.drawString(s + " : " + game.selectedClass.stats.get(s) + " / "+game.selectedClass.maxStats.get(s), 10, yStatsbyStat);
			}
			yStatsbyStat += 20;
		}
		g2.drawLine(0,  yInv - 20, xLine, yInv - 20);
		g2.drawString("Inventaire", xLine/2-(int)Utils.textToRectangle2D("Inventaire", g2).getWidth()/2, yInv);
		g2.drawString(potions, 10, yPotions);
		g2.drawString(object, 10, yObject);
		g2.drawLine(0,  yChoice-20, xLine, yChoice-20);
		g2.drawString("Interaction", xLine/2-(int)Utils.textToRectangle2D("Interaction", g2).getWidth()/2, yChoice);

	}

}
