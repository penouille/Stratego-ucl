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
	private Map iaMap;
	
	private int [] [] influenceMap;
	
	private Controller controller;
	
	private ArrayList<Pion> ListePion;
	
	private ArrayList<Deplacement> listOfDisplacement;
	
	private Game game;
	
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
	
	protected void setInfluence(int x, int y, int influence)
	{
		influenceMap [x][y] = influence;
	}
	
	public int getInfluenceMap(int x, int y)
	{
		return influenceMap[x][y];
	}
	
	private void printInfluenceMap()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				System.out.print(getInfluenceMap(i, j)+" ");
			}
			System.out.println();
		}
	}
	
	private void printList()
	{
		for(Deplacement i : getListOfDisplacement())
		{
			System.out.println("oldX = "+i.getOldX()+", oldY = "+i.getOldY()+", x = "+i.getX()+", y = "+i.getY()+", et l'influence = "+i.getInfluence());
		}
	}
	
	public void initializePions()
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
	 * touche pas à la liste des pions.
	 */
	public Pion getPion(String name)
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
	 * @return true si il n'y a rien sur la case mentionné, false si il y a un pion, ou que la case n'existe pas.
	 */
	public boolean isNothingOnCase(int x, int y)
	{
		if(x>=0 && x<10 && y>=0 && y<10) return getIaMap().getPion(x, y)==null;
		else return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true si il n'y a rien sur la case mentionné ou que la case n'existe pas, false si il y a un pion.
	 */
	public boolean isNoPionOnCase(int x, int y)
	{
		if(x>=0 && x<10 && y>=0 && y<10) return getIaMap().getPion(x, y)==null;
		else return true;
	}
	
	public void setDrapeau()
	{
		int t; Random r = new Random();
		t = r.nextInt(30);
		if(t==20 || t==21 || t==24 || t==25 || t==28 || t==29) setDrapeau();
		else
		{
			getIaMap().setEtat(t/10, t%10, getPion("drapeau"));
			//une bombe à gauche du drapeau
			if(isNothingOnCase(t/10,t%10-1)) getIaMap().setEtat(t/10, t%10-1, getPion("bombe"));
			//une bombe en haut du drapeau
			if(isNothingOnCase(t/10-1,t%10)) getIaMap().setEtat(t/10-1, t%10, getPion("bombe"));
			//une bombe à droite du drapeau
			if(isNothingOnCase(t/10,t%10+1)) getIaMap().setEtat(t/10, t%10+1, getPion("bombe"));
			//une bombe en bas du drapeau
			if(isNothingOnCase(t/10+1,t%10)) getIaMap().setEtat(t/10+1, t%10, getPion("bombe"));
		}
	}
	
	public void setEclaireur()
	{
		getIaMap().setEtat(3, 0, getPion("eclaireur"));
		getIaMap().setEtat(3, 1, getPion("eclaireur"));
		getIaMap().setEtat(3, 4, getPion("eclaireur"));
		getIaMap().setEtat(3, 5, getPion("eclaireur"));
		getIaMap().setEtat(3, 8, getPion("eclaireur"));
		getIaMap().setEtat(3, 9, getPion("eclaireur"));
		Pion temp = getPion("eclaireur");
		int t; Random r = new Random();
		while(temp!=null)
		{
			t = r.nextInt(10);
			if(isNothingOnCase(2, t))
			{
				getIaMap().setEtat(2, t, temp);
				temp = getPion("eclaireur");
			}
				
		}
	}
	
	public void setBombe()
	{
		int t; Random r = new Random();
		t = r.nextInt(20);
		if(isNoPionOnCase(t/10+1, t%10) && isNoPionOnCase(t/10, t%10-1) && isNoPionOnCase(t/10, t%10+1))
		{
			getIaMap().setEtat(t/10+1, t%10, getPion("bombe"));
			if(t%10<5)
			{
				//Si la case designé par t est plus à gauche, je commence par mettre à droite
				getIaMap().setEtat(t/10, t%10+1, getPion("bombe"));
				if(isNothingOnCase(t/10, t%10-1)) getIaMap().setEtat(t/10, t%10-1, getPion("bombe"));
			}
			else
			{
				//Si la case designé par t est plus à droite, je commence par mettre à gauche
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
	
	public void setEspionAndMarechal()
	{
		int t; Random r = new Random();
		t = r.nextInt(30);
		if(isNothingOnCase(t/10, t%10) && 
				(isNothingOnCase(t/10-1, t%10) || isNothingOnCase(t/10, t%10-1) || isNothingOnCase(t/10, t%10+1)))
		{
			getIaMap().setEtat(t/10, t%10, getPion("marechal"));
			if(isNothingOnCase(t/10-1, t%10)) getIaMap().setEtat(t/10-1, t%10, getPion("espion"));
			else if(isNothingOnCase(t/10, t%10-1)) getIaMap().setEtat(t/10, t%10-1, getPion("espion"));
			else getIaMap().setEtat(t/10, t%10+1, getPion("espion"));
		}
		else setEspionAndMarechal();
	}
	
	public void setDemineur()
	{
		int t, i=0; Random r = new Random();
		Pion temp = getPion("demineur");
		while(i<3)
		{
			t = r.nextInt(20);
			if(isNothingOnCase(t/10, t%10))
			{
				getIaMap().setEtat(t/10, t%10, temp);
				temp = getPion("demineur");
				i++;
			}	
		}
		while(temp!=null)
		{
			t = 20+r.nextInt(20);
			if(isNothingOnCase(t/10, t%10))
			{
				getIaMap().setEtat(t/10, t%10, temp);
				temp = getPion("demineur");
			}	
		}
	}
	
	public void setSergent()
	{
		int t; Random r = new Random();
		Pion temp = getPion("sergent");
		while(temp!=null)
		{
			t = r.nextInt(40);
			if(isNothingOnCase(t/10, t%10))
			{
				getIaMap().setEtat(t/10, t%10, temp);
				temp = getPion("sergent");
			}	
		}
	}
	
	public void setLieutenant()
	{
		int t; Random r = new Random();
		Pion temp = getPion("lieutenant");
		while(temp!=null)
		{
			t = r.nextInt(40);
			if(isNothingOnCase(t/10, t%10))
			{
				getIaMap().setEtat(t/10, t%10, temp);
				temp = getPion("lieutenant");
			}	
		}
	}
	
	public void setOthersPions()
	{
		int t, place; Random r = new Random();
		while(ListePion.size()!=0)
		{
			t = r.nextInt(ListePion.size());
			place = r.nextInt(40);
			if(isNothingOnCase(place/10, place%10))
			{
				getIaMap().setEtat(place/10, place%10, ListePion.get(t));
				ListePion.remove(t);
			}
		}
	}

	public void placeYourPions()
	{
		// TODO Auto-generated method stub
		initializePions();
		
		setDrapeau();
		setEclaireur();
		setBombe();
		setEspionAndMarechal();
		setDemineur();
		setSergent();
		setLieutenant();
		setOthersPions();
		
		//updateInfluenceMap();
		//printInfluenceMap();
		controller.checkStopPJ2();
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

	public void play()
	{
		
	}
}