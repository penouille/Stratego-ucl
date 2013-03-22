package Pion;

public class Commandant extends Pion
{
	public Commandant(boolean joueur)
	{
		nbrDePas = 1;
		force = 7;
		name = "commandant";
		nombre = 3;
		team = joueur;
		path = "commandant.jpg";
		visibleByIA = false;
	}

}
