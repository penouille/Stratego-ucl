package Game;
import Pion.*;

import java.util.ArrayList;

/**
 * Classe représentant une carte virtuelle du jeu stratégo.
 * 
 * @author Florian
 *
 */
public class Map 
{
	private Pion[][] Map;
	
	public Map()
	{
		Map = new Pion[10][10];
		initialise();
		
	}
	
	/*
	 * pré:-
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
	 * retourne l'objet position dont les coordonnées ont été passées en argument.
	 */
	public Pion getPion (int x, int y)
	{
		return Map[x][y]; 
	}
	
	public void resetPosition (int x , int y)
	{
		Map[x][y] = null;
	}
	/**
	 * post: crée des "trou" dans la carte du jeu.
	 */
	public void setBlackout()
	{
		Blackout blackout = new Blackout();
		
		setEtat(4,2,blackout);
		setEtat(4,3,blackout);
		setEtat(5,2,blackout);
		setEtat(5,3,blackout);
		
		setEtat(4,6,blackout);
		setEtat(4,7,blackout);
		setEtat(5,6,blackout);
		setEtat(5,7,blackout);
	}
	
	public void setEtat(int x, int y, Pion pion)
	{
		Map[x][y] = pion;
	}
	
	public int getSize()
	{
		return Map.length;
	}
}
