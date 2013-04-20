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
		path = "sergent.jpg";
		visibleByIA = false;
	}
	public String getPath()
	{
		return path.substring(0,path.indexOf("."))+joueur.getPrefColor()+".jpg";
	}
}
