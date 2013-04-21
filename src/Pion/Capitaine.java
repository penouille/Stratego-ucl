package Pion;

import Intelligence.Joueur;

public class Capitaine extends Pion
{
	public Capitaine(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 6;
		name = "capitaine";
		nombre = 4;
		team = teamm;
		path = "capitaine.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
