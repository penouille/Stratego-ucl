package Pion;

import Intelligence.Joueur;

public class Colonel extends Pion
{
	public Colonel(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 8;
		name = "colonel";
		nombre = 2;
		team = teamm;
		path = "colonel.jpg";
		visibleByIA = false;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
