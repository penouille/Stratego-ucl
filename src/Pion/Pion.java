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
}
