package Pion;

public class Marechal extends Pion
{
	public Marechal(boolean joueur)
	{
		nbrDePas = 1;
		force = 10;
		name = "marechal";
		nombre = 1;
		team = joueur;
		path = "marechal.jpg";
		visibleByIA = false;
	}
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
