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
	
	//implémentation d'un mouvement
	public void move ( int x1, int y1, int x2, int y2)
	{
		Pion attaquant = map.getPion(x1,y1);
		Pion defenseur = map.getPion(x2, y2);
		
		map.resetPosition(x1,y1);
		
		if(defenseur == null)
		{
			map.setEtat(x2, y2, attaquant);
			return;
		}
		
		switch( fight(attaquant, defenseur) )
		{
		
		case(0): 
			map.setEtat(x2, y2, null);
		
		
		case(1):
			map.setEtat(x2, y2, defenseur);
		
		
		case(2):
			map.setEtat(x2, y2, attaquant);
		}
	}
	
	public boolean canMove(int oldX, int oldY, int x, int y, boolean joueur)
	{
		Pion attaquant = map.getPion(oldX,oldY);
		if(attaquant==null)
		{
			return false;
		}
		else if(attaquant.getTeam()!=joueur)//Si on tente de déplacer un joueur qui ne nous appartient pas.
		{
			return false;
		}
		else
		{
			Pion defenseur = map.getPion(x, y);
			if (defenseur.getForce()==1000)//Déplace un joueur sur une case interdite.
			{
				return false;
			}
			if (defenseur.getTeam() == attaquant.getTeam())
			{
				return false;
			}
			if(defenseur==null)
			{
				byte nbrPas = attaquant.getNbrDePas();
				if(oldX-x<=nbrPas || oldX-x<=nbrPas*(-1) || oldY-y<=nbrPas || oldY-y<=nbrPas*(-1))
				{
					return true;
				}
			}
			else
			{
				//TODO Affronter un pion adverse.
				return false;
			}
		}
		
		
		
		
		
		//A rajouter: Le cas où un joueur essaie de jouer un pion adverse.
		
		return true;
		
	}
	
	//implémentation d'un combat entre pions sur la carte.
	//return 0, 1 ou 2 en fct du résultat du combat.
	public int fight(Pion P1 , Pion P2)
	{
		return Fight.fightResult[P1.getForce()][P2.getForce()];
	}

	public void placePion(int oldX, int oldY, int x, int y) 
	{
		// TODO Auto-generated method stub
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

	public boolean checkNumberOfPion(String pionPath) 
	{
		int count = 0; int nbrPion = 0;
		for(int i = 0; i<4; i++)
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

	public void createAndPlacePion(String pionPath, int x, int y, boolean joueur) {
		Pion pion = getTypePion(pionPath, joueur);
		map.setEtat(x, y, pion);
	}
}
