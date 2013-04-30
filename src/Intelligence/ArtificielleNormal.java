package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Pion.Pion;

public class ArtificielleNormal extends Artificielle
{
	private String currentStrategy;
	
	//private ArrayList<Pion> listPionDeadJ1;
	//private ArrayList<Pion> listPionDeadJ2;
	
	private Deplacement deplExploration;
	private Deplacement deplAttaque;
	private Deplacement deplDefense;
	
	private int count;

	public ArtificielleNormal(Controller controller)
	{
		super(controller);
		System.out.println("IA Normal");
		currentStrategy="Exploration";
		setListOfDisplacement(new ArrayList<Deplacement>());
		count=0;
		//listPionDeadJ1 = getGame().getJ1().getListPionDead();
		//listPionDeadJ2 = getGame().getJ2().getListPionDead();
	}
	
	/**
	 * @param x
	 * @param y
	 * @return Renvoit true s'il y a un pion à la position (x,y) et qu'il appartient à l'IA (à l'equipe false)
	 * check si les coordonnées sont valides.
	 */
	public boolean isMine(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10
				&& (getIaMap().getPion(x, y)!=null && !getIaMap().getPion(x, y).getTeam()));
	}
	
	public boolean isEnemy(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10 
				&& getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getTeam());
	}
	
	public boolean isBlackout(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10 
				&& getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getName().equals("blackout"));
	}
	
	public boolean isBombe(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10 
				&& getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getName().equals("bombe"));
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
	
	private ArrayList<Deplacement> getBestDisplacement(ArrayList<Deplacement> listDepl)
	{
		System.out.println("taille de la liste des deplcament dans getBestDisplacement "+getListOfDisplacement().size());
		ArrayList<Deplacement> bestDisplacement = new ArrayList<Deplacement>();
		for(Deplacement i : listDepl)
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
	
	/**
	 * @param x
	 * @param y
	 * @return true si le pion est entouré de pion (allié ou enemi), false s'il y a au moins une case vide.
	 */
	public boolean isBlocked(int x, int y)
	{
		return (!isNothingOnCase(x, y-1)
				&& !isNothingOnCase(x-1, y)
				&& !isNothingOnCase(x, y+1)
				&& !isNothingOnCase(x+1, y));
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true s'il n'y a que des pions à l'IA autour de la position (x,y) ou une case qui n'existe pas.
	 */
	public boolean isOnlyMyPionAround(int x, int y)
	{
		return ((y-1<0 || isMine(x, y-1)) 
				&& (x-1<0 || isMine(x-1, y)) 
				&& (y+1>9 || isMine(x, y+1))
				&& (x+1>9 || isMine(x+1, y)));
	}
	
	public boolean isOnlyEnemyAround(int x, int y)
	{
		return ((y-1<0 || isEnemy(x, y-1)) 
				&& (x-1<0 || isEnemy(x-1, y)) 
				&& (y+1>9 || isEnemy(x, y+1))
				&& (x+1>9 || isEnemy(x+1, y)));
	}
	
	public void unblockPion(Deplacement depl, int dist)
	{
		//TODO appel infini et ça craint du boudin
		if(count==10) return;
		int x = depl.getOldX(), y = depl.getOldY();
		System.out.println("déblocage d'un pion au position ("+x+","+y+") et distance ="+dist);
		//deplacer le pion à droite
		if(depl.getOldY()<depl.getY() && isMine(x, y+dist) && getIaMap().getPion(x, y+dist).getNbrDePas()!=0)
		{
			if(isNothingOnCase(x, y+dist+1)){
				doOneMove(new Deplacement(x, y+dist, x, y+dist+1));	return;
			}
			else if(isBlackout(x, y+dist+1)){
				depl.setOldY(y+dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x-1, y+dist)){
				doOneMove(new Deplacement(x, y+dist, x-1, y+dist)); return;
			}
			else if(isBlackout(x-1, y+dist)){
				depl.setOldY(y+dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x+1, y+dist)){
				doOneMove(new Deplacement(x, y+dist, x+1, y+dist)); return;
			}
			else if(isBlackout(x+1, y+dist)){
				depl.setOldY(y+dist); skirtBlackout(depl); return;
			}
		}
		else if(depl.getOldY()<depl.getY() && isBlackout(x, y+dist)){
			skirtBlackout(depl); return;
		}
		
		//déplacer le pion à gauche
		else if(depl.getOldY()>depl.getY() && isMine(x, y-dist) && getIaMap().getPion(x, y-dist).getNbrDePas()!=0)
		{
			if(isNothingOnCase(x, y-dist-1)){
				doOneMove(new Deplacement(x, y-dist, x, y-dist-1)); return;
			}
			else if(isBlackout(x, y-dist-1)){
				depl.setOldY(y-dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x-1, y-dist)){
				doOneMove(new Deplacement(x, y-dist, x-1, x-1, y-dist)); return;
			}
			else if(isBlackout(x-1, y-dist)){
				depl.setOldY(y-dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x+1, y-dist)){
				doOneMove(new Deplacement(x, y-dist, x+1, y-dist)); return;
			}
			else if(isBlackout(x+1, y-dist)){
				depl.setOldY(y-dist); skirtBlackout(depl); return;
			}
		}
		else if(depl.getOldY()>depl.getY() && isBlackout(x, y-dist)){
			skirtBlackout(depl); return;
		}
		
		//S'il faut déplacer le pion en bas
		else if(depl.getOldX()<depl.getX() && isMine(x+dist, y) && getIaMap().getPion(x+dist, y).getNbrDePas()!=0)
		{
			if(isNothingOnCase(x+dist, y-1)){
				doOneMove(new Deplacement(x+dist, y, x+dist, y-1)); return;
			}
			else if(isBlackout(x+dist, y-1)){
				depl.setOldX(x+dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x+dist, y+1)){
				doOneMove(new Deplacement(x+dist, y, x+dist, y+1)); return;
			}
			else if(isBlackout(x+dist, y+1)){
				depl.setOldX(x+dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x+dist+1, y)){
				doOneMove(new Deplacement(x+dist, y, x+dist+1, y)); return;
			}
			else if(isBlackout(x+dist+1, y)){
				depl.setOldX(x+dist); skirtBlackout(depl); return;
			}
		}
		else if(depl.getOldX()<depl.getX() && isBlackout(x+dist, y)){
			skirtBlackout(depl); return;
		}
		
		//S'il faut déplacer le pion en haut
		else if(depl.getOldX()>depl.getX() && isMine(x-dist, y) && getIaMap().getPion(x-dist, y).getNbrDePas()!=0)
		{
			if(isNothingOnCase(x-dist, y-1)){
				doOneMove(new Deplacement(x-dist, y, x-dist, y-1)); return;
			}
			else if(isBlackout(x-dist, y-1)){
				depl.setOldX(x-dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x-dist, y+1)){
				doOneMove(new Deplacement(x-dist, y, x-dist, y+1)); return;
			}
			else if(isBlackout(x-dist, y+1)){
				depl.setOldX(x-dist); skirtBlackout(depl); return;
			}
			
			else if(isNothingOnCase(x-dist-1, y)){
				doOneMove(new Deplacement(x-dist, y, x-dist-1, y)); return;
			}
			else if(isBlackout(x-dist-1, y)){
				depl.setOldX(x-dist); skirtBlackout(depl); return;
			}
		}
		else if(depl.getOldX()>depl.getX() && isBlackout(x-dist, y)){
			skirtBlackout(depl); return;
		}
		else
		{
			count++;
			dist+=1;
			unblockPion(depl, dist);
		}
	}
	
	private void skirtBlackout(Deplacement depl)
	{
		System.out.println("On evite le blackout !");
		int x=depl.getOldX(), y = depl.getOldY();
		if(x==3){
			if(y==2 || y==6){
				depl.setX(x+3); 
				depl.setY(y-1);
			}
			else{
				depl.setX(x+3); 
				depl.setY(y+1);
			}
		}
		else if(x==4){
			depl.setX(x-1); 
			depl.setY(y);
		}
		else if(x==5){
			depl.setX(x+1);
			depl.setY(y);
		}
		else if(x==6){
			if(y==2 || y==6){
				depl.setX(x-3);
				depl.setY(y-1);
			}
			else{
				depl.setX(x-3);
				depl.setY(y+1);
			}
		}
	}

	public boolean hasStillPion(String name, boolean team)
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(getIaMap().getPion(i, j)!=null 
						&& getIaMap().getPion(i, j).getTeam()==team
						&& getIaMap().getPion(i, j).getName().equals(name))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public int getInfluenceAroundPion(int x, int y)
	{
		int influence = 0;
		//on ajoute la force du pion allié à gauche de l'eclaireur
		if(y-1>=0 && getIaMap().getPion(x, y-1)!=null && !getIaMap().getPion(x, y-1).getTeam())
		{
			influence += getIaMap().getPion(x, y-1).getForce();
		}
		//on ajoute la force du pion allié en haut de l'eclaireur
		if(x-1>=0 && getIaMap().getPion(x-1, y)!=null && !getIaMap().getPion(x-1, y).getTeam())
		{
			influence += getIaMap().getPion(x-1, y).getForce();
		}
		//on ajoute la force du pion allié à droite de l'eclaireur
		if(y+1<10 && getIaMap().getPion(x, y+1)!=null && !getIaMap().getPion(x, y+1).getTeam())
		{
			influence += getIaMap().getPion(x, y+1).getForce();
		}
		//on ajoute la force du pion allié en bas de l'eclaireur
		if(x+1<10 && getIaMap().getPion(x+1, y)!=null && !getIaMap().getPion(x+1, y).getTeam())
		{
			influence += getIaMap().getPion(x+1, y).getForce();
		}
		return influence;
	}
	
	public void addDeplForEclaireur(int x, int y)
	{
		int i; Pion pion; boolean end=false;
		//on regarde à gauche
		for(i=1; i<10 && !end; i++)
		{
			if(y-i>=0)
			{
				pion = getIaMap().getPion(x, y-i);
				if(pion!=null)
				{
					if(pion.getTeam())
					{
						if(!pion.getVisibleByIA())
						{
							Deplacement depl = new Deplacement(x, y, x, y-i);
							depl.setInfluence(getInfluenceAroundPion(x, y)-i);
							getListOfDisplacement().add(depl);
							end=true;
						}
						else end=true;
					}
					else end=true;
				}
			}
			else end=true;
		}
		end=false;
		//on regarde en haut
		for(i=1; i<10 && !end; i++)
		{
			if(x-i>=0)
			{
				pion = getIaMap().getPion(x-i, y);
				if(pion!=null)
				{
					if(pion.getTeam())
					{
						if(!pion.getVisibleByIA())
						{
							Deplacement depl = new Deplacement(x, y, x-i, y);
							depl.setInfluence(getInfluenceAroundPion(x, y)-i);
							getListOfDisplacement().add(depl);
							end=true;
						}
						else end=true;
					}
					else end=true;
				}
			}
			else end=true;
		}
		end=false;
		//on regarde à droite
		for(i=1; i<10 && !end; i++)
		{
			if(y+i<10)
			{
				pion = getIaMap().getPion(x, y+i);
				if(pion!=null)
				{
					if(pion.getTeam())
					{
						if(!pion.getVisibleByIA())
						{
							Deplacement depl = new Deplacement(x, y, x, y+i);
							depl.setInfluence(getInfluenceAroundPion(x, y)-i);
							getListOfDisplacement().add(depl);
							end=true;
						}
						else end=true;
					}
					else end=true;
				}
			}
			else end=true;
		}
		end=false;
		//on regarde en bas
		for(i=1; i<10 && !end; i++)
		{
			if(x+i<10)
			{
				pion = getIaMap().getPion(x+i, y);
				if(pion!=null)
				{
					if(pion.getTeam())
					{
						if(!pion.getVisibleByIA())
						{
							Deplacement depl = new Deplacement(x, y, x+i, y);
							depl.setInfluence(getInfluenceAroundPion(x, y)-i);
							getListOfDisplacement().add(depl);
							end=true;
						}
						else end=true;
					}
					else end=true;
				}
			}
			else end=true;
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param perimetre
	 * @return true si le pion en position (x,y) peut être pris par un pion de la team 
	 * preciser dans la variable byTeam. 
	 */
	public boolean canBeTaken(int x, int y, int perimMax, boolean byPion, int perimetre)
	{
		if(getIaMap().getPion(x, y)==null || perimetre>perimMax)
		{
			return false;
		}
		int h = 0, v;
		for(v=-perimetre; v<=0; v++)
		{
			for(int k=-h; k<=h; k++)
			{
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && getIaMap().getPion(x+v, y+k)!=null
						&& !isOnlyMyPionAround(x+v, y+k)
						&& getIaMap().getPion(x+v, y+k).getNbrDePas()!=0
						&& getIaMap().getPion(x+v, y+k).getTeam()==byPion
						&& getIaMap().getPion(x, y).getTeam()!=byPion)
				{
					if(getIaMap().getPion(x+v, y+k).getForce()>getIaMap().getPion(x, y).getForce())
					{
						deplAttaque = new Deplacement(x+v, y+k, x, y);
						return true;
					}
					else if(getIaMap().getPion(x, y).getName().equals("marechal")
							&& getIaMap().getPion(x+v, y+k).getName().equals("espion"))
					{
						System.out.println("Le marechal adverse peut-etre pris");
						deplAttaque = new Deplacement(x+v, y+k, x, y);
						return true;
					}
					else if(getIaMap().getPion(x, y).getName().equals("bombe")
							&& getIaMap().getPion(x+v, y+k).getName().equals("demineur"))
					{
						deplAttaque = new Deplacement(x+v, y+k, x, y);
						return true;
					}
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
						&& !isOnlyMyPionAround(x+v, y+k)
						&& getIaMap().getPion(x+v, y+k).getNbrDePas()!=0
						&& getIaMap().getPion(x+v, y+k).getTeam()==byPion
						&& getIaMap().getPion(x, y).getTeam()!=byPion)
				{
					if(getIaMap().getPion(x+v, y+k).getForce()>getIaMap().getPion(x, y).getForce())
					{
						deplAttaque = new Deplacement(x+v, y+k, x, y);
						return true;
					}
					else if(getIaMap().getPion(x, y).getName().equals("marechal")
							&& getIaMap().getPion(x+v, y+k).getName().equals("espion"))
					{
						System.out.println("Le marechal adverse peut-etre pris");
						deplAttaque = new Deplacement(x+v, y+k, x, y);
						return true;
					}
					else if(getIaMap().getPion(x, y).getName().equals("bombe")
							&& getIaMap().getPion(x+v, y+k).getName().equals("demineur"))
					{
						deplAttaque = new Deplacement(x+v, y+k, x, y);
						return true;
					}
				}
			}
			h--;
		}
		perimetre +=1;
		return canBeTaken(x, y, perimMax, byPion, perimetre++);
	}
	
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param perimetre
	 * @return Renvoit un objet Deplacement ou oldX, oldY represente la position du pion trouve.
	 * x et y étant initialisées à 0, et influence n'étant pas initialisé.
	 * renvoit null sinon
	 * mettre "osef" comme name pour savoir si il y a un pion autour de la position.
	 */
	public Deplacement isAPionOfMeAroundPos(String name, int x, int y, int perimetre)
	{
		int h = 0, v;
		for(v=-perimetre; v<=0; v++)
		{
			for(int k=-h; k<=h; k++)
			{
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && isMine(x+v, y+k)
						&& getIaMap().getPion(x+v, y+k).getNbrDePas()!=0
						&& (getIaMap().getPion(x+v, y+k).getName().equals(name) || name.equals("osef")))
				{
					return new Deplacement(x+v,y+k,0,0);
				}
			}
			h++;
		}
		h -=2;
		for(v=1; v<=perimetre; v++)
		{
			for(int k=-h; k<=h; k++)
			{
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && isMine(x+v, y+k)
						&& getIaMap().getPion(x+v, y+k).getNbrDePas()!=0
						&& (getIaMap().getPion(x+v, y+k).getName().equals(name) || name.equals("osef")))
				{
					return new Deplacement(x+v,y+k,0,0);
				}
			}
			h--;
		}
		return null;
	}
	
	private void findPionToTake(int perimetre)
	{
		for(int i=9; i>=0 && deplAttaque==null; i--)
		{
			for(int j=9; j>=0 && deplAttaque==null; j--)
			{
				Pion temp = getIaMap().getPion(i, j);
				if(temp!=null && temp.getTeam() && !isOnlyEnemyAround(i, j) && temp.getVisibleByIA())
				{
					canBeTaken(i, j, perimetre, false, 1);
				}
			}
		}
	}
	
	public Deplacement findWeakerPion(int force)
	{
		if(force==3)
		{
			force +=1;
			return findWeakerPion(force);
		}
		for(int i=9; i>=0; i--)
		{
			for(int j=9; j>=0; j--)
			{
				if(isMine(i, j) && getIaMap().getPion(i, j).getForce()==force)
				{
					return new Deplacement(i, j, 0, 0);
				}
			}
		}
		force +=1;
		return findWeakerPion(force);
	}
	
	private void exploreMore(int oldX, int oldY, int x, int y)
	{
		if(hasStillPion("eclaireur", false))
		{
			boolean find=false;
			for(int i=0; i<10 && !find; i++)
			{
				deplExploration = isAPionOfMeAroundPos("eclaireur", oldX, oldY, i);
				if(deplExploration!=null) find=true;
			}
			//si l'eclaireur est allé en bas
			if(oldX<x && oldY==y && x+1<10)
			{
				deplExploration.setX(x+1); deplExploration.setY(y);
			}
			//s'il est allé en haut
			else if(oldX>x && oldY==y && x-1>=0)
			{
				deplExploration.setX(x-1); deplExploration.setY(y);
			}
			//s'il est allé à droite
			else if(oldY<y && oldX==x && y+1<10)
			{
				deplExploration.setX(x); deplExploration.setY(y+1);
			}
			//s'il est allé à gauche
			else if(oldY>y && oldX==x && y-1>=0)
			{
				deplExploration.setX(x); deplExploration.setY(y-1);
			}
			else deplExploration=null;
		}
		
	}
	
	private void playerMoveNearMe(int x, int y)
	{
		Pion temp = getIaMap().getPion(x, y);
		//si le pion est mort en bougeant pres de moi, ou s'il n'y a rien que je puisse bougé à coté.
		if(temp==null || isAPionOfMeAroundPos("osef", x, y, 1)==null) return;
		if(temp.getVisibleByIA())
		{
			if(canBeTaken(x, y, 1, false, 1))
			{
				currentStrategy="Attaque";
			}
		}
		else
		{
			String [] typePion = {"marechal", "general", "colonel", "commandant", "capitaine", "lieutenant", "sergent",
					"demineur", "eclaireur", "espion"}; int i; Deplacement pionFinded=null;
			for(i=0; pionFinded==null && i<typePion.length; i++)
			{
				pionFinded=isAPionOfMeAroundPos(typePion[i], x, y, 1);
			}
			evaluateStrategy(pionFinded, x, y);
		}
	}

	private void evaluateStrategy(Deplacement pionFinded, int x, int y)
	{
		Pion myPion = getIaMap().getPion(pionFinded.getOldX(), pionFinded.getOldY());
		int chance = howMuchPionCanTakeThis(myPion);
		if(chance<=0)
		{
			if(!isBlocked(pionFinded.getOldX(), pionFinded.getOldY()))
			{
				deplDefense=pionFinded;
				if(isNothingOnCase(pionFinded.getOldX(), pionFinded.getOldY()-1))
				{
					deplDefense.setX(pionFinded.getOldX()); deplDefense.setY(pionFinded.getOldY()-1);
				}
				else if(isNothingOnCase(pionFinded.getOldX(), pionFinded.getOldY()+1))
				{
					deplDefense.setX(pionFinded.getOldX()); deplDefense.setY(pionFinded.getOldY()+1);
				}
				else if(isNothingOnCase(pionFinded.getOldX()-1, pionFinded.getOldY()))
				{
					deplDefense.setX(pionFinded.getOldX()-1); deplDefense.setY(pionFinded.getOldY());
				}
				else if(isNothingOnCase(pionFinded.getOldX()+1, pionFinded.getOldY()))
				{
					deplDefense.setX(pionFinded.getOldX()+1); deplDefense.setY(pionFinded.getOldY());
				}
				currentStrategy = "Defense";
				System.out.println("changement de strategie : Defense mode (playerMoveNearMe)");
			}
		}
		else 
		{
			deplAttaque = pionFinded; deplAttaque.setX(x); deplAttaque.setY(y);
			currentStrategy="Attaque";
			System.out.println("changement de strategie : Attaque mode (playerMoveNearMe)");
		}

	}

	/**
	 * 
	 * @param myPion
	 * @return Renvoit la force du pion - le nombre de pion qui peuvent prendre ce pion.
	 */
	private int howMuchPionCanTakeThis(Pion myPion) {
		int nbr= 0; Pion temp;
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				temp = getIaMap().getPion(i, j);
				if(temp!=null && temp.getTeam() && temp.getNbrDePas()!=0 && temp.getForce()>myPion.getForce())
				{
					nbr++;
				}
			}
		}
		return myPion.getForce()-nbr;
	}

	private void goToDest(Deplacement depl)
	{
		if(depl==null || !isMine(depl.getOldX(), depl.getOldY()))
		{
			System.out.println("goToDest avec depl==null");
			return;
		}
		else
		{
			System.out.println("currentStrategy = "+currentStrategy+ ", Influence="+depl.getInfluence()
					+", et x="+depl.getX()+" et y="+depl.getY()
					+", et oldX="+depl.getOldX()+" et oldY="+depl.getOldY());
			System.out.println("goToDest avec depl!=null");
			setListOfDisplacement(new ArrayList<Deplacement>());
			doOneMove(depl);
		}
	}

	private void doOneMove(Deplacement depl)
	{
		System.out.println("doOneMove");
		//S'il faut deplacer le pion à droite
		if(depl.getOldY()<depl.getY() && canMoveRight(depl))
		{
			System.out.println("Deplacement à droite");
		}
		//S'il faut déplacer le pion à gauche
		else if(depl.getOldY()>depl.getY() && canMoveLeft(depl))
		{
			System.out.println("Deplacement à gauche");
		}
		//S'il faut déplacer le pion en bas
		else if(depl.getOldX()<depl.getX() && canMoveBottom(depl))
		{
			System.out.println("Deplacement en bas");
		}
		//S'il faut déplacer le pion en haut
		else if(depl.getOldX()>depl.getX() && canMoveTop(depl))
		{
			System.out.println("Deplacement en haut");
		}
		else
		{
			unblockPion(depl, 1);
		}
		checkIfNeededDeleteDepl();
	}

	/**
	 * @param depl
	 * Regarde si l'une des tactiques de déplacement est accomplie, si oui, elle est mise à null.
	 */
	private void checkIfNeededDeleteDepl()
	{
		if(deplAttaque!=null 
				&& deplAttaque.getOldX()==deplAttaque.getX() 
				&& deplAttaque.getOldY()==deplAttaque.getY())
		{
			deplAttaque=null;
		}
		if(deplDefense!=null
				&& deplDefense.getOldX()==deplDefense.getX()
				&& deplDefense.getOldY()==deplDefense.getY())
		{
			deplDefense=null;
		}
		if(deplExploration!=null
				&& deplExploration.getOldX()==deplExploration.getX()
				&& deplExploration.getOldY()==deplExploration.getY())
		{
			deplExploration=null;
		}
	}

	private boolean canMoveTop(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldX()-i>=0 
			&& getGame().canMoveOnNewCase(depl.getOldX(), depl.getOldY(), depl.getOldX()-i, depl.getOldY(), false))
			{
				Deplacement move;
				move = new Deplacement(depl.getOldX(), depl.getOldY(), depl.getOldX()-i, depl.getOldY(), 42);
				getListOfDisplacement().add(move);
				depl.setOldX(depl.getOldX()-i);
				return true;
			}
		}
		return false;
	}

	private boolean canMoveBottom(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldX()+i<10 
			&& getGame().canMoveOnNewCase(depl.getOldX(), depl.getOldY(), depl.getOldX()+i, depl.getOldY(), false))
			{
				Deplacement move;
				move = new Deplacement(depl.getOldX(), depl.getOldY(), depl.getOldX()+i, depl.getOldY(), 42);
				getListOfDisplacement().add(move);
				depl.setOldX(depl.getOldX()+i);
				return true;
			}
		}
		return false;
	}

	private boolean canMoveLeft(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldY()-i>=0
			&& getGame().canMoveOnNewCase(depl.getOldX(), depl.getOldY(), depl.getOldX(), depl.getOldY()-i, false))
			{
				Deplacement move;
				move = new Deplacement(depl.getOldX(), depl.getOldY(), depl.getOldX(), depl.getOldY()-i, 42);
				getListOfDisplacement().add(move);
				depl.setOldY(depl.getOldY()-i);
				return true;
			}
		}
		return false;
	}

	private boolean canMoveRight(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldY()+i<10 
			&& getGame().canMoveOnNewCase(depl.getOldX(), depl.getOldY(), depl.getOldX(), depl.getOldY()+i, false))
			{
				Deplacement move;
				move = new Deplacement(depl.getOldX(), depl.getOldY(), depl.getOldX(), depl.getOldY()+i, 42);
				getListOfDisplacement().add(move);
				depl.setOldY(depl.getOldY()+i);
				return true;
			}
		}
		return false;
	}

	private boolean findUnknownPion(Deplacement depl)
	{
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<10; j++)
			{
				if(isEnemy(i, j) && !getIaMap().getPion(i, j).getVisibleByIA() && !isOnlyEnemyAround(i, j))
				{
					depl.setX(i); depl.setY(j); return true;
				}
			}
		}
		return false;
	}
	
	public void playExploration()
	{
		System.out.println("Exploration mode");
		//si je n'ai pas de mouvement retenu du précédent mouvement effectué.
		if(deplExploration==null)
		{
			System.out.println("Pas de deplacement exploration programme");
			setListOfDisplacement(new ArrayList<Deplacement>());
			if(hasStillPion("eclaireur", false))
			{
				Pion temp;
				for(int i=9; i>=0; i--)
				{
					for(int j=9; j>=0; j--)
					{
						temp = getIaMap().getPion(i, j);
						if(isMine(i, j) && temp.getName().equals("eclaireur") && !isBlocked(i, j))
						{
							System.out.println("j'ai encore des eclaireurs non bloqué");
							addDeplForEclaireur(i, j);
						}
					}
				}
			}
			//si je n'ai plus d'eclaireur ou qu'il m'en reste mais qu'ils sont bloqués..
			else
			{
				System.out.println("Exploration avec pion ou eclaireur bloqué");
				deplExploration = findWeakerPion(2);
				if(!findUnknownPion(deplExploration))
				{
					deplExploration=null;
					currentStrategy="Attaque";
				}
			}
		}
		else if(!isMine(deplExploration.getOldX(), deplExploration.getOldY())) deplExploration=null;
		goToDest(deplExploration);
		if(getListOfDisplacement().size()==0)
		{
			System.out.println("J'ai encore des eclaireurs non bloqué, " +
					"mais ils ne savent rien explorer en ligne droite");
			deplExploration = findWeakerPion(2);
			if(!findUnknownPion(deplExploration))
			{
				deplExploration=null;
				currentStrategy="Attaque";
			}
		}
		playBestDisplacement(getListOfDisplacement());
		
	}

	public void playDefense()
	{
		System.out.println("Defense mode");
		goToDest(deplDefense);
		playBestDisplacement(getListOfDisplacement());
		if(deplDefense==null)
		{
			if(deplAttaque!=null)
			{
				currentStrategy="Attaque";
			}
			else currentStrategy="Exploration";
		}
	}
	
	public void playAttaque()
	{
		System.out.println("Attaque mode");
		if(deplAttaque!=null && !isMine(deplAttaque.getOldX(), deplAttaque.getOldY())) deplAttaque=null;
		goToDest(deplAttaque);
		playBestDisplacement(getListOfDisplacement());
		if(deplAttaque==null)
		{
			for(int i=1; i!=10 && deplAttaque==null; i++)
			{
				findPionToTake(i);
			}
			if(deplAttaque==null)
			{
				System.out.println("pas de pion à attaquer->switch strategie:Exploration");
				currentStrategy="Exploration";
			}
		}
	}
	
	public void playSomething()
	{
		System.out.println("I play something");
		updateListOfDisplacement();
		playBestDisplacement(getListOfDisplacement());
	}

	public void playBestDisplacement(ArrayList<Deplacement> listDepl)
	{
		ArrayList<Deplacement> bestDeplacement = getBestDisplacement(listDepl);
		Deplacement deplacementDone;
		if(bestDeplacement.size()==0)
		{
			playSomething();
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
	
	private void checkIfNeededToChangeStrategy(Deplacement depl)
	{
		if(depl==null)
		{
			System.out.println("Error -> deplacementDone == null");
			return;
		}
		Pion temp = getIaMap().getPion(depl.getX(), depl.getY());
		if(temp!=null && temp.getTeam() && canBeTaken(depl.getX(), depl.getY(), 5, false, 1))
		{
			System.out.println("changement de strategie : Attaque mode");
			currentStrategy="Attaque";
		}
		if(temp==null && currentStrategy.equals("Exploration"))
		{
			exploreMore(depl.getOldX(), depl.getOldY(), depl.getX(), depl.getY());
			System.out.println("J'ai exploreMore.");
		}
		System.out.println("taille de la list des deplacements ="+getListOfDisplacement().size());
		System.out.println();
	}

	public void checkChangeDeplProg(Deplacement deplJoueur)
	{
		if(deplAttaque!=null)
		{
			//si il a pris le pion que je comptais deplacer.
			if(deplJoueur.getX()==deplAttaque.getOldX() && deplJoueur.getY()==deplAttaque.getOldY() 
					&& (isNoPionOnCase(deplJoueur.getX(), deplJoueur.getY()) 
							|| isEnemy(deplJoueur.getX(), deplJoueur.getY())
							|| isMine(deplJoueur.getX(), deplJoueur.getY())))
			{
				deplAttaque=null;
			}
			//si il a deplacer le pion que je comptais prendre
			else
			{
				if(deplJoueur.getOldX()==deplAttaque.getX() && deplJoueur.getOldY()==deplAttaque.getY())
				{
					deplAttaque.setX(deplJoueur.getX()); deplAttaque.setY(deplJoueur.getY());
				}
				if(isNothingOnCase(deplAttaque.getX(), deplAttaque.getY()) 
						|| isMine(deplAttaque.getX(), deplAttaque.getY()))
				{
					deplAttaque=null;
				}
			}
		}
		if(deplDefense!=null)
		{
			//si il a pris le pion que je comptais deplacer.
			if(deplJoueur.getX()==deplDefense.getOldX() && deplJoueur.getY()==deplDefense.getOldY() 
					&& (isNoPionOnCase(deplJoueur.getX(), deplJoueur.getY()) 
							|| isEnemy(deplJoueur.getX(), deplJoueur.getY())))
			{
				deplDefense=null;
			}
		}
		if(deplExploration!=null)
		{
			//si il a pris le pion que je comptais deplacer.
			if(deplJoueur.getX()==deplExploration.getOldX() && deplJoueur.getY()==deplExploration.getOldY() 
					&& (isNoPionOnCase(deplJoueur.getX(), deplJoueur.getY()) 
							|| isEnemy(deplJoueur.getX(), deplJoueur.getY())))
			{
				deplExploration=null;
			}
			//si il a deplacer le pion que je comptais "explorer"
			else
			{
				if(deplJoueur.getOldX()==deplExploration.getX() && deplJoueur.getOldY()==deplExploration.getY())
				{
					deplExploration.setX(deplJoueur.getX()); deplExploration.setY(deplJoueur.getY());
				}
				if(isNothingOnCase(deplExploration.getX(), deplExploration.getY()) 
						|| isMine(deplExploration.getX(), deplExploration.getY()))
				{
					deplExploration=null;
				}
			}
		}
		//s'il déplace son pion à coté d'un de mes pions.
		playerMoveNearMe(deplJoueur.getX(), deplJoueur.getY());
	}

	public void playStrategy()
	{
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
	}
	
	public void play(Deplacement depl) 
	{
		checkChangeDeplProg(depl);
		playStrategy();
	}

}
