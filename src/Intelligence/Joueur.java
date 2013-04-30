package Intelligence;

import java.util.ArrayList;

import Pion.Pion;

public class Joueur 
{
	private String pseudo;
	private String prefColor;
	private String prefDiff;
	private ArrayList<Pion> listPionDead;
	private int score;
	
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

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
