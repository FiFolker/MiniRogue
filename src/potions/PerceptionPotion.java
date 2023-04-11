package potions;


import main.Game;
import main.Utils;

public class PerceptionPotion extends Potion{

	public PerceptionPotion(Game game) {
		super(game, Utils.loadImage("assets/potions/perceptionPotion.png"));
		name = "Perception réussi le test de compétence ou soigne l'aveuglement";
		addButtton();
	}

	@Override
	public void applyEffect() {
		if(game.currentCard.needSkillTest){
			game.characterDices.get(0).value = 6;
			game.perceptionEffect = true;
			Potion.removePotion(this);
		}else{
			error = true;
			errorString = "Vous devez l'utiliser sur une carte nécéssitant un test de compétence où si vous êtes aveuglé !";
		}
	}
	
}
