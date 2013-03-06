package Controller;
import Game.*;
import View.AdminGame;
	
/** 
 *  Cette classe decouple l'interface graphique de la logique de l'application. Elle 
 * s'occupe de traduire les actions effectuees sur l'interface en actions comprises
 * par les "objets metier" de l'application 
 */  
public class controller 
{   
	
	Game game;
	AdminGame admin;
	
	public boolean Tour;
	
	public controller()
	{
		
		
		Tour = true;
		
	}
	
	/**
	 * Lance une partie.
	 */
	public void newGame ()
	{
		game = new Game();
		
		admin = new AdminGame("");
		
	}
	
}   
	