package Pion;

public class Eclaireur extends Pion
{
	public Eclaireur(boolean joueur)
	{
		nbrDePas = 10;
		force = 2;
		name = "eclaireur";
		nombre = 8;
		team = joueur;
		path = "eclaireur.jpg";
		visibleByIA = false;
	}
	public String getPath()
	{
		System.out.println(path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg");
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}

}
