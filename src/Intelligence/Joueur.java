package Intelligence;

public class Joueur 
{
	private String prefColor;
	
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
	}

	public String getPrefColor()
	{
		return prefColor;
	}

	public void setPrefColor(String prefColor)
	{
		this.prefColor = prefColor;
	}
}
