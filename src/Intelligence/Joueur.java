package Intelligence;

import java.util.ArrayList;

import Pion.Pion;

public class Joueur 
{
	private String pseudo;
	private boolean team;
	private String prefColor;
	private String prefDiff;
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
		setTeam(joueur);
		setPrefDiff("Intermediaire");
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

	public boolean isTeam() {
		return team;
	}

	public void setTeam(boolean team) {
		this.team = team;
	}
}
