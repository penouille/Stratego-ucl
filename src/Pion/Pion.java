package Pion;

/**
 * 
 * @author Florian Faingnaert et Q.M.
 * 
 *
 *
 */
public abstract class Pion 
{
	protected int NbrDePas;
	protected int Force;
	protected int Team;
	protected boolean mort;
	
	public Pion()
	{
		
	}
	
	public int getForce()
	{
		return this.Force;
	}
	
	public int getNbrDePas()
	{
		return this.NbrDePas;
	}
	
	public int getTeam()
	{
		return this.Team;
	}
	
	public void setMort(boolean b)
	{
		mort = b;
	}
	
	public boolean getMort()
	{
		return this.mort;
	}
}
