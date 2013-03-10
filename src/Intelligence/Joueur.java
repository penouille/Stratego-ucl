package Intelligence;

import java.util.ArrayList;

import Pion.Bombe;
import Pion.Capitaine;
import Pion.Colonel;
import Pion.Commandant;
import Pion.Demineur;
import Pion.Drapeau;
import Pion.Eclaireur;
import Pion.Espion;
import Pion.General;
import Pion.Lieutenant;
import Pion.Marechal;
import Pion.Pion;
import Pion.Sergent;

public class Joueur 
{
	private boolean Tour;
	
	private ArrayList<Pion> ListPion;
	
	private Pion ListePion [];
	
	public Joueur()
	{
		//Initialiser les pions du joueur
		System.out.println("Instanciation d'un joueur");
		
		ListPion = new ArrayList<Pion>();
		
		ListePion = new Pion [40];
		initializePion();
		
	}
	
	public void initializePion()
	{
int i;
		
		ListePion[0] = new Drapeau();
		for(i=1; i<7; i++)
		{
			ListePion[i] = new Bombe();
		}
		ListePion[7] = new Espion();
		for(i=8; i<16; i++)
		{
			ListePion[i] = new Eclaireur();
		}
		for(i=16; i<21; i++)
		{
			ListePion[i] = new Demineur();
		}
		for(i=21; i<25; i++)
		{
			ListePion[i] = new Sergent();
		}
		for(i=25; i<29; i++)
		{
			ListePion[i] = new Lieutenant();
		}
		for(i=29; i<33; i++)
		{
			ListePion[i] = new Capitaine();
		}
		for(i=33; i<36; i++)
		{
			ListePion[i] = new Commandant();
		}
		for(i=36; i<38; i++)
		{
			ListePion[i] = new Colonel();
		}
		ListePion[38] = new General();
		ListePion[39] = new Marechal();
	}
}
