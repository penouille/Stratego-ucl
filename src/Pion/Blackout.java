package Pion;

/**
 * Classe représenatant une case interdite.
 * 
 * @author Florian
 *
 */
public class Blackout extends Pion
{
	public Blackout()
	{
		force = 1000;
		nbrDePas = 0;
		name = "bombe";
		team = true;
		nombre = 6;
	}
}
