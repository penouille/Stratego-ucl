package Pion;

public class Sergent extends Pion
{
	
	public Sergent(boolean joueur)
	{
		nbrDePas = 1;
		force = 4;
		name = "sergent";
		nombre = 4;
		team = joueur;
	}
}
