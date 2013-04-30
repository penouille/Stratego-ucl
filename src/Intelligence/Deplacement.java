package Intelligence;

public class Deplacement
{
	private int oldX;
	private int oldY;
	private int x;
	private int y;
	private int influence;
	
	public Deplacement(int oldX, int oldY, int x, int y)
	{
		this.setOldX(oldX);
		this.setOldY(oldY);
		this.setX(x);
		this.setY(y);
	}
	
	public Deplacement(int oldX, int oldY, int x, int y, int influence)
	{
		this.setOldX(oldX);
		this.setOldY(oldY);
		this.setX(x);
		this.setY(y);
		this.setInfluence(influence);
	}

	public void setInfluence(int influence)
	{
		this.influence = influence;
	}
	
	public int getInfluence()
	{
		return this.influence;
	}

	public int getOldX()
	{
		return this.oldX;
	}

	public void setOldX(int oldX)
	{
		this.oldX = oldX;
	}

	public int getOldY()
	{
		return this.oldY;
	}

	public void setOldY(int oldY)
	{
		this.oldY = oldY;
	}

	public int getX()
	{
		return this.x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
