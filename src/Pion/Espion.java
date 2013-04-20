package Pion;

import Intelligence.Joueur;

public class Espion extends Pion
{
	public Espion(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 1;
		name = "espion";
		nombre = 1;
		team = teamm;
		path = "espion.jpg";
		visibleByIA = false;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
