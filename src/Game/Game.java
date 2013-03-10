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
	
	private Map Map;
	
	private Joueur joueur1;
	
	private Joueur joueur2;
	
	private Artificielle IA;
	
	public Game(String typeGame)
	{
		Map = new Map();
		
		if(typeGame.equals("JvJ"))
		{
			joueur1 = new Joueur();
			joueur2=new Joueur();
		}
		else
		{
			joueur1 = new Joueur();
			IA = new Artificielle();
		}
	}
	
	//implémentation d'un mouvement
	public void move ( int x1, int y1, int x2, int y2)
	{
		Pion attaquant = Map.getPosition(x1,y1);
		Pion defenseur = Map.getPosition(x2, y2);
		
		Map.resetPosition(x1,y1);
		
		if(defenseur == null)
		{
			Map.setEtat(x2, y2, attaquant);
			return;
		}
		
		switch( fight(attaquant, defenseur) )
		{
		
		case(0): 
			Map.setEtat(x2, y2, null);
		
		
		case(1):
			Map.setEtat(x2, y2, defenseur);
		
		
		case(2):
			Map.setEtat(x2, y2, attaquant);
		}
	}
	
	public boolean canMove(int x1, int y1, int x2, int y2 )
	{
		Pion attaquant = Map.getPosition(x1,y1);
		Pion defenseur = Map.getPosition(x2, y2);
		
		if (attaquant == null)
		{
			return false;
		}
		
		if (defenseur.getForce()==1000)
		{
			return false;
		}
		
		if (defenseur.getTeam() == attaquant.getTeam())
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
}
