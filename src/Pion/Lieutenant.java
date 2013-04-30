package Pion;

import Intelligence.Joueur;

public class Lieutenant extends Pion
{
	public Lieutenant(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 5;
		name = "lieutenant";
		nombre = 4;
		team = teamm;
		path = "lieutenant.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
