package Pion;

import Intelligence.Joueur;

public class General extends Pion
{
	public General(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 9;
		name = "general";
		nombre = 1;
		team = teamm;
		path = "general.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
