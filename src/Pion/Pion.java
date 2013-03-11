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
	protected boolean teamRed;
	protected String name;
	//protected boolean mort;
	
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
	
	public void setTeamRed(boolean b)
	{
		this.teamRed = b;
	}
	
	public boolean getTeamRed()
	{
		return this.teamRed;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	/*public void setMort(boolean b)
	{
		mort = b;
	}
	
	public boolean getMort()
	{
		return this.mort;
	}*/
}
