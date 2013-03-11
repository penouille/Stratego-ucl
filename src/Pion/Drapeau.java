package Pion;

public class Drapeau extends Pion
{
	
	public Drapeau(boolean joueur)
	{
		nbrDePas = 0;
		force = 0;
		name = "drapeau";
		nombre = 1;
		team = joueur;
	}
	
}
