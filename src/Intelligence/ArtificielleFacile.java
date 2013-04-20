package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;

public class ArtificielleFacile extends Artificielle
{

	public ArtificielleFacile(Controller controller)
	{
		super(controller);
		System.out.println("IA Facile");
	}
	
	/*public int getInfluence(int oldX, int oldY, int x, int y)
	{
		//si la case est un blackout
		if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getName().equals("blackout"))
		{
			return iaMap.getPion(x, y).getForce()*-1;
		}
		//si le pion sur la case x,y m'appartient
		else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getTeam())
		{
			return -5;
		}
		//Cas à part pour les eclaireurs.
		else if(iaMap.getPion(oldX, oldY) !=null && iaMap.getPion(oldX, oldY).getName().equals("eclaireur"))
		{
			//si la case est vide mais que l'influence dessus est superieur à la force de l'eclaireur
			if(iaMap.getPion(x, y)==null && getInfluenceMap(x, y)>iaMap.getPion(oldX, oldY).getForce())
			{
				return getInfluenceMap(x, y);
			}
			//si la case est vide, et que l'influence dessus est inferieur à la force de l'eclaireur
			else if(iaMap.getPion(x, y)==null)
			{
				return iaMap.getPion(oldX, oldY).getForce();
			}
			//si il y a un pion, mais qu'on le connait.
			else if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getVisibleByIA())
			{
				if(iaMap.getPion(oldX, oldY).getForce()>iaMap.getPion(x, y).getForce())
				{
					return 21;
				}
				else if(iaMap.getPion(oldX, oldY).getForce()==iaMap.getPion(x, y).getForce())
				{
					return 0;
				}
				else return -10;
			}
			//si il y a un pion, mais qu'on ne le connait pas.
			//je priviligie l'exploration avec les eclaireurs
			else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getVisibleByIA())
			{
				return 20;
			}
			
		}
		//si la case sur laquelle on souhaite se deplacer est vide, l'influence est egal à la force du pion qui s'y déplace
		else if(iaMap.getPion(x, y)==null)
		{
			if(getInfluenceMap(x, y)>iaMap.getPion(oldX, oldY).getForce()) return getInfluenceMap(x, y);
			else return iaMap.getPion(oldX, oldY).getForce();
		}
		//si il y a un pion, mais qu'on ne le connait pas.
		else if(iaMap.getPion(x, y)!=null && !iaMap.getPion(x, y).getVisibleByIA() )
		{
			if(getInfluenceMap(x, y)>iaMap.getPion(oldX, oldY).getForce()-5) return getInfluenceMap(x, y);
			else return iaMap.getPion(oldX, oldY).getForce()-5;
		}
		//si il y a un pion, mais qu'on le connait.
		else if(iaMap.getPion(x, y)!=null && iaMap.getPion(x, y).getVisibleByIA() && iaMap.getPion(x, y).getTeam())
		{
			if(iaMap.getPion(oldX, oldY).getForce()>iaMap.getPion(x, y).getForce())
			{
				return 21;
			}
			else if(iaMap.getPion(oldX, oldY).getForce()==iaMap.getPion(x, y).getForce())
			{
				return 0;
			}
			else return -10;
		}
		return -5;
	}*/
	
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

	private void updateInfluenceMap()
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(getIaMap().getPion(i, j)!=null && !getIaMap().getPion(i, j).getTeam())
				{
					for(int u=0; u<=getIaMap().getPion(i, j).getNbrDePas(); u++)
					{
						
						//a gauche
						if(0<=j-u)
						{
							setInfluence(i, j-u, getInfluence(i, j, i, j-u));
						}
						//en haut
						if(0<=i-u)
						{
							setInfluence(i-u, j, getInfluence(i, j, i-u, j));
						}
						//a droite
						if(j+u<10)
						{
							setInfluence(i, j+u, getInfluence(i, j, i, j+u));
						}
						//en bas
						if(i+u<10)
						{
							setInfluence(i+u, j, getInfluence(i, j, i+u, j));
						}
					}
				}
			}
		}
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
	
	public void play() 
	{
		// TODO Auto-generated method stub
		updateListOfDisplacement();
		//printList();
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

}
