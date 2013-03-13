package Controller;

import View.AdminGame;
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

public class Controller
{   
	
	//Game game;
	AdminGame admin;
	
	public boolean tour;
	private boolean placementJoueur1;
	private boolean placementJoueur2;
	private String prise;
	private int lastClick;
	private int newClick;
	private boolean partieFinie;
	
	private Game game;
	
	public Controller()
	{
		super();
		tour = true;
		partieFinie=false;
		this.game = new Game();
		placementJoueur1=true;
		placementJoueur2=true;
	}
	public Game getGame() 
	{
		return this.game;
	}	
	public boolean getPlacementJoueur1()
	{
		return this.placementJoueur1;
	}
	public boolean getPlacementJoueur2()
	{
		return this.placementJoueur2;
	}
	public boolean getPlacement()
	{
		return(placementJoueur1 || placementJoueur2);
	}
	public void setPrise(String prise)
	{
		this.prise = prise;
	}
	
	public String getPrise()
	{
		return this.prise;
	}
	
	/**
	 * @param click
	 * Methode qui est appelé par le view à chaque fois que l'on a fait un clique.
	 * Cette methode appel placePion;
	 */
	public void setClick(int click)
	{
		lastClick = newClick;
		newClick = click;
		placePion();
	}
	
	/**
	 * Methode qui est continuellement appelé (indirectement) par la view, et qui regarde si
	 * on a tenté de faire un déplacement, si oui, s'il est possible, et si oui, il l'effectue.
	 * elle ne fait rien, si on a par exemple cliqué n'importe ou.
	 */
	public void placePion()
	{
		if(prise!=null)
		{
			int x = newClick/10;
			int y = newClick%10;
			//Quand c'est au tour de joueur1 de placer ses pions.
			if(placementJoueur1 && x>5 && tour && game.checkNumberOfPion(prise, 6, 9))
			{
				System.out.println(x);
				//si on veut remplacer un pion déja existant sur la carte par un autre.
				game.removePion(x, y);	
				game.createAndPlacePion(prise, x, y, tour);
			}
			//Quand c'est au tour de joueur2 de placer ses pions.
			else if(placementJoueur2 && x<4 && !tour && game.checkNumberOfPion(prise, 0, 3))
			{
				System.out.println(x);
				//si on veut remplacer un pion déja existant sur la carte par un autre.
				game.removePion(x, y);	
				game.createAndPlacePion(prise, x, y, tour);
			}
			else
			{
				//Quand on déplace un pion.
				int oldX = lastClick/10;
				int oldY = lastClick%10;
				if(game.canMoveOnNewCase(oldX, oldY, x, y, tour))
				{
					int resultFight = game.checkNewCase(oldX, oldY, x, y);
					if(game.getMap().getPion(x, y).getName().equals("drapeau")) partieFinie=true;
					switch(resultFight)
					{
					case 10: game.placePion(oldX, oldY, x, y); break;
					case 0:  game.removePion(oldX, oldY); game.removePion(x, y); break;
					case 1:  game.removePion(oldX, oldY); break;
					case 2:  game.removePion(x, y); game.placePion(oldX, oldY, x, y); break;
					}
					//check si après le déplacement du pion d'un joueur, l'autre joueur sait encore jouer.
					partieFinie = game.checkLost(!tour);
					tour=!tour;
				}
				
			}
			System.out.println(game.getMap().getPion(x, y));
		}
		
	}
}
	
