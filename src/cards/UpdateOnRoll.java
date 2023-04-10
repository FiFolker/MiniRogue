package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import dices.Dice;
import main.Coordonnees;
import main.Game;

public abstract class UpdateOnRoll extends Card{
    
    public UpdateOnRoll(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
        //TODO Auto-generated constructor stub
    }

    public abstract void updateOnRoll();

    public abstract void drawAdditional(Graphics2D g2, int x, int y);

    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
        if(game.currentPos.equals(this.coord) && !hasTakenReward && game.diceHasRolled){
            updateOnRoll();
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        // TODO Auto-generated method stub
        super.draw(g2);
        if(game.currentPos.equals(this.coord)){
            drawAdditional(g2, game.choicePlaceX, game.choicePlaceY);
        }
    }
}
