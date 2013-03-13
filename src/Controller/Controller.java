package Controller;

import org.newdawn.slick.Image;

import Pion.Pion;
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
		placementJoueur1 = true;
		this.game = new Game();
	}

	public Controller(Game game)
	{
		super();
		tour = true;
		partieFinie=false;
		this.game = game;
		placementJoueur1 = true;
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
	 * Methode qui est appel� par le view � chaque fois que l'on a fait un clique.
	 * Cette methode appel placePion;
	 */
	public void setClick(int click)
	{
		lastClick = newClick;
		newClick = click;
		placePion();
	}

	/**
	 * renvoit vrai si le pion sur lequel on a cliquer peut etre d�placer
	 * faux sinon.
	 */
	public boolean checkMove()
	{
		return true;
	}
	
	public boolean getPlacement()
	{
		return placementJoueur1;
	}
	
	public Game getGame()
	{
		return game;
	}

	public boolean checkClick(int i)
	{
		int x = i/10;
		int y = i%10;
		if(placementJoueur1==true)
		{
			
		}
		return true;
	}

	/**
	 * Methode qui est continuellement appel� (indirectement) par la view, et qui regarde si
	 * on a tent� de faire un d�placement, si oui, s'il est possible, et si oui, il l'effectue.
	 * elle ne fait rien, si on a par exemple cliqu� n'importe ou.
	 */
	public void placePion()
	{
		if(prise!=null)
		{
			System.out.println(prise);
			//Quand on place un pion.
			if(placementJoueur1 && game.checkNumberOfPion(prise))
			{
				int x = newClick/10;
				int y = newClick%10;
				if(x>5)
				{
					//si on veut remplacer un pion d�ja existant sur la carte par un autre.
					game.removePion(x, y);	
					game.createAndPlacePion(prise, x, y, tour);
				}
			}
			else
			{
				//Quand on d�place un pion.
				int oldX = lastClick/10;
				int oldY = lastClick%10;
				int x = newClick/10;
				int y = newClick%10;
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
					partieFinie = !game.checkLost(tour);
					tour=!tour;
				}

			}
		}

	}

}



	

	

		
