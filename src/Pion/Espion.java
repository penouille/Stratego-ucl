package Pion;

public class Espion extends Pion
{
	public Espion(boolean joueur)
	{
		nbrDePas = 1;
		force = 1;
		name = "espion";
		nombre = 1;
		team = joueur;
		path = "espion.jpg";
	}
}
