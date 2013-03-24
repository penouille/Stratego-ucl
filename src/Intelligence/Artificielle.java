package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Game.Game;
import Game.Map;
import Pion.Bombe;
import Pion.Capitaine;
import Pion.Colonel;
import Pion.Commandant;
import Pion.Demineur;
import Pion.Drapeau;
import Pion.Eclaireur;
import Pion.Espion;
import Pion.General;
import Pion.Lieutenant;
import Pion.Marechal;
import Pion.Pion;
import Pion.Sergent;

public class Artificielle 
{
	private Map iaMap;
	
	private int [] [] influenceMap;
	
	private Controller controller;
	
	private ArrayList<Pion> ListePion;
	
	private ArrayList<Deplacement> listOfDisplacement;
	
	private Game game;
	
	public Artificielle(Controller controller)
	{
		this.controller = controller;
		iaMap = controller.getGame().getMap();
		influenceMap = new int [10] [10];
		game = controller.getGame();
	}
	
	private void setInfluence(int x, int y, int influence)
	{
		influenceMap [x][y] = influence;
	}
	
	public int getInfluenceMap(int x, int y)
	{
		return influenceMap[x][y];
	}
	
	private void printInfluenceMap()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				System.out.print(getInfluenceMap(i, j)+" ");
			}
			System.out.println();
		}
	}
	
	private void printList()
	{
		for(Deplacement i : listOfDisplacement)
		{
			System.out.println("oldX = "+i.getOldX()+", oldY = "+i.getOldY()+", x = "+i.getX()+", y = "+i.getY()+", et l'influence = "+i.getInfluence());
		}
	}
	
	public void initializePions()
	{
		int i;
		ListePion = new ArrayList<Pion>();
		ListePion.add(new Drapeau(false));
		for(i=1; i<7; i++)
		{
			ListePion.add(new Bombe(false));
		}
		ListePion.add(new Espion(false));
		for(i=8; i<16; i++)
		{
			ListePion.add(new Eclaireur(false));
		}
		for(i=16; i<21; i++)
		{
			ListePion.add(new Demineur(false));
		}
		for(i=21; i<25; i++)
		{
			ListePion.add(new Sergent(false));
		}
		for(i=25; i<29; i++)
		{
			ListePion.add(new Lieutenant(false));
		}
		for(i=29; i<33; i++)
		{
			ListePion.add(new Capitaine(false));
		}
		for(i=33; i<36; i++)
		{
			ListePion.add(new Commandant(false));
		}
		for(i=36; i<38; i++)
		{
			ListePion.add(new Colonel(false));
		}
		ListePion.add(new General(false));
		ListePion.add(new Marechal(false));
		
		for(Pion pion: ListePion)
		{
			pion.setVisibleByIA(true);
		}
	}

	public void placeYourPions()
	{
		// TODO Auto-generated method stub
		initializePions();
		int t; Random r = new Random();
		for(int i=0; i<4; i++)
		{
			for(int j=0;j<10;j++)
			{
				t = r.nextInt(ListePion.size());
				iaMap.setEtat(i, j, ListePion.get(t));
				ListePion.remove(t);
			}
		}
		//updateInfluenceMap();
		//printInfluenceMap();
		controller.checkStopPJ2();
	}
	

	/*public int getInfluence(int oldX, int oldY, int x, int y)
	{
		//si la case est un blackout
		if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getName().equals("blackout"))
		{
			return iaMap.getPion(x, y).getForce()*-1;
		}
		//si le pion sur la case x,y m'appartient
		else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getTeam())
		{
			return -5;
		}
		//Cas à part pour les eclaireurs.
		else if(iaMap.getPion(oldX, oldY) !=null && iaMap.getPion(oldX, oldY).getName().equals("eclaireur"))
		{
			//si la case est vide mais que l'influence dessus est superieur à la force de l'eclaireur
			if(iaMap.getPion(x, y)==null && getInfluenceMap(x, y)>iaMap.getPion(oldX, oldY).getForce())
			{
				return getInfluenceMap(x, y);
			}
			//si la case est vide, et que l'influence dessus est inferieur à la force de l'eclaireur
			else if(iaMap.getPion(x, y)==null)
			{
				return iaMap.getPion(oldX, oldY).getForce();
			}
			//si il y a un pion, mais qu'on le connait.
			else if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getVisibleByIA())
			{
				if(iaMap.getPion(oldX, oldY).getForce()>iaMap.getPion(x, y).getForce())
				{
					return 21;
				}
				else if(iaMap.getPion(oldX, oldY).getForce()==iaMap.getPion(x, y).getForce())
				{
					return 0;
				}
				else return -10;
			}
			//si il y a un pion, mais qu'on ne le connait pas.
			//je priviligie l'exploration avec les eclaireurs
			else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getVisibleByIA())
			{
				return 20;
			}
			
		}
		//si la case sur laquelle on souhaite se deplacer est vide, l'influence est egal à la force du pion qui s'y déplace
		else if(iaMap.getPion(x, y)==null)
		{
			if(getInfluenceMap(x, y)>iaMap.getPion(oldX, oldY).getForce()) return getInfluenceMap(x, y);
			else return iaMap.getPion(oldX, oldY).getForce();
		}
		//si il y a un pion, mais qu'on ne le connait pas.
		else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getVisibleByIA() )
		{
			if(getInfluenceMap(x, y)>iaMap.getPion(oldX, oldY).getForce()-5) return getInfluenceMap(x, y);
			else return iaMap.getPion(oldX, oldY).getForce()-5;
		}
		//si il y a un pion, mais qu'on le connait.
		else if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getVisibleByIA() && iaMap.getPion(x, y).getTeam())
		{
			if(iaMap.getPion(oldX, oldY).getForce()>iaMap.getPion(x, y).getForce())
			{
				return 21;
			}
			else if(iaMap.getPion(oldX, oldY).getForce()==iaMap.getPion(x, y).getForce())
			{
				return 0;
			}
			else return -10;
		}
		return -5;
	}*/
	
	public int getInfluence(int oldX, int oldY, int x, int y)
	{
		//si la case est un blackout
		if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getName().equals("blackout"))
		{
			return iaMap.getPion(x, y).getForce()*-1;
		}
		//si le pion sur la case (x,y) m'appartient
		else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getTeam())
		{
			return -5;
		}
		//si la case sur laquelle on souhaite se deplacer est vide, l'influence est egal à la force du pion qui s'y déplace
		else if(iaMap.getPion(x, y)==null)
		{
			return iaMap.getPion(oldX, oldY).getForce();
		}
		//si il y a un pion, mais qu'on ne le connait pas.
		else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getVisibleByIA() )
		{
			//je privilegie l'exploration avec les eclaireurs;
			if(iaMap.getPion(oldX, oldY).getName().equals("eclaireur"))
			{
				return 20;
			}
			else
			{
				return iaMap.getPion(oldX, oldY).getForce()-5;
			}
		}
		//si il y a un pion, mais qu'on le connait.
		else if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getVisibleByIA())
		{
			if(iaMap.getPion(oldX, oldY).getForce()>iaMap.getPion(x, y).getForce())
			{
				return 21;
			}
			else if(iaMap.getPion(oldX, oldY).getForce()==iaMap.getPion(x, y).getForce())
			{
				return 0;
			}
			else return -10;
		}
		else return -5;
	}

	private void updateInfluenceMap()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(iaMap.getPion(i, j)!=null && !iaMap.getPion(i, j).getTeam())
				{
					for(int u=0; u<=iaMap.getPion(i, j).getNbrDePas(); u++)
					{
						
						//a gauche
						if(0<=j-u)
						{
							setInfluence(i, j-u, getInfluence(i, j, i, j-u));
						}
						//en haut
						if(0<=i-u)
						{
							setInfluence(i-u, j, getInfluence(i, j, i-u, j));
						}
						//a droite
						if(j+u<10)
						{
							setInfluence(i, j+u, getInfluence(i, j, i, j+u));
						}
						//en bas
						if(i+u<10)
						{
							setInfluence(i+u, j, getInfluence(i, j, i+u, j));
						}
					}
				}
			}
		}
	}
	
	private void updateListOfDisplacement()
	{
		listOfDisplacement = new ArrayList<Deplacement>();
		Deplacement depl;
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(iaMap.getPion(i, j)!=null && !iaMap.getPion(i, j).getTeam())
				{
					for(int u=1; u<=iaMap.getPion(i, j).getNbrDePas(); u++)
					{
						if( (i-u>=0) && game.canMoveOnNewCase(i, j, i-u, j, false))
						{
							depl = new Deplacement(i, j, i-u, j);
							depl.setInfluence(getInfluence(i, j, i-u, j));
							listOfDisplacement.add(depl);
						}
						if( (i+u<10) && game.canMoveOnNewCase(i, j, i+u, j, false))
						{
							depl = new Deplacement(i, j, i+u, j);
							depl.setInfluence(getInfluence(i, j, i+u, j));
							listOfDisplacement.add(depl);
						}
						if( (j-u>=0) && game.canMoveOnNewCase(i, j, i, j-u, false))
						{
							depl = new Deplacement(i, j, i, j-u);
							depl.setInfluence(getInfluence(i, j, i, j-u));
							listOfDisplacement.add(depl);
						}
						if( (j+u<10) && game.canMoveOnNewCase(i, j, i, j+u, false))
						{
							depl = new Deplacement(i, j, i, j+u);
							depl.setInfluence(getInfluence(i, j, i, j+u));
							listOfDisplacement.add(depl);
						}
					}
				}
			}
		}
	}
	
	private ArrayList<Deplacement> getBestDisplacement()
	{
		ArrayList<Deplacement> bestDisplacement = new ArrayList<Deplacement>();
		for(Deplacement i : listOfDisplacement)
		{
			if(bestDisplacement.size()==0)
			{
				bestDisplacement.add(i);
			}
			else if(bestDisplacement.get(0).getInfluence()<i.getInfluence())
			{
				bestDisplacement.removeAll(bestDisplacement);
				bestDisplacement.add(i);
			}
			else if(bestDisplacement.get(0).getInfluence()==i.getInfluence())
			{
				bestDisplacement.add(i);
			}
		}
		return bestDisplacement;
	}
	
	private void doDisplacement(Deplacement depl)
	{
		System.out.println("Influence = "+depl.getInfluence()+", et x et y = "+depl.getX()+" "+depl.getY());
		controller.setClick((depl.getOldX()*10)+depl.getOldY());
		controller.setPrise(iaMap.getPion(depl.getOldX(), depl.getOldY()).getPath());
		controller.setClick((depl.getX()*10)+depl.getY());
		controller.setPrise(null);
		
		
		/*int resultFight = game.checkNewCase(depl.getOldX(), depl.getOldY(), depl.getX(), depl.getY());
		
		switch(resultFight)
		{
		case 10:game.placePion(depl.getOldX(), depl.getOldY(), depl.getX(), depl.getY());
				break;
		case 0: game.removePion(depl.getOldX(), depl.getOldY());
				game.removePion(depl.getX(), depl.getY());
				//check si après l'élimination des deux pions, les deux joueurs savent encore jouer.
				if(!controller.getPartieFinie() && game.checkLost(!controller.getTour()) && game.checkLost(controller.getTour()))
				{
					controller.setPartieFinie(true);
				}
				if(!controller.getPartieFinie() && game.checkLost(!controller.getTour()))
				{
					controller.setPartieFinie(true);
					controller.setGagnant(controller.getTour()+"");
				}
				if(!controller.getPartieFinie() && game.checkLost(controller.getTour()))
				{
					controller.setPartieFinie(true);
					controller.setGagnant(!controller.getTour()+"");
				}
				break;
		case 1: game.removePion(depl.getOldX(), depl.getOldY());
				game.getMap().getPion(depl.getX(), depl.getY()).setVisibleByIA(true);
				//check si après la perte du pion, le joueur sait encore jouer.
				if(!controller.getPartieFinie() && game.checkLost(controller.getTour()))
				{
					controller.setPartieFinie(true);
					controller.setGagnant(!controller.getTour()+"");
				}
				break;
		case 2: if(game.getMap().getPion(depl.getX(), depl.getY()).getName().equals("drapeau")) controller.setPartieFinie(true);;
				game.removePion(depl.getX(), depl.getY());
				game.getMap().getPion(depl.getOldX(), depl.getOldY()).setVisibleByIA(true);
				game.placePion(depl.getOldX(), depl.getOldY(), depl.getX(), depl.getY());
				//check si après le déplacement du pion d'un joueur, l'autre joueur sait encore jouer.
				if(!controller.getPartieFinie() && game.checkLost(!controller.getTour()))
				{
					controller.setPartieFinie(true);
					controller.setGagnant(controller.getTour()+"");
				}
				break;
		}*/
	}

	

	public void play() 
	{
		// TODO Auto-generated method stub
		updateListOfDisplacement();
		printList();
		ArrayList<Deplacement> bestDeplacement = getBestDisplacement();
		if(bestDeplacement.size()==1)
		{
			doDisplacement(bestDeplacement.get(0));
		}
		else
		{
			int t; Random r = new Random();
			t = r.nextInt(bestDeplacement.size());
			doDisplacement(bestDeplacement.get(t));
		}
	}
	
	
}