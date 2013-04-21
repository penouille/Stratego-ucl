package Pion;

import Intelligence.Joueur;

public class Eclaireur extends Pion
{
	public Eclaireur(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 10;
		force = 2;
		name = "eclaireur";
		nombre = 8;
		team = teamm;
		path = "eclaireur.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
