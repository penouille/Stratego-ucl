package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Game.Map;
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

public class Artificielle 
{
	private Map iaMap;
	
	private Controller controller;
	
	private ArrayList<Pion> ListePion;
	
	public Artificielle(Controller controller)
	{
		this.controller = controller;
		iaMap = controller.getGame().getMap();
	}
	
	public void initializePions()
	{
		int i;
		ListePion = new ArrayList<Pion>();
		ListePion.add(new Drapeau(false));
		for(i=1; i<7; i++)
		{
			ListePion.add(new Bombe(false));
		}
		ListePion.add(new Espion(false));
		for(i=8; i<16; i++)
		{
			ListePion.add(new Eclaireur(false));
		}
		for(i=16; i<21; i++)
		{
			ListePion.add(new Demineur(false));
		}
		for(i=21; i<25; i++)
		{
			ListePion.add(new Sergent(false));
		}
		for(i=25; i<29; i++)
		{
			ListePion.add(new Lieutenant(false));
		}
		for(i=29; i<33; i++)
		{
			ListePion.add(new Capitaine(false));
		}
		for(i=33; i<36; i++)
		{
			ListePion.add(new Commandant(false));
		}
		for(i=36; i<38; i++)
		{
			ListePion.add(new Colonel(false));
		}
		ListePion.add(new General(false));
		ListePion.add(new Marechal(false));
		
		for(Pion pion: ListePion)
		{
			pion.setVisibleByIA(true);
		}
	}

	public void placeYourPions()
	{
		// TODO Auto-generated method stub
		initializePions();
		int t; Random r = new Random();
		for(int i=0; i<4; i++)
		{
			for(int j=0;j<10;j++)
			{
				t = r.nextInt(ListePion.size());
				iaMap.setEtat(i, j, ListePion.get(t));
				ListePion.remove(t);
;			}
		}
		controller.checkStopPJ2();
	}

	public void play() 
	{
		// TODO Auto-generated method stub
		
	}
	
	
}