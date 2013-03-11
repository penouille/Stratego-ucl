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
	protected byte nbrDePas;
	protected int force;
	protected boolean team;
	protected String name;
	protected byte nombre;
	//protected boolean mort;
	
	public Pion()
	{
		
	}
	
	public int getForce()
	{
		return this.force;
	}
	
	public byte getNbrDePas()
	{
		return this.nbrDePas;
	}
	
	public byte getNombre()
	{
		return this.nombre;
	}
	
	public void setTeam(boolean b)
	{
		this.team = b;
	}
	
	public boolean getTeam()
	{
		return this.team;
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
