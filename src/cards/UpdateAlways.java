package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Coordonnees;
import main.Game;

public abstract class UpdateAlways extends Card {
    
    public UpdateAlways(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
    }
    public abstract void drawAdditional(Graphics2D g2);
    public abstract void updateAlways();

    @Override
    public void update() {
        super.update();
        if(game.currentPos.equals(this.coord) && !isFinish){
            updateAlways();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if(game.currentPos.equals(this.coord)){
            drawAdditional(g2);
        }
    }

}
