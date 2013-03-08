package Game;
import Pion.*;
import java.util.ArrayList;

/**
 * Classe repr�sentant une carte virtuelle du jeu strat�go.
 * 
 * @author Florian
 *
 */
public class Map 
{
	private pion[][] Map;
	
	public Map()
	{
		Map = new pion[11][11];
		
		initialise();
		
	}
	
	/*
	 * pr�:-
	 * post: initialise une nouvelle map vierge.
	 */
	public void initialise()
	{
		
		for (int i = 0 ; i<10 ; i++)
		{
			for (int j = 0 ; j<10 ; j++ )
			{
				Map[i][j] = null;
			}
		}
		
		setBlackout();
		
	}
	
	/*
	 * retourne l'objet position dont les coordonn�es ont �t� pass�es en argument.
	 */
	public pion getPosition (int x, int y)
	{
		return Map[x][y]; 
	}
	
	public void resetPosition (int x , int y)
	{
		Map[x][y] = null;
	}
	/**
	 * post: cr�e des "trou" dans la carte du jeu.
	 */
	public void setBlackout()
	{
		Blackout blackout = new Blackout();
		
		setEtat(4,2,blackout);
		setEtat(4,3,blackout);
		setEtat(5,2,blackout);
		setEtat(5,3,blackout);
		
		setEtat(4,5,blackout);
		setEtat(4,6,blackout);
		setEtat(5,5,blackout);
		setEtat(5,6,blackout);
	}
	
	public void setEtat(int x, int y, pion pion)
	{
		Map[x][y] = pion;
	}
}
