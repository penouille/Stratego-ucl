package Controller;
import Game.*;
	
/** 
 *  Cette classe decouple l'interface graphique de la logique de l'application. Elle 
 * s'occupe de traduire les actions effectuees sur l'interface en actions comprises
 * par les "objets metier" de l'application 
 */  
public class controller 
{   
	
	Game game = new Game();
	public boolean Tour;
	
	public controller()
	{
		
		Tour = true;
		
	}
	
	
}   
	