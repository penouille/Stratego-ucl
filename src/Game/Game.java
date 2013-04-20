package Game;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Intelligence.Joueur;
import Pion.Bombe;
import Pion.Capitaine;
import Pion.Colonel;
import Pion.Commandant;
import Pion.Demineur;
import Pion.Drapeau;
import Pion.Eclaireur;
import Pion.Espion;
import Pion.Fight;
import Pion.General;
import Pion.Lieutenant;
import Pion.Marechal;
import Pion.Pion;
import Pion.Sergent;


/**
 * Classe représentant l'état d'une partie à un moment donné.
 * 
 * @author Florian
 *
 */
public class Game
{
	
	private Map map;
	
	private Joueur J1;
	private Joueur J2;
	
	public Game()
	{
		map = new Map();
		J1 = new Joueur(true);
		J2 = new Joueur(false);
	}
	
	public Joueur getJ1()
	{
		return this.J1;
	}
	public Joueur getJ2()
	{
		return this.J2;
	}
	
	public int sizeMap()
	{
		return map.getSize();
	}
	
	public Map getMap() 
	{
		return this.map;
	}
	
	public boolean logicalXOR(boolean x, boolean y) 
	{
	    return ( ( x || y ) && ! ( x && y ) );
	}
	
	/**
	 * @param oldX, oldY, x, y, nbrPas
	 * @return Cette methode renvoit true si il y a un obstacle sur le trajet, et false sinon.
	 */
	public boolean checkObstacleOnWay(int oldX, int oldY, int x, int y, int nbrPas)
	{
		//si le pion descend.
		if(x>oldX)
		{
			for(int i=oldX+1; i<x; i++)
			{
				if(map.getPion(i, y)!=null) return true;
			}
			return false;
		}
		//si le pion monte
		else if(oldX>x)
		{
			for(int i=x+1; i<oldX; i++)
			{
				if(map.getPion(i, y)!=null) return true;
			}
			return false;
		}
		//si le pion va à gauche
		else if(y<oldY)
		{
			for(int i=y+1; i<oldY; i++)
			{
				if(map.getPion(x, i)!=null) return true;
			}
			return false;
		}
		//si le pion va à droite
		else if(oldY<y)
		{
			for(int i=oldY+1; i<y; i++)
			{
				if(map.getPion(x, i)!=null) return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * @param oldX, oldY, x, y, joueur
	 * @return 	renvoit true si le pion de la case (oldX,oldY) peut être déplacer sur la case (x,y)
	 * 			Attention: 	Si la case sur laquelle on souhaite déplacer son pion est occupée par un 
	 * 						pion adverse, cette methode renvoit true.
	 */
	public boolean canMoveOnNewCase(int oldX, int oldY, int x, int y, boolean joueur)
	{
		Pion attaquant = map.getPion(oldX,oldY);
		if(attaquant==null)	//verification "inutile", mais on est jamais trop prudent.
		{
			//System.out.println("attaquant = "+attaquant);
			return false;
		}
		//Si on tente de déplacer un pion qui ne nous appartient pas.
		else if(attaquant.getTeam()!=joueur)
		{
			//System.out.println("ce n'est pas mon pion");
			return false;
		}
		else
		{
			byte nbrPas = attaquant.getNbrDePas();
			
			/*System.out.println("oldX = "+oldX+" et x = "+x);
			System.out.println("oldY = "+oldY+" et y = "+y);
			System.out.println("Nombre de pas = "+nbrPas);
			System.out.println("(0<oldX-x && oldX-x<=nbrPas) = "+((0<oldX-x && oldX-x<=nbrPas)));
			System.out.println("(0< x-oldX && x-oldX<=nbrPas) = "+((0< x-oldX && x-oldX<=nbrPas)));
			System.out.println("(0<oldY-y && oldY-y<=nbrPas) = "+((0<oldY-y && oldY-y<=nbrPas)));
			System.out.println("(0<y-oldY && y-oldY<=nbrPas) = "+((0<y-oldY && y-oldY<=nbrPas)));
			
			System.out.println("xor = "+(logicalXOR(((0<oldX-x && oldX-x<=nbrPas) || (0<x-oldX && x-oldX<=nbrPas)),
					((0<oldY-y && oldY-y<=nbrPas) || (0<y-oldY && y-oldY<=nbrPas)))));*/
			
							//Soit un déplacement verticale (donc les x)
			if(logicalXOR(((0<oldX-x && oldX-x<=nbrPas) || (0<x-oldX && x-oldX<=nbrPas)), 
							//Soit un déplacement horizontale (donc les y)
					((0<oldY-y && oldY-y<=nbrPas) || (0<y-oldY && y-oldY<=nbrPas))))
			{
				if(oldX!=x && oldY!=y)
				{
					//System.out.println("oldX!=x et oldY!=y");
					return false;
				}
				//Verifie s'il il n'y as pas de pion sur le trajet, lorsque la distance parcourue est différente de 1.
				if(nbrPas!=1 && checkObstacleOnWay(oldX, oldY, x, y, nbrPas))
				{
					//System.out.println("il y a des obstacles");
					return false;
				}
				Pion defenseur = map.getPion(x, y);
				if(defenseur==null)
				{
					//System.out.println("case vide");
					return true;
				}
				//deplacer un pion sur un pion adverse.
				else if(defenseur.getTeam() != attaquant.getTeam())
				{
					//System.out.println("Il y a un enemi");
					return true;
				}
				//tenter de deplacer un pion sur un autre pion qui nous appartient, ou sur une case interdite.
				else
				{
					//System.out.println("c'est autre chose");
					return false;
				}
			}
			else return false;
		}
	}
	
	/**
	 * @param x, y, joueur
	 * @return 	renvoit true si le pion à la position (x,y) peut etre deplacer sur une autre case 
	 * 			(gauche, droite, haut, ou bas), false sinon.
	 */
	public boolean canMove(int x, int y, boolean joueur)
	{
		for(int i=1; i<=map.getPion(x, y).getNbrDePas(); i++)
		{
			if( (x-i>=0) && canMoveOnNewCase(x, y, x-i, y, joueur)) return true;
			else if( (x+i<10) && canMoveOnNewCase(x, y, x+i, y, joueur)) return true;
			else if( (y-i>=0) && canMoveOnNewCase(x, y, x, y-i, joueur)) return true;
			else if( (y+i<10) && canMoveOnNewCase(x, y, x, y+i, joueur)) return true;
		}
		return false;
	}
	
	public ArrayList<Integer> whichCaseCanGo(int x, int y, boolean joueur)
	{
		ArrayList<Integer> listCase = new ArrayList<Integer>();
		for(int i=1; i<=map.getPion(x, y).getNbrDePas(); i++)
		{
			if( (x-i>=0) && canMoveOnNewCase(x, y, x-i, y, joueur)) listCase.add(((x-i)*10)+y);
			if( (x+i<10) && canMoveOnNewCase(x, y, x+i, y, joueur)) listCase.add(((x+i)*10)+y);
			if( (y-i>=0) && canMoveOnNewCase(x, y, x, y-i, joueur)) listCase.add((x*10)+y-i);
			if( (y+i<10) && canMoveOnNewCase(x, y, x, y+i, joueur)) listCase.add((x*10)+y-i);
		}
		return listCase;
	}
	
	/**
	 * @param Pion1 et Pion2
	 * @return implémentation d'un combat entre pions sur la carte.
	 * 			renvoit 0 si les deux pions meurt
	 * 			renvoit 1 si le pion attaquant meurt
	 * 			ou renvoit 2 si le pion defenseur meurt.
	 */
	public int fight(Pion P1 , Pion P2)
	{
		return Fight.fightResult[P1.getForce()][P2.getForce()];
	}

	public void placePion(int oldX, int oldY, int x, int y) 
	{
		map.setEtat(x, y, map.getPion(oldX, oldY));
		map.setEtat(oldX, oldY, null);
	}

	private Pion getTypePion(String pionPath, boolean joueur)
	{
		Joueur player;
		if(joueur) player = J1;
		else player = J2;
		if(pionPath.contains("bombe")) return new Bombe(joueur, player);
		if(pionPath.contains("drapeau")) return new Drapeau(joueur, player);
		if(pionPath.contains("espion")) return new Espion(joueur, player);
		if(pionPath.contains("colonel")) return new Colonel(joueur, player);
		if(pionPath.contains("capitaine")) return new Capitaine(joueur, player);
		if(pionPath.contains("commandant")) return new Commandant(joueur, player);
		if(pionPath.contains("demineur")) return new Demineur(joueur, player);
		if(pionPath.contains("eclaireur")) return new Eclaireur(joueur, player);
		if(pionPath.contains("espion")) return new Espion(joueur, player);
		if(pionPath.contains("general")) return new General(joueur, player);
		if(pionPath.contains("lieutenant")) return new Lieutenant(joueur, player);
		if(pionPath.contains("marechal")) return new Marechal(joueur, player);
		if(pionPath.contains("sergent")) return new Sergent(joueur, player);
		else return null;
	}

	/**
	 * 
	 * @param pionPath
	 * @return renvoit true si on peut placer le pion desirer, en fonction de si on en as encore à placer.
	 */
	public boolean checkNumberOfPion(String pionPath, int min, int max) 
	{
		int count = 0; int nbrPion = -1;
		for(int i = min; i<=max; i++)
		{
			for(int j = 0; j<10; j++)
			{
				if(map.getPion(i, j)!=null && pionPath.contains(map.getPion(i, j).getName()))
				{
					count++;
					if(nbrPion==-1) nbrPion = map.getPion(i, j).getNombre();
				}
			}
		}
		return nbrPion!=count;
	}

	public void createAndPlacePion(String pionPath, int x, int y, boolean joueur) 
	{
		Pion pion = getTypePion(pionPath, joueur);
		map.setEtat(x, y, pion);
	}

	/**
	 * @param oldX, oldY, x, y
	 * @return 	renvoit le resultat d'un eventuel combat lors du deplacement d'un pion
	 * 			renvoit 10 si la case de destination etait inocupee.
	 *			renvoit 0 si les deux pions meurt 
	 * 			renvoit 1 si le pion attaquant meurt
	 * 			ou renvoit 2 si le pion defenseur meurt.
	 */
	public int checkNewCase(int oldX, int oldY, int x, int y)
	{
		Pion attaquant = map.getPion(oldX,oldY);
		Pion defenseur = map.getPion(x, y);
		//Si la case sur laquelle on veut placer le pion est vide.
		if(defenseur==null) return 10;
		else return fight(attaquant, defenseur);
	}

	public void removePion(int x, int y) 
	{
		map.resetPosition(x, y);
	}
	
	/**
	 * @param joueur
	 * @return 	renvoit true si le joueur a perdu parce qu'il ne sait plus déplacer un seul pion
	 * 			false sinon.
	 */
	public boolean checkLost(boolean joueur)
	{
		boolean lost = true;
		for(int i = 0; lost && i<10; i++)
		{
			for(int j = 0; lost && j<10; j++)
			{
				if(map.getPion(i, j)!=null && map.getPion(i, j).getTeam()==joueur)
				{
					if(canMove(i, j, joueur)) lost=false;
				}
			}
		}
		return lost;
	}

	
	/**
	 * @param min, max
	 * @return 	Regarde si le joueur a bien placé tous ses pions.
	 * 			Renvoit true si il a placé tous ses pions, false sinon.
	 */
	public boolean checkHaveAllPionsPlaced(int min, int max)
	{
		for(int i=min; i<=max; i++)
		{
			for(int j=0; j<10;j++)
			{
				if(map.getPion(i, j)==null) return false;
			}
		}
		return true;
	}
	
	public void dude(boolean joueur)
	{
		Joueur player;
		if(joueur) player = J1;
		else player = J2;
		int min, max;
		if(joueur)
		{
			min=6; 
			max=10;
		}
		else
		{
			min=0; 
			max=4;
		}
		int i;
		ArrayList<Pion> ListePion = new ArrayList<Pion>();
		ListePion.add(new Drapeau(joueur, player));
		for(i=1; i<7; i++)
		{
			ListePion.add(new Bombe(joueur, player));
		}
		ListePion.add(new Espion(joueur, player));
		for(i=8; i<16; i++)
		{
			ListePion.add(new Eclaireur(joueur, player));
		}
		for(i=16; i<21; i++)
		{
			ListePion.add(new Demineur(joueur, player));
		}
		for(i=21; i<25; i++)
		{
			ListePion.add(new Sergent(joueur, player));
		}
		for(i=25; i<29; i++)
		{
			ListePion.add(new Lieutenant(joueur, player));
		}
		for(i=29; i<33; i++)
		{
			ListePion.add(new Capitaine(joueur, player));
		}
		for(i=33; i<36; i++)
		{
			ListePion.add(new Commandant(joueur, player));
		}
		for(i=36; i<38; i++)
		{
			ListePion.add(new Colonel(joueur, player));
		}
		ListePion.add(new General(joueur, player));
		ListePion.add(new Marechal(joueur, player));
		
		int t; Random r = new Random();
		for(i=min; i<max; i++)
		{
			for(int j=0;j<10;j++)
			{
				t = r.nextInt(ListePion.size());
				map.setEtat(i, j, ListePion.get(t));
				ListePion.remove(t);
;			}
		}
	}
}
