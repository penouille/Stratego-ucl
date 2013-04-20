package Pion;

import Intelligence.Joueur;

public class Marechal extends Pion
{
	public Marechal(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 10;
		name = "marechal";
		nombre = 1;
		team = teamm;
		path = "marechal.jpg";
		visibleByIA = false;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
