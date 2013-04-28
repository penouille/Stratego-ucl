package Intelligence;

import java.util.ArrayList;

import Pion.Pion;

public class Joueur 
{
	private String prefColor;
	private String prefDiff;
	private ArrayList<Pion> listPionDead;
	
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
		setListPionDead(new ArrayList<Pion>());
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

	public ArrayList<Pion> getListPionDead()
	{
		return listPionDead;
	}

	public void setListPionDead(ArrayList<Pion> listPionDead)
	{
		this.listPionDead = listPionDead;
	}
}
