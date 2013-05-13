package View;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Controller.Controller;

public class AdminGame extends StateBasedGame
{
	private Controller controller;
	private CurrentGame current;
	
	
	public AdminGame(String title, Controller controller)
	{
		super(title);
		
		this.controller = controller;
		
		try
		{
            AppGameContainer container = new AppGameContainer(this);
            container.setTargetFrameRate(60); // Limite le nombre de fps à 60
            container.setDisplayMode(1200,730,false);
            container.setShowFPS(true);
            container.setAlwaysRender(true);
            
            container.start();
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}


	public void initStatesList(GameContainer container) throws SlickException
	{
		current = new CurrentGame(controller);
		this.addState(current);
		this.addState(new Intermediaire(controller , current ));
	}
}
