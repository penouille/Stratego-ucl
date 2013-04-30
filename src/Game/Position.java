package Game;
import Pion.*;

/**
 * Classe représentant l'état d'une position sur la map.
 * 
 * @author Florian
 *
 */
public class Position 
{
	private int X;
	private int Y;
	private Pion Etat;
	
	public Position( int x, int y, Pion pion)
	{
		X = x;
		Y = y;
		Etat = pion;
	}
	
	/*
	 * Accesseurs.
	 */
	public int getX()
	{
		return X;
	}
	
	public int getY()
	{
		return Y;
	}
	
	public void setX(int x)
	{
		X=x;
	}
	
	public void setY(int y)
	{
		Y=y;
	}
	
	public Pion getEtat()
	{
		return Etat;
	}
	
	public void setEtat(Pion pion)
	{
		Etat = pion;
	}
	
	/*
	 * return true si les coordonnées correspondent à cette position.
	 */
	public boolean find( int x , int y )
	{
		return (this.getX() == x && this.getY() == y);
	}
}
