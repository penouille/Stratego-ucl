package Game;

import Intelligence.Artificielle;
import Intelligence.Joueur;
import Pion.Fight;
import Pion.Pion;


/**
 * Classe représentant l'état d'une partie à un moment donné.
 * 
 * @author Florian
 *
 */
public class Game extends AbstractGame
{
	
	private Map map;
	
	//private Joueur joueur1;
	
	//private Joueur joueur2;
	
	//private Artificielle IA;
	
	public Game(String typeGame)
	{
		map = new Map();
		
		/*if(typeGame.equals("JvJ"))
		{
			joueur1 = new Joueur();
			joueur2 = new Joueur();
		}
		else
		{
			joueur1 = new Joueur();
			IA = new Artificielle();
		}*/
	}
	
	//implémentation d'un mouvement
	public void move ( int x1, int y1, int x2, int y2)
	{
		Pion attaquant = map.getPosition(x1,y1);
		Pion defenseur = map.getPosition(x2, y2);
		
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
	
	public boolean canMove(int x1, int y1, int x2, int y2 )
	{
		Pion attaquant = map.getPosition(x1,y1);
		Pion defenseur = map.getPosition(x2, y2);
		
		if (attaquant == null)
		{
			return false;
		}
		
		if (defenseur.getForce()==1000)
		{
			return false;
		}
		
		if (defenseur.getTeamRed() == attaquant.getTeamRed())
		{
			return false;
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

	public void placePion(String pionPath, int x, int y) {
		// TODO Auto-generated method stub
		//Pion pion = joueur1.getPion(pionPath);
		//map.setEtat(x, y, pion);
	}
}
