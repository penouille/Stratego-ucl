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
 * Classe représentant l'état d'une partie à un moment donné.
 * 
 * @author Florian
 *
 */
public class Game extends AbstractGame
{
	
	private Map map;
	
	public Game(String typeGame)
	{
		map = new Map();
	}
	
	public Map getMap()
	{
		return this.map;
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
		if(attaquant==null)
		{
			return false;
		}
		//Si on tente de déplacer un pion qui ne nous appartient pas.
		else if(attaquant.getTeam()!=joueur)
		{
			return false;
		}
		else
		{
			Pion defenseur = map.getPion(x, y);
			//tenter de déplacer un pion sur une case interdite.
			if (defenseur.getForce()==1000)
			{
				return false;
			}
			//tenter de deplacer un pion sur un autre pion qui nous appartient.
			else if (defenseur.getTeam() == attaquant.getTeam())
			{
				return false;
			}
			else
			{
				byte nbrPas = attaquant.getNbrDePas();
				if((oldX-x<=nbrPas || oldX-x<=nbrPas*(-1)) || (oldY-y<=nbrPas || oldY-y<=nbrPas*(-1)))
				{
					return true;
				}
				else return false;
			}
		}		
	}
	
	/**
	 * 
	 * @param x, y, joueur
	 * @return renvoit true si le pion à la position (x,y) peut etre deplacer sur une autre case,
	 * false sinon.
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
	 * @return renvoit true si on peut placer le pion desirer, en fonction de si on en as encore à placer.
	 */
	public boolean checkNumberOfPion(String pionPath) 
	{
		int count = 0; int nbrPion = 0;
		for(int i = 6; i<10; i++)
		{
			for(int j = 0; j<10; j++)
			{
				if(pionPath.contains(map.getPion(i, j).getName()))
				{
					count++;
					if(nbrPion==0) nbrPion = map.getPion(i, j).getNombre();
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
	 * @return renvoit le resultat d'un eventuel combat lors du deplacement d'un pion
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
}
