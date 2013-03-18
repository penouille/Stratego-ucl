package Game;

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
 * Classe repr�sentant l'�tat d'une partie � un moment donn�.
 * 
 * @author Florian
 *
 */
public class Game
{
	
	private Map map;
	
	public Game()
	{
		map = new Map();
	}
	
	public int sizeMap()
	{
		return map.getSize();
	}
	
	public Map getMap() 
	{
		return this.map;
	}
	
	/**
	 * @param oldX, oldY, x, y, joueur
	 * @return 	renvoit true si le pion de la case (oldX,oldY) peut �tre d�placer sur la case (x,y)
	 * 			Attention: 	Si la case sur laquelle on souhaite d�placer son pion est occup�e par un 
	 * 						pion adverse, cette methode renvoit true.
	 */
	public boolean canMoveOnNewCase(int oldX, int oldY, int x, int y, boolean joueur)
	{
		Pion attaquant = map.getPion(oldX,oldY);
		if(attaquant==null)	//verification "inutile", mais on est jamais trop prudent.
		{
			return false;
		}
		//Si on tente de d�placer un pion qui ne nous appartient pas.
		else if(attaquant.getTeam()!=joueur)
		{
			return false;
		}
		else
		{
			byte nbrPas = attaquant.getNbrDePas();
			//Soit un d�placement vertical (donc les x)
			if(((0<=oldX-x && oldX-x<=nbrPas) || (0<= oldX-x*(-1) && oldX-x*(-1)<=nbrPas)) 
					//Soit un d�placement horizontale (donc les y)
					|| ((0<=oldY-y && oldY-y<=nbrPas) || (0<=oldY-y*(-1) && oldY-y*(-1)<=nbrPas)))
			{
				Pion defenseur = map.getPion(x, y);
				if(defenseur==null)
				{
					return true;
				}
				//deplacer un pion sur un pion adverse.
				else if(defenseur.getTeam() != attaquant.getTeam())
				{
					return true;
				}
				//tenter de deplacer un pion sur un autre pion qui nous appartient, ou sur une case interdite.
				else
				{
					return false;
				}
			}
			else return false;
		}
	}
	
	/**
	 * @param x, y, joueur
	 * @return 	renvoit true si le pion � la position (x,y) peut etre deplacer sur une autre case 
	 * 			(gauche, droite, haut, ou bas), false sinon.
	 */
	public boolean canMove(int x, int y, boolean joueur)
	{
		boolean canMove = false;
		for(int i=1; !canMove && i<=map.getPion(x, y).getNbrDePas(); i++)
		{
			if( (x-i>=0) && canMoveOnNewCase(x, y, x-i, y, joueur)) canMove=true;
			else if( (x+i<10) && canMoveOnNewCase(x, y, x+i, y, joueur)) canMove=true;
			else if( (y-i>=0) && canMoveOnNewCase(x, y, x, y-i, joueur)) canMove=true;
			else if( (y+i<10) && canMoveOnNewCase(x, y, x, y+i, joueur)) canMove=true;
		}
		return canMove;
	}
	
	/**
	 * @param Pion1 et Pion2
	 * @return impl�mentation d'un combat entre pions sur la carte.
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
		if(pionPath.contains("bombe")) return new Bombe(joueur);
		if(pionPath.contains("drapeau")) return new Drapeau(joueur);
		if(pionPath.contains("espion")) return new Espion(joueur);
		if(pionPath.contains("colonel")) return new Colonel(joueur);
		if(pionPath.contains("capitaine")) return new Capitaine(joueur);
		if(pionPath.contains("commandant")) return new Commandant(joueur);
		if(pionPath.contains("demineur")) return new Demineur(joueur);
		if(pionPath.contains("eclaireur")) return new Eclaireur(joueur);
		if(pionPath.contains("espion")) return new Espion(joueur);
		if(pionPath.contains("general")) return new General(joueur);
		if(pionPath.contains("lieutenant")) return new Lieutenant(joueur);
		if(pionPath.contains("marechal")) return new Marechal(joueur);
		if(pionPath.contains("sergent")) return new Sergent(joueur);
		else return null;
	}

	/**
	 * 
	 * @param pionPath
	 * @return renvoit true si on peut placer le pion desirer, en fonction de si on en as encore � placer.
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
	 * @return 	renvoit true si le joueur a perdu parce qu'il ne sait plus d�placer un seul pion
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
	 * @return 	Regarde si le joueur a bien plac� tous ses pions.
	 * 			Renvoit true si il a plac� tous ses pions, false sinon.
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
}
