package Pion;

import Intelligence.Joueur;

public class Demineur extends Pion
{
	public Demineur(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 3;
		name = "demineur";
		nombre = 5;
		team = teamm;
		path = "demineur.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
