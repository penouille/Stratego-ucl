package Controller;

import View.AdminGame;
import View.Option;
import Game.Game;
import Intelligence.Artificielle;
import Intelligence.ArtificielleFacile;
import Intelligence.ArtificielleIntermediaire;
import Intelligence.ArtificielleKikoo;
import Intelligence.ArtificielleNormal;
import Intelligence.Deplacement;
import Intelligence.Joueur;

	
/** 
 *  Cette classe decouple l'interface graphique de la logique de l'application. Elle 
 * s'occupe de traduire les actions effectuees sur l'interface en actions comprises
 * par les "objets metier" de l'application 
 */

public class Controller
{
	AdminGame admin;
	
	public boolean tour;
	private boolean placementJoueur1;
	private boolean placementJoueur2;
	private String prise;
	private int lastClick;
	private int newClick;
	private boolean partieFinie;
	private String gagnant;
	private boolean isAnIA;
	
	private Artificielle IA;
	
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
	public void setIA(boolean isAnIA)
	{
		this.isAnIA = isAnIA;
		if(game.getJ1().getPrefDiff().equals("Kikoo")){ IA = new ArtificielleKikoo(this); }
		else if(game.getJ1().getPrefDiff().equals("Facile")){ IA = new ArtificielleFacile(this); }
		else if(game.getJ1().getPrefDiff().equals("Normal")){ IA = new ArtificielleNormal(this); }
		else if(game.getJ1().getPrefDiff().equals("Intermediaire")){ IA = new ArtificielleIntermediaire(this); }
	}
	public void changeIA(String diff)
	{
		if(isAnIA)
		{
			if(diff.equals("Kikoo")) IA = new ArtificielleKikoo(this);
			else if(diff.equals("Facile")) IA = new ArtificielleFacile(this);
			else if(diff.equals("Normal")) IA = new ArtificielleNormal(this);
		}
		getGame().getJ1().setPrefDiff(diff);
	}
	public boolean getTour()
	{
		return this.tour;
	}
	public boolean getPartieFinie()
	{
		return this.partieFinie;
	}
	public void setPartieFinie(boolean b)
	{
		this.partieFinie = b;
	}
	public void setGagnant(String g)
	{
		this.gagnant = g;
	}
	
	/**
	 * @return Renvoit true si l'un ou l'autre joueur est en phase de placement.
	 */
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
	
	public int getNewClick()
	{
		return newClick;
	}
	
	public int getLastClick()
	{
		return lastClick;
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
	
	public boolean checkStopPJ1()
	{
		if(game.checkHaveAllPionsPlaced(6,9))
		{
			placementJoueur1=false;
			tour=!tour;
			if(isAnIA)
			{
				IA.placeYourPions();
			}
			return true;
		}
		else return false;
	}
	
	public boolean checkStopPJ2()
	{
		if(game.checkHaveAllPionsPlaced(0,3))
		{
			placementJoueur2=false;
			tour=!tour;
			return true;
		}
		else return false;
	}
	
	/**
	 * Methode qui est continuellement appelé (indirectement) par la view, et qui regarde si
	 * on a tenté de faire un déplacement, si oui, s'il est possible, et si oui, il l'effectue.
	 * elle ne fait rien, si on a cliqué n'importe ou.
	 */
	public void placePion()
	{
		if(prise!=null)
		{
			int x = newClick/10;
			int y = newClick%10;
			//Quand c'est au tour de joueur1 de placer ses pions.
			
			/*System.out.println("placementJoueur1 = "+placementJoueur1);
			System.out.println("x>5 ? = "+ (x>5));
			System.out.println("tour = "+tour);
			System.out.println("Assez de nombre ? = "+game.checkNumberOfPion(prise, 6, 9));*/
			
			if(placementJoueur1 && x>5 && tour && game.checkNumberOfPion(prise, 6, 9))
			{
				//si on veut remplacer un pion déja existant sur la carte par un autre.
				System.out.println(x);
				game.removePion(x, y);	
				game.createAndPlacePion(prise, x, y, tour);
			}
			//Quand c'est au tour de joueur2 de placer ses pions.
			else if(placementJoueur2 && x<4 && !tour && game.checkNumberOfPion(prise, 0, 3))
			{
				//si on veut remplacer un pion déja existant sur la carte par un autre.
				System.out.println(x);
				game.removePion(x, y);	
				game.createAndPlacePion(prise, x, y, tour);
			}
			else if(!getPlacement() && !partieFinie)
			{
				//Quand on déplace un pion.
				int oldX = lastClick/10;
				int oldY = lastClick%10;
				//System.out.println("Peut etre bougé ? = "+game.canMoveOnNewCase(oldX, oldY, x, y, tour));
				if(game.canMoveOnNewCase(oldX, oldY, x, y, tour))
				{
					int resultFight = game.checkNewCase(oldX, oldY, x, y);
					
					switch(resultFight)
					{
					case 10:game.placePion(oldX, oldY, x, y);
							if(isAnIA && game.getMap().getPion(x, y).getName().equals("eclaireur"))
							{
								IA.checkIfEclaireur(oldX, oldY, x, y);
							}
							break;
					case 0: game.removePion(oldX, oldY);
							game.removePion(x, y);
							//check si après l'élimination des deux pions, les deux joueurs savent encore jouer.
							if(!partieFinie && game.checkLost(!tour) && game.checkLost(tour))
							{
								partieFinie=true;
							}
							if(!partieFinie && game.checkLost(!tour))
							{
								partieFinie=true; 
								gagnant=tour+"";
							}
							if(!partieFinie && game.checkLost(tour))
							{
								partieFinie=true; 
								gagnant=!tour+"";
							}
							break;
					case 1: game.removePion(oldX, oldY);
							if(isAnIA) game.getMap().getPion(x, y).setVisibleByIA(true);
							//check si après la perte du pion, le joueur sait encore jouer.
							if(!partieFinie && game.checkLost(tour))
							{
								partieFinie=true;
								gagnant = !tour+"";
							}
							break;
					case 2: if(game.getMap().getPion(x, y).getName().equals("drapeau")) partieFinie=true;
							game.removePion(x, y);
							if(isAnIA) game.getMap().getPion(oldX, oldY).setVisibleByIA(true);
							game.placePion(oldX, oldY, x, y);
							//check si après le déplacement du pion d'un joueur, l'autre joueur sait encore jouer.
							if(!partieFinie && game.checkLost(!tour))
							{
								partieFinie=true;
								gagnant = tour+"";
							}
							break;
					}
					tour=!tour;
					if(isAnIA && !tour && !partieFinie)
					{
						Deplacement depl = new Deplacement(oldX, oldY, x, y);
						IA.play(depl);
						//tour=!tour;
					}
				}
			}
			//System.out.println("Le pion = "+game.getMap().getPion(x, y));
		}
	}
	
	public void dude()
	{
		game.dude(tour);
	}
}