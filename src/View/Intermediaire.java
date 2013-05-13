package View;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Controller.Controller;

public class Intermediaire extends BasicGameState implements InputProviderListener
{
	
	private Controller controller;
	private CurrentGame current;
	private MouseOverArea finDuTour;
	
	public Intermediaire(Controller controller, CurrentGame current)
	{
		super();
		
		this.current = current;
		
		this.controller = controller;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		finDuTour = new MouseOverArea( arg0, new Image("finDuTour.png") ,520 , 310 );
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException 
			{
		finDuTour.render(arg0 , arg2);
		
		arg2.drawString("Tour suivant.", 100, 100);
		
		if(!controller.getPlacement())
		{
			System.out.println("victime"+controller.getVictime());
			if ( controller.getVictime() )//&& controller.getGame().getMap().getPion(controller.getNewClick()/10, controller.getNewClick()%10) != null)
			{
				arg2.drawString("Dernier coup joué: "+ controller.getGame().getMap().getPion(controller.getNewClick()/10, controller.getNewClick()%10).getName()+ " déplacé de "+ current.lettres[controller.getLastClick()%10]+","+controller.getLastClick()/10+
						" à "+current.lettres[controller.getNewClick()%10]+","+controller.getNewClick()/10, 100, 200);
			}
			else
			{
				arg2.drawString("Dernier coup joué:"+ current.lettres[controller.getLastClick()%10]+","+controller.getLastClick()/10+
						" placé en "+current.lettres[controller.getNewClick()%10]+","+controller.getNewClick()/10, 100, 200);
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int arg2)
			throws SlickException {
		
		if ( container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && finDuTour.isMouseOver())
 	   {
			game.enterState(0,new  FadeOutTransition() , new FadeInTransition()) ;
			
			controller.setVictime();
				
			current.setTourView(!current.getTourView());
				
			current.UpGame2();
 	   }
		
	}

	@Override
	public void controlPressed(Command arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlReleased(Command arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() 
	{
		// TODO Auto-generated method stub
		return 2;
	}

}
