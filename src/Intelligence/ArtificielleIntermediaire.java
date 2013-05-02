package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Pion.Pion;

public class ArtificielleIntermediaire extends ArtificielleNormal
{
	ArrayList<Pion> listTempPion;
	
	public ArtificielleIntermediaire(Controller controller)
	{
		super(controller);
		setForceIA("IA Intermediaire");
		System.out.println("IA Intermediaire");
		currentStrategy="Attaque";
		setListOfDisplacement(new ArrayList<Deplacement>());
		count=0;
	}
	
	public void placeYourPions()
	{
		super.initializePions();
		super.setDrapeau();
		super.setBombe();
		
		setStrongestPions();
		setOtherPions();
		
		setVisibleIATrueForAll();
		controller.checkStopPJ2();
	}

	private void setOtherPions()
	{
		listTempPion.add(getPion("espion"));
		int i; int t; Random r = new Random();
		for(i=0; listTempPion.size()!=0 && i<10; i++)
		{
			if(getIaMap().getPion(2, i)==null)
			{
				t = r.nextInt(listTempPion.size());
				getIaMap().setEtat(2, i, listTempPion.get(t));
				listTempPion.remove(t);
			}
		}
		for(i=2; i>=0; i--)
		{
			for(int j=0; j<10; j++)
			{
				if(getIaMap().getPion(i, j)==null)
				{
					t = r.nextInt(ListePion.size());
					getIaMap().setEtat(i, j, ListePion.get(t));
					ListePion.remove(t);
				}
			}
		}
	}

	private void setStrongestPions()
	{
		getStrongestPion();
		int t; Random r = new Random();
		for(int i=0; i<10; i++)
		{
			if(getIaMap().getPion(3, i)==null)
			{
				t = r.nextInt(listTempPion.size());
				getIaMap().setEtat(3, i, listTempPion.get(t));
				listTempPion.remove(t);
			}
		}
	}

	private void getStrongestPion()
	{
		listTempPion = new ArrayList<Pion>();
		for(Pion pion : ListePion)
		{
			if(pion.getForce()>5){
				listTempPion.add(pion);
			}
		}
		ListePion.removeAll(listTempPion);
	}

	private void setVisibleIATrueForAll()
	{
		for(int i=9; i>5; i--)
		{
			for(int j=0; j<10; j++)
			{
				getIaMap().getPion(i, j).setVisibleByIA(true);
			}
		}
	}
	
	public void playAttaque()
	{
		System.out.println("Attaque mode");
		if(deplAttaque!=null && !isMine(deplAttaque.getOldX(), deplAttaque.getOldY())) deplAttaque=null;
		else
		{
			for(int i=1; i!=10 && deplAttaque==null; i++)
			{
				findPionToTake(i);
			}
		}
		goToDest(deplAttaque);
		playBestDisplacement(getListOfDisplacement());
	}
	
	public void playBestDisplacement(ArrayList<Deplacement> listDepl)
	{
		ArrayList<Deplacement> bestDeplacement = getBestDisplacement(listDepl);
		Deplacement deplacementDone;
		if(bestDeplacement.size()==0)
		{
			if(count!=10){
				playStrategy();
				count++;
			}
			else playSomething();
			
		}
		else
		{
			int t; Random r = new Random();
			t = r.nextInt(bestDeplacement.size());
			deplacementDone = bestDeplacement.get(t);
			doDisplacement(deplacementDone);
			listDepl.remove(deplacementDone);
			checkIfNeededToChangeStrategy(deplacementDone);
			getListOfDisplacement().removeAll(getListOfDisplacement());
		}
		count=0;
	}

}
