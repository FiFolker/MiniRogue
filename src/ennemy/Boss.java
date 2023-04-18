package ennemy;

import dices.Dice;
import effect.Effect;
import main.Game;
import rewardAndPenalty.Reward;

public class Boss extends Ennemy{

    public int nbPhase;
    public int[] lifes;
    public int[] damages;
    public Dice[] applicableDices;
    public int currentPhase = 1;

    public Boss(Game game, String name, int[] lifes, int[] damages, Reward reward, Dice[] applicableDices, int nbPhase) {
        super(game, name, lifes[0], damages[0], reward, applicableDices[0]);
        this.nbPhase = nbPhase;
        this.lifes = lifes;
        this.damages = damages;
        this.applicableDices = applicableDices;
    }

    @Override
    public void nextPhase(){
        if(currentPhase < nbPhase){
            finish = false;
            currentPhase ++;
            life = lifes[currentPhase-1];
            totalLife = lifes[currentPhase-1];
            damage = damages[currentPhase-1];
            applicableDice = applicableDices[currentPhase-1];
        }else{
            finish = true;
            System.out.println("Vous avez battu " + name);
        }
    }
    
}
