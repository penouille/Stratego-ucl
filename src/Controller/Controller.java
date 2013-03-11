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
	
	public boolean Tour;
	private boolean placement;
	private Image prise;
	
	private Game game;
	
	private AdministratorGUI interfaces;
	
	public Controller()
	{
		super();		
	}
	
	public Controller(Game game)
	{
		// TODO Auto-generated constructor stub
		super();
		this.game = game;
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
	
	/**
	 * renvoit vrai si le pion sur lequel on a cliquer peut etre déplacer
	 * faux sinon.
	 */
	public boolean checkMove()
	{
		
	}
	
	public boolean checkClick(int i)
	{
		int x = i/10;
		int y = i%10;
		if(placement==true)
		{
			
		}
	}
	
	/**
	 * pre:
	 * post: Renvoit 
	 */
	public ArrayList<int> checkandroitoujepeuxposercesatanepion()
	{
		return null;
	}
	
	public void placePion(String pionPath, int x, int y, boolean joueur)
	{
		
		game.placePion(pionPath, x, y, joueur);
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
	
