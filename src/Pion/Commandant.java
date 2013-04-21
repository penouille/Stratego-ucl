package Pion;

import Intelligence.Joueur;

public class Commandant extends Pion
{
	public Commandant(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 7;
		name = "commandant";
		nombre = 3;
		team = teamm;
		path = "commandant.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
