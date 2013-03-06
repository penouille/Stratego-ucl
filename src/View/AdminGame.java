package View;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class AdminGame extends StateBasedGame
{
	static boolean Tour = true;
	
	public AdminGame(String title)
	{
		super(title);
	}
	
	
	public void initStatesList(GameContainer container) throws SlickException
	{
		this.addState(new CurrentGame());
		this.addState(new AdversGame());
		this.addState(new Intermediaire());
	}
	
	public static void main(String[] argv) 
	{
		try 
		{
            AppGameContainer container = new AppGameContainer(new AdminGame("Yo"));
            container.setDisplayMode(1150,720,false);
            container.setShowFPS(false);
            container.setAlwaysRender(true);
            
            container.start();
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
}
