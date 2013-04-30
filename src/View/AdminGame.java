package View;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Controller.Controller;

public class AdminGame extends StateBasedGame
{
	static boolean Tour = true;
	
	private Controller controller;
	
	private Option optFrame;
	
	public AdminGame(String title)
	{
		super(title);
		
		try
		{
            AppGameContainer container = new AppGameContainer(this);
            container.setDisplayMode(1150,700,false);
            container.setShowFPS(false);
            container.setAlwaysRender(true);
            
            container.start();
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public AdminGame(String title, Controller controller, Option optFrame)
	{
		super(title);
		
		this.controller = controller;
		
		this.optFrame = optFrame;
		
		try
		{
            AppGameContainer container = new AppGameContainer(this);
            container.setDisplayMode(1150,700,false);
            container.setShowFPS(false);
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
		this.addState(new CurrentGame(controller, optFrame));
		this.addState(new AdversGame(controller));
		this.addState(new Intermediaire(controller));
	}
}
