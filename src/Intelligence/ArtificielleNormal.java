package Intelligence;

import java.util.ArrayList;

import Controller.Controller;
import Pion.Pion;

public class ArtificielleNormal extends ArtificielleFacile
{
	protected String currentStrategy;
	
	protected Deplacement deplExploration;
	protected Deplacement deplAttaque;
	protected Deplacement deplDefense;
	
	protected int count;

	public ArtificielleNormal(Controller controller)
	{
		super(controller);
		setForceIA("IA Normal");
		System.out.println("IA Normal");
		currentStrategy="Exploration";
		setListOfDisplacement(new ArrayList<Deplacement>());
		count=0;
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
	
	protected void skirtBlackout(Deplacement depl)
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
		doOneMove(depl);
	}
	
	/**
	 * Ajoute tous des déplacement qu'un eclaireur peut faire (en haut, en bas, a gauche a droite)
	 * que ce soit 1 case, 2cases, 3cases, etc.
	 * @param x
	 * @param y
	 */
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

	protected void goToDest(Deplacement depl)
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

	/**
	 * Effectue un deplacement en fonction de la direction dans laquelle un pion doit aller
	 * @param depl
	 */
	protected void doOneMove(Deplacement depl)
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
	 * joue la tactique exploration.
	 * Soit des eclaireur si il y en a encore, soit avec des pions les plus faibles s'il n'y en a plus.
	 */
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

}
