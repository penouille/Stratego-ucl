package Controller;

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


	@Override
	void control() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void placePion(String pion, int x, int y)
	{
		game.placePion(pion, x, y);
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
	
