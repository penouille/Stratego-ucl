package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Pion.Pion;

public class ArtificielleNormal extends Artificielle
{
	private String currentStrategy;

	public ArtificielleNormal(Controller controller)
	{
		super(controller);
		System.out.println("IA Normal");
		currentStrategy="Exploration";
	}
	
	public int getInfluence(int oldX, int oldY, int x, int y)
	{
		//si la case est un blackout
		if(getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getName().equals("blackout"))
		{
			return getIaMap().getPion(x, y).getForce()*-1;
		}
		//si le pion sur la case (x,y) m'appartient
		else if(getIaMap().getPion(x, y)!=null && !getIaMap().getPion(x, y).getTeam())
		{
			return -5;
		}
		//si la case sur laquelle on souhaite se deplacer est vide, l'influence est egal à la force du pion qui s'y déplace
		else if(getIaMap().getPion(x, y)==null)
		{
			return getIaMap().getPion(oldX, oldY).getForce();
		}
		//si il y a un pion, mais qu'on ne le connait pas.
		else if(getIaMap().getPion(x, y)!=null && !getIaMap().getPion(x, y).getVisibleByIA() )
		{
			//je privilegie l'exploration avec les eclaireurs;
			if(getIaMap().getPion(oldX, oldY).getName().equals("eclaireur"))
			{
				return 20;
			}
			else
			{
				return getIaMap().getPion(oldX, oldY).getForce()-5;
			}
		}
		//si il y a un pion, mais qu'on le connait.
		else if(getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getVisibleByIA())
		{
			if(getIaMap().getPion(oldX, oldY).getForce()>getIaMap().getPion(x, y).getForce())
			{
				return 21;
			}
			else if(getIaMap().getPion(oldX, oldY).getForce()==getIaMap().getPion(x, y).getForce())
			{
				return 0;
			}
			else return -10;
		}
		else return -5;
	}
	
	private void updateListOfDisplacement()
	{
		setListOfDisplacement(new ArrayList<Deplacement>());
		Deplacement depl;
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(getIaMap().getPion(i, j)!=null && !getIaMap().getPion(i, j).getTeam())
				{
					for(int u=1; u<=getIaMap().getPion(i, j).getNbrDePas(); u++)
					{
						if( (i-u>=0) && getGame().canMoveOnNewCase(i, j, i-u, j, false))
						{
							depl = new Deplacement(i, j, i-u, j);
							depl.setInfluence(getInfluence(i, j, i-u, j));
							getListOfDisplacement().add(depl);
						}
						if( (i+u<10) && getGame().canMoveOnNewCase(i, j, i+u, j, false))
						{
							depl = new Deplacement(i, j, i+u, j);
							depl.setInfluence(getInfluence(i, j, i+u, j));
							getListOfDisplacement().add(depl);
						}
						if( (j-u>=0) && getGame().canMoveOnNewCase(i, j, i, j-u, false))
						{
							depl = new Deplacement(i, j, i, j-u);
							depl.setInfluence(getInfluence(i, j, i, j-u));
							getListOfDisplacement().add(depl);
						}
						if( (j+u<10) && getGame().canMoveOnNewCase(i, j, i, j+u, false))
						{
							depl = new Deplacement(i, j, i, j+u);
							depl.setInfluence(getInfluence(i, j, i, j+u));
							getListOfDisplacement().add(depl);
						}
					}
				}
			}
		}
	}
	
	private ArrayList<Deplacement> getBestDisplacement()
	{
		ArrayList<Deplacement> bestDisplacement = new ArrayList<Deplacement>();
		for(Deplacement i : getListOfDisplacement())
		{
			if(bestDisplacement.size()==0)
			{
				bestDisplacement.add(i);
			}
			else if(bestDisplacement.get(0).getInfluence()<i.getInfluence())
			{
				bestDisplacement.removeAll(bestDisplacement);
				bestDisplacement.add(i);
			}
			else if(bestDisplacement.get(0).getInfluence()==i.getInfluence())
			{
				bestDisplacement.add(i);
			}
		}
		return bestDisplacement;
	}
	
	public boolean isBlocked(int x, int y)
	{
		return (isNothingOnCase(x, y-1) 
				&& isNothingOnCase(x-1, y) 
				&& isNothingOnCase(x, y+1) 
				&& isNothingOnCase(x+1, y));
	}
	
	public boolean hasStillEclaireur()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(getIaMap().getPion(i, j)!=null 
						&& !getIaMap().getPion(i, j).getTeam() 
						&& getIaMap().getPion(i, j).getName().equals("eclaireur"))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public int getInfluenceOfDeplEclaireur(int oldX, int oldY, int x, int y)
	{
		int influence = 0;
		//on ajoute la force du pion allié à gauche de l'eclaireur
		if(oldY-1>=0 && getIaMap().getPion(oldX, oldY-1)!=null && !getIaMap().getPion(oldX, oldY-1).getTeam())
		{
			influence += getIaMap().getPion(oldX, oldY-1).getForce();
		}
		//on ajoute la force du pion allié en haut de l'eclaireur
		if(oldX-1>=0 && getIaMap().getPion(oldX-1, oldY)!=null && !getIaMap().getPion(oldX-1, oldY).getTeam())
		{
			influence += getIaMap().getPion(oldX-1, oldY).getForce();
		}
		//on ajoute la force du pion allié à droite de l'eclaireur
		if(oldY+1<10 && getIaMap().getPion(oldX, oldY+1)!=null && !getIaMap().getPion(oldX, oldY+1).getTeam())
		{
			influence += getIaMap().getPion(oldX, oldY+1).getForce();
		}
		//on ajoute la force du pion allié en bas de l'eclaireur
		if(oldX+1<10 && getIaMap().getPion(oldX+1, oldY)!=null && !getIaMap().getPion(oldX+1, oldY).getTeam())
		{
			influence += getIaMap().getPion(oldX+1, oldY).getForce();
		}
		return influence;
	}
	
	public void addDeplForEclaireur(int x, int y)
	{
		int i; Pion pion;
		//on regarde à gauche
		for(i=1; i<10; i++)
		{
			if(y-i>=0)
			{
				pion = getIaMap().getPion(x, y-i);
				if(pion!=null)
				{
					if(!pion.getTeam()) break;
					else if(pion.getTeam() && !pion.getVisibleByIA())
					{
						Deplacement depl = new Deplacement(x, y, x, y-i);
						depl.setInfluence(getInfluenceOfDeplEclaireur(x, y, x, y-i)-i);
						getListOfDisplacement().add(depl);
					}
				}
			}
			else break;
		}
		//on regarde en haut
		for(i=1; i<10; i++)
		{
			if(x-i>=0)
			{
				pion = getIaMap().getPion(x-i, y);
				if(pion!=null)
				{
					if(!pion.getTeam()) break;
					else if(pion.getTeam() && !pion.getVisibleByIA())
					{
						Deplacement depl = new Deplacement(x, y, x-i, y);
						depl.setInfluence(getInfluenceOfDeplEclaireur(x, y, x-i, y)-i);
						getListOfDisplacement().add(depl);
					}
				}
			}
			else break;
		}
		//on regarde à droite
		for(i=1; i<10; i++)
		{
			if(y+i<10)
			{
				pion = getIaMap().getPion(x, y+i);
				if(pion!=null)
				{
					if(!pion.getTeam()) break;
					else if(pion.getTeam() && !pion.getVisibleByIA())
					{
						Deplacement depl = new Deplacement(x, y, x, y+i);
						depl.setInfluence(getInfluenceOfDeplEclaireur(x, y, x, y+i)-i);
						getListOfDisplacement().add(depl);
					}
				}
			}
			else break;
		}
		//on regarde en bas
		for(i=1; i<10; i++)
		{
			if(x+i<10)
			{
				pion = getIaMap().getPion(x+i, y);
				if(pion!=null)
				{
					if(!pion.getTeam()) break;
					else if(pion.getTeam() && !pion.getVisibleByIA())
					{
						Deplacement depl = new Deplacement(x, y, x+i, y);
						depl.setInfluence(getInfluenceOfDeplEclaireur(x, y, x+i, y)-i);
						getListOfDisplacement().add(depl);
					}
				}
			}
			else break;
		}
	}
	
	public boolean pionAdversePeutEtrePris(int x, int y, int perimetre)
	{
		if(getIaMap().getPion(x, y)==null) return false;
		int h = 0, v;
		for(v=-perimetre; v<=0; v++)
		{
			for(int k=-h; k<=h; k++)
			{
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && getIaMap().getPion(x+v, y+k)!=null 
						&& !getIaMap().getPion(x+v, y+k).getTeam()
						&& getIaMap().getPion(x+v, y+k).getForce()>getIaMap().getPion(x, y).getForce())
				{
					return true;
				}
			}
			h++;
		}
		h -=2;
		for(v=1; v<=perimetre; v++)
		{
			for(int k=-h; k<=h; k++)
			{
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && getIaMap().getPion(x+v, y+k)!=null 
						&& !getIaMap().getPion(x+v, y+k).getTeam()
						&& getIaMap().getPion(x+v, y+k).getForce()>getIaMap().getPion(x, y).getForce())
				{
					return true;
				}
			}
			h--;
		}
		return false;
	}
	
	public void playExploration()
	{
		setListOfDisplacement(new ArrayList<Deplacement>());
		if(hasStillEclaireur())
		{
			for(int i=9; i>=0; i--)
			{
				for(int j=9; j>=0; j--)
				{
					//System.out.println("different de null "+getIaMap().getPion(i, j)!=null);
					//System.out.println("m'appartient "+ !getIaMap().getPion(i, j).getTeam());
					//System.out.println("est un eclaireur "+ getIaMap().getPion(i, j).getName().equals("eclaireur"));
					if(getIaMap().getPion(i, j)!=null 
							&& !getIaMap().getPion(i, j).getTeam() 
							&& getIaMap().getPion(i, j).getName().equals("eclaireur"))
					{
						if(!isBlocked(i, j))
						{
							addDeplForEclaireur(i, j);
						}
						else
						{
							
						}
					}
				}
			}
		}
		else
		{
			
		}
		ArrayList<Deplacement> bestDeplacement = getBestDisplacement();
		Deplacement deplacementDone;
		if(bestDeplacement.size()==1)
		{
			deplacementDone = bestDeplacement.get(0);
			doDisplacement(deplacementDone);
		}
		else
		{
			int t; Random r = new Random();
			t = r.nextInt(bestDeplacement.size());
			deplacementDone = bestDeplacement.get(t);
			doDisplacement(deplacementDone);
		}
		if(pionAdversePeutEtrePris(deplacementDone.getX(), deplacementDone.getY(), 6)) currentStrategy="Attaque";
	}
	
	public void playDefense()
	{
		updateListOfDisplacement();
		ArrayList<Deplacement> bestDeplacement = getBestDisplacement();
		if(bestDeplacement.size()==1)
		{
			doDisplacement(bestDeplacement.get(0));
		}
		else
		{
			int t; Random r = new Random();
			t = r.nextInt(bestDeplacement.size());
			doDisplacement(bestDeplacement.get(t));
		}
	}
	
	public void playAttaque()
	{
		System.out.println("Attaque mode");
		updateListOfDisplacement();
		ArrayList<Deplacement> bestDeplacement = getBestDisplacement();
		if(bestDeplacement.size()==1)
		{
			doDisplacement(bestDeplacement.get(0));
		}
		else
		{
			int t; Random r = new Random();
			t = r.nextInt(bestDeplacement.size());
			doDisplacement(bestDeplacement.get(t));
		}
	}
	
	public void play() 
	{
		// TODO Auto-generated method stub
		if(currentStrategy.equals("Exploration"))
		{
			playExploration();
		}
		else if(currentStrategy.equals("Defense"))
		{
			playDefense();
		}
		else if(currentStrategy.equals("Attaque"))
		{
			playAttaque();
		}
		//printList();
		
	}

}
