package Pion;

public class General extends Pion
{
	public General(boolean joueur)
	{
		nbrDePas = 1;
		force = 9;
		name = "general";
		nombre = 1;
		team = joueur;
		path = "general.jpg";
		visibleByIA = false;
	}
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
