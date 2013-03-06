package View;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class AdversGame extends BasicGameState implements InputProviderListener
{

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException 
			{
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException 
			{
		// TODO Auto-generated method stub
		
		arg2.drawString("Joueur adverse", 100, 100);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int arg2)
			throws SlickException {
		
		if ( container.getInput().isKeyPressed(Input.KEY_T))
 	   {
			AdminGame.Tour = false;
			
 		   game.enterState(2,new  FadeOutTransition() , new FadeInTransition()) ;
 	   }
		
	}

	@Override
	public void controlPressed(Command arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlReleased(Command arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
