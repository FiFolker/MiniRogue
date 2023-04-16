package cards;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Coordonnees;
import main.Game;

public class GhostCard extends UpdateAlways{

    public GhostCard(Game game, Rectangle hitbox, int x, int y, Coordonnees coord) {
        super(game, hitbox, x, y, coord);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void drawAdditional(Graphics2D g2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawAdditional'");
    }

    @Override
    public void updateAlways() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAlways'");
    }
    
}
