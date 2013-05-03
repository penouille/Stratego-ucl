package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Game.Game;
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

public abstract class Artificielle 
{
	protected Map iaMap;
	
	protected int [] [] influenceMap;
	
	protected Controller controller;
	
	protected ArrayList<Pion> ListePion;
	
	protected ArrayList<Deplacement> listOfDisplacement;
	
	protected Game game;
	
	protected String forceIA;
	
	public Artificielle(Controller controller)
	{
		this.controller = controller;
		setIaMap(controller.getGame().getMap());
		influenceMap = new int [10] [10];
		setGame(controller.getGame());
	}
	
	public Map getIaMap()
	{
		return iaMap;
	}

	public void setIaMap(Map iaMap)
	{
		this.iaMap = iaMap;
	}

	public ArrayList<Deplacement> getListOfDisplacement()
	{
		return listOfDisplacement;
	}

	public void setListOfDisplacement(ArrayList<Deplacement> listOfDisplacement)
	{
		this.listOfDisplacement = listOfDisplacement;
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}
	
	public void setInfluence(int x, int y, int influence)
	{
		influenceMap [x][y] = influence;
	}
	
	public int getInfluenceMap(int x, int y)
	{
		return influenceMap[x][y];
	}
	
	public void printList()
	{
		for(Deplacement i : getListOfDisplacement())
		{
			System.out.println("oldX = "+i.getOldX()+", oldY = "+i.getOldY()+", x = "+i.getX()+", y = "+i.getY()+", et l'influence = "+i.getInfluence());
		}
	}
	
	protected void initializePions()
	{
		int i;
		ListePion = new ArrayList<Pion>();
		ListePion.add(new Drapeau(false, getGame().getJ2()));
		for(i=1; i<7; i++)
		{
			ListePion.add(new Bombe(false, getGame().getJ2()));
		}
		ListePion.add(new Espion(false, getGame().getJ2()));
		for(i=8; i<16; i++)
		{
			ListePion.add(new Eclaireur(false, getGame().getJ2()));
		}
		for(i=16; i<21; i++)
		{
			ListePion.add(new Demineur(false, getGame().getJ2()));
		}
		for(i=21; i<25; i++)
		{
			ListePion.add(new Sergent(false, getGame().getJ2()));
		}
		for(i=25; i<29; i++)
		{
			ListePion.add(new Lieutenant(false, getGame().getJ2()));
		}
		for(i=29; i<33; i++)
		{
			ListePion.add(new Capitaine(false, getGame().getJ2()));
		}
		for(i=33; i<36; i++)
		{
			ListePion.add(new Commandant(false, getGame().getJ2()));
		}
		for(i=36; i<38; i++)
		{
			ListePion.add(new Colonel(false, getGame().getJ2()));
		}
		ListePion.add(new General(false, getGame().getJ2()));
		ListePion.add(new Marechal(false, getGame().getJ2()));
		
		for(Pion pion: ListePion)
		{
			pion.setVisibleByIA(true);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return renvoit le pion et le retir de la liste des pions si il y en a encore un, renvoit null sinon, et ne
	 * touche pas � la liste des pions.
	 */
	protected Pion getPion(String name)
	{
		for(Pion i : ListePion)
		{
			if(i.getName().equals(name))
			{
				ListePion.remove(i);
				return i;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true si il n'y a rien sur la case mentionn�, false si il y a un pion, ou que la case n'existe pas.
	 */
	protected boolean isNothingOnCase(int x, int y)
	{
		if(x>=0 && x<10 && y>=0 && y<10) return getIaMap().getPion(x, y)==null;
		else return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true si il n'y a rien sur la case mentionn� ou que la case n'existe pas, false si il y a un pion.
	 */
	protected boolean isNoPionOnCase(int x, int y)
	{
		if(x>=0 && x<10 && y>=0 && y<10) return getIaMap().getPion(x, y)==null;
		else return true;
	}
	
	protected void setDrapeau()
	{
		int t; Random r = new Random();
		t = r.nextInt(30);
		if(t==20 || t==21 || t==24 || t==25 || t==28 || t==29) setDrapeau();
		else
		{
			getIaMap().setEtat(t/10, t%10, getPion("drapeau"));
			//une bombe � gauche du drapeau
			if(isNothingOnCase(t/10,t%10-1)) getIaMap().setEtat(t/10, t%10-1, getPion("bombe"));
			//une bombe en haut du drapeau
			if(isNothingOnCase(t/10-1,t%10)) getIaMap().setEtat(t/10-1, t%10, getPion("bombe"));
			//une bombe � droite du drapeau
			if(isNothingOnCase(t/10,t%10+1)) getIaMap().setEtat(t/10, t%10+1, getPion("bombe"));
			//une bombe en bas du drapeau
			if(isNothingOnCase(t/10+1,t%10)) getIaMap().setEtat(t/10+1, t%10, getPion("bombe"));
		}
	}
	
	protected void setBombe()
	{
		int t; Random r = new Random();
		t = r.nextInt(20);
		if(isNoPionOnCase(t/10+1, t%10) && isNoPionOnCase(t/10, t%10-1) && isNoPionOnCase(t/10, t%10+1))
		{
			getIaMap().setEtat(t/10+1, t%10, getPion("bombe"));
			if(t%10<5)
			{
				//Si la case design� par t est plus � gauche, je commence par mettre � droite
				getIaMap().setEtat(t/10, t%10+1, getPion("bombe"));
				if(isNothingOnCase(t/10, t%10-1)) getIaMap().setEtat(t/10, t%10-1, getPion("bombe"));
			}
			else
			{
				//Si la case design� par t est plus � droite, je commence par mettre � gauche
				getIaMap().setEtat(t/10, t%10-1, getPion("bombe"));
				if(isNothingOnCase(t/10, t%10+1)) getIaMap().setEtat(t/10, t%10+1, getPion("bombe"));
			}
			if(isNothingOnCase(t/10, t%10)) getIaMap().setEtat(t/10, t%10, getPion("sergent"));
				
				
		}
		else setBombe();
		Pion temp = getPion("bombe");
		while(temp!=null)
		{
			t = r.nextInt(40);
			if(isNothingOnCase(t/10, t%10))
			{
				getIaMap().setEtat(t/10, t%10, temp);
				temp = getPion("bombe");
			}
				
		}
	}
	public void placeYourPions()
	{
		controller.dude();
	}
	
	public void checkIfEclaireur(int oldX, int oldY, int x, int y)
	{
		if(x-oldX>1 || x-oldX<-1 || y-oldY>1 || y-oldY<-1) iaMap.getPion(x, y).setVisibleByIA(true);
	}	
	

	
	
	protected void doDisplacement(Deplacement depl)
	{
		System.out.println("Influence = "+depl.getInfluence()+", et x="+depl.getX()+" et y="+depl.getY()
				+ ", et oldX="+depl.getOldX()+" et oldY="+depl.getOldY());
		controller.setClick((depl.getOldX()*10)+depl.getOldY());
		controller.setPrise(getIaMap().getPion(depl.getOldX(), depl.getOldY()).getPath());
		controller.setClick((depl.getX()*10)+depl.getY()); 
		controller.setPrise(null);
	}

	public void play(Deplacement depl)
	{
		
	}

	public String getForceIA() {
		return forceIA;
	}

	public void setForceIA(String forceIA) {
		this.forceIA = forceIA;
	}
}