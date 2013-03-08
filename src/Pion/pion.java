package Pion;

/**
 * 
 * @author Florian Faingnaert et Q.M.
 * 
 *
 *
 */
public abstract class pion 
{
	protected int NbrDePas;
	protected int Force;
	protected int Team;
	
	public pion()
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
