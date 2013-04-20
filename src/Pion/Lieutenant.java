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
		path = "lieutenant.jpg";
		visibleByIA = false;
	}
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
