package Pion;

public class Eclaireur extends Pion
{
	public Eclaireur(boolean joueur)
	{
		nbrDePas = 10;
		force = 2;
		name = "eclaireur";
		nombre = 8;
		team = joueur;
		path = "eclaireur.jpg";
	}

}
