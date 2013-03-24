package Pion;

import Intelligence.Joueur;

public class Bombe extends Pion
{

	public Bombe(boolean teamm, Joueur prefJoueur)
	{
		nbrDePas = 0;
		force = 11;
		name = "bombe";
		team = teamm;
		nombre = 6;
		path = "bombe.jpg";
		visibleByIA = false;
		joueur = prefJoueur;
	}
	
	public String getPath()
	{
		System.out.println(path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg");
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
