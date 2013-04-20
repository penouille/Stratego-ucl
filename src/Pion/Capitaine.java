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
		visibleByIA = false;
	}
	
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
