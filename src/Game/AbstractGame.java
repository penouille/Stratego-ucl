package Game;

import java.util.ArrayList;

import observer.Observable;
import observer.Observer;

public abstract class AbstractGame implements Observable
{
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	
	//Implémentation du pattern observer
	public void addObserver(Observer obs) 
	{
		this.listObserver.add(obs);
	}
	 
	public void notifyObserver(String str) 
	{
		if(str.matches("^0[0-9]+"))
	    {
			str = str.substring(1, str.length());
	    }
	 
		for(Observer obs : listObserver)
	    {
			obs.update(str);
	    }
	}
	 
	public void removeObserver() 
	{
	    listObserver = new ArrayList<Observer>();
	}
}
