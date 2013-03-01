package Game;
import Pion.*;

/**
 * Classe repr�sentant l'�tat d'une position sur la map.
 * 
 * @author Florian
 *
 */
public class Position 
{
	private int X;
	private int Y;
	private pion Etat;
	
	public Position( int x, int y, pion pion)
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
	
	public pion getEtat()
	{
		return Etat;
	}
	
	public void setEtat(pion pion)
	{
		Etat = pion;
	}
	
	/*
	 * return true si les coordonn�es correspondent � cette position.
	 */
	public boolean find( int x , int y )
	{
		return (this.getX() == x && this.getY() == y);
	}
}
