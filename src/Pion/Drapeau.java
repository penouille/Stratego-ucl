package Pion;

import Intelligence.Joueur;

public class Drapeau extends Pion
{	
	public Drapeau(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 0;
		force = 0;
		name = "drapeau";
		nombre = 1;
		team = teamm;
		path = "drapeau.jpg";
		visibleByIA = false;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
