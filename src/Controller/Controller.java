package Controller;

import org.newdawn.slick.Image;

import Pion.Pion;
import View.AdminGame;
import View.AdministratorGUI;
import Game.Game;


/**
=======
import Game.*;
import View.AdminGame;
	
/** 
>>>>>>> branch 'master' of https://github.com/penouille/Stratego-ucl.git
 *  Cette classe decouple l'interface graphique de la logique de l'application. Elle 
 * s'occupe de traduire les actions effectuees sur l'interface en actions comprises
 * par les "objets metier" de l'application 
<<<<<<< HEAD
 */

public class Controller extends AbstractController
{   
	
	//Game game;
	AdminGame admin;
	
	public boolean tour;
	private boolean placement;
	private Image prise;
	private int lastClick;
	private int newClick;
	
	private Game game;
	
	public Controller()
	{
		super();
	}
	
	public Controller(Game game)
	{
		super();
		tour = true;
		this.game = game;
	}
	
	public void setClick(int click)
	{
		lastClick = newClick;
		newClick = click;
		placePion();
	}
	
	public void setPrise(Image prise)
	{
		this.prise = prise;
	}
	
	public Image getPrise()
	{
		return this.prise;
	}


	@Override
	void control() 
	{
		// TODO Auto-generated method stub
		
		
	}
	
	public void placePion()
	{
		if(prise!=null)
		{
			String pionPath = prise.getResourceReference();
			if(placement && game.checkNumberOfPion(pionPath))
			{
				int x = newClick/10;
				int y = newClick%10;
				if(x<4) game.createAndPlacePion(pionPath, x, y, tour);
			}
			else
			{
				//TODO Quand on déplace un pion.
				int oldX = lastClick/10;
				int oldY = lastClick%10;
				int x = newClick/10;
				int y = newClick%10;
				if(game.canMove(oldX, oldY, x, y, tour))
				{
					game.placePion(oldX, oldY, x, y);
					tour=!tour;
				}
				
			}
		}
		
	}
	
	/**
	 * Lance une partie.
	 */
	/*public void newGame ()
	{
		//game = new Game();
		
		admin = new AdminGame("Stratego");
		
	}*/
	
}
	
