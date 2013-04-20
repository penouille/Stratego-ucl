package Intelligence;

public class Joueur 
{
	private String prefColor;
	private String prefDiff;
	
	public Joueur(boolean joueur)
	{
		if(joueur)
		{
			setPrefColor("Rouge");
		}
		else
		{
			setPrefColor("Bleu");
		}
		setPrefDiff("Normal");
	}

	public String getPrefColor()
	{
		return prefColor;
	}

	public void setPrefColor(String prefColor)
	{
		this.prefColor = prefColor;
	}

	public String getPrefDiff()
	{
		return prefDiff;
	}

	public void setPrefDiff(String prefDiff)
	{
		this.prefDiff = prefDiff;
	}
}
