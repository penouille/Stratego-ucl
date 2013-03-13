package Pion;

public class Demineur extends Pion
{
	public Demineur(boolean joueur)
	{
		nbrDePas = 1;
		force = 3;
		name = "demineur";
		nombre = 5;
		team = joueur;
	}
}
