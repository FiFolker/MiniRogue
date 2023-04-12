package effect;

import java.util.ArrayList;

import dices.Dice;
import main.Game;

public abstract class Effect {
    
    public String name;
    String info;
    Game game;

    public Effect(Game game, String name, String info){
        this.game = game;
        this.name = name;
        this.info = info;
    }

    public abstract void applyEffect();

}
