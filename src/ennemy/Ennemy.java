package ennemy;

import dices.Dice;

public class Ennemy {

    public int life;
    public int reward;
    public String name;
    public int damage;
    public Dice applicableDice;
    public int totalLife;

    public Ennemy(String name, int life, int damage, int reward, Dice applicableDice){
        this.life = life;
        totalLife = this.life;
        this.reward = reward;
        this.damage = damage;
        this.name = name;
        this.applicableDice = applicableDice;
    }
    
}
