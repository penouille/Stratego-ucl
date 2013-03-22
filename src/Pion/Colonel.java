package Pion;

public class Colonel extends Pion
{
	public Colonel(boolean joueur)
	{
		nbrDePas = 1;
		force = 8;
		name = "colonel";
		nombre = 2;
		team = joueur;
		path = "colonel.jpg";
		visibleByIA = false;
	}

}
