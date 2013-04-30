package Pion;

import Intelligence.Joueur;

public class Sergent extends Pion
{
	public Sergent(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 1;
		force = 4;
		name = "sergent";
		nombre = 4;
		team = teamm;
		path = "sergent.jpg";
		visibleByIA = !teamm;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
