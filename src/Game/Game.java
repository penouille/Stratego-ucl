package Game;

import Pion.*;

/**
 * Classe représentant l'état d'une partie à un moment donné.
 * 
 * @author Florian
 *
 */
public class Game 
{
	
	private Map Map;
	
	public Game()
	{
		Map = new Map();
	}
	
	public Map getMap()
	{
		return Map;
	}
	//implémentation d'un mouvement
	public void move ( int x1, int y1, int x2, int y2)
	{
		Pion attaquant = Map.getPion(x1,y1);
		Pion defenseur = Map.getPion(x2, y2);
		
		Map.resetPosition(x1,y1);
		
		if(defenseur == null)
		{
			Map.setEtat(x2, y2, attaquant);
			return;
		}
		
		switch( fight (attaquant, defenseur) )
		{
		
		case(0): 
			Map.setEtat(x2, y2, null);
		
		
		case(1):
			Map.setEtat(x2, y2, defenseur);
		
		
		case(2):
			Map.setEtat(x2, y2, attaquant);
		}
	}
	
	public boolean canMove(int x, int y, boolean joueur)
	{
		boolean canMove = false;
		for(int i=1; !canMove && i<=Map.getPion(x, y).getNbrDePas(); i++)
		{
			if( (x-i>=0) && canMoveOnNewCase(x, y, x-i, y, joueur)) canMove=true;
			else if( (x+i<10) && canMoveOnNewCase(x, y, x+i, y, joueur)) canMove=true;
			else if( (y-i>=0) && canMoveOnNewCase(x, y, x, y-i, joueur)) canMove=true;
			else if( (y+i<10) && canMoveOnNewCase(x, y, x, y+i, joueur)) canMove=true;
		}

		return canMove;
	}
	
	/**
	 * @param oldX, oldY, x, y, joueur
	 * @return 	renvoit true si le pion de la case (oldX,oldY) peut être déplacer sur la case (x,y)
	 * 			Attention: 	Si la case sur laquelle on souhaite déplacer son pion est occupée par un 
	 * 						pion adverse, cette methode renvoit true.
	 */
	public boolean canMoveOnNewCase(int oldX, int oldY, int x, int y, boolean joueur)
	{
		Pion attaquant = Map.getPion(oldX,oldY);
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
			Pion defenseur = Map.getPion(x, y);
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
	
	//implémentation d'un combat entre pions sur la carte.
	//return 0, 1 ou 2 en fct du résultat du combat.
	public int fight(Pion P1 , Pion P2)
	{
		return Fight.fightResult[P1.getForce()][P2.getForce()];
	}
	
	public int sizeMap()
	{
		return Map.getSize();
	}
	
	public Pion getPosition( int x , int y)
	{
		return Map.getPion( x , y);
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
				if(Map.getPion(i, j)!=null && Map.getPion(i, j).getTeam()==joueur)
				{
					if(canMove(i, j, joueur)) lost=false;
				}
			}
		}
		return lost;
	}
	
	public void removePion(int x, int y) 
	{
		Map.resetPosition(x, y);
	}
	
	public void placePion(int oldX, int oldY, int x, int y) 
	{
		Map.setEtat(x, y, Map.getPion(oldX, oldY));
		Map.setEtat(oldX, oldY, null);
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
				if(pionPath.contains(Map.getPion(i, j).getName()))
				{
					count++;
					if(nbrPion==0) nbrPion = Map.getPion(i, j).getNombre();
				}
			}
		}
		return nbrPion!=count;
	}

	public void createAndPlacePion(String pionPath, int x, int y, boolean joueur) 
	{
		Pion pion = getTypePion(pionPath, joueur);
		Map.setEtat(x, y, pion);
	}

	/**
	 * @param oldX, oldY, x, y
	 * @return renvoit le resultat d'un eventuel combat lors du deplacement d'un pion
	 */
	public int checkNewCase(int oldX, int oldY, int x, int y)
	{
		Pion attaquant = Map.getPion(oldX,oldY);
		Pion defenseur = Map.getPion(x, y);
		//Si la case sur laquelle on veut placer le pion est vide.
		if(defenseur==null) return 10;
		else return fight(attaquant, defenseur);
	}
}
