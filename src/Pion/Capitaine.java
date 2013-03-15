package Pion;

public class Capitaine extends Pion
{
	public Capitaine(boolean joueur)
	{
		nbrDePas = 1;
		force = 6;
		name = "capitaine";
		nombre = 4;
		team = joueur;
		path = "capitaine.jpg";
	}

}
