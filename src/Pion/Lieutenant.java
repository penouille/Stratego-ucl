package Pion;

public class Lieutenant extends Pion
{
	public Lieutenant(boolean joueur)
	{
		nbrDePas = 1;
		force = 5;
		name = "lieutenant";
		nombre = 4;
		team = joueur;
	}
}
