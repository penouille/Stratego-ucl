package Intelligence;

import java.util.ArrayList;
import java.util.Random;

import Controller.Controller;
import Pion.Pion;

public class ArtificielleFacile extends Artificielle
{
	private String currentStrategy;
	
	private Deplacement deplExploration;
	private Deplacement deplAttaque;
	private Deplacement deplDefense;

	public ArtificielleFacile(Controller controller)
	{
		super(controller);
		setForceIA("IA Facile");
		currentStrategy="Exploration";
		setListOfDisplacement(new ArrayList<Deplacement>());
	}
	
	/**
	 * @param x
	 * @param y
	 * @return Renvoit true s'il y a un pion � la position (x,y) et qu'il appartient � l'IA (� l'equipe false)
	 * check si les coordonn�es sont valides.
	 */
	public boolean isMine(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10
				&& (getIaMap().getPion(x, y)!=null && !getIaMap().getPion(x, y).getTeam()));
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true si le pion au position (x,y) est un pion adverse, soit dans l'equipe true
	 */
	public boolean isEnemy(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10 
				&& getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getTeam());
	}
	
	/**
	 * @param x
	 * @param y
	 * @return renvoit true si la case (x,y) est un blackout, false sinon.
	 */
	public boolean isBlackout(int x, int y)
	{
		return (x>=0 && x<10 && y>=0 && y<10 
				&& getIaMap().getPion(x, y)!=null && getIaMap().getPion(x, y).getName().equals("blackout"));
	}
	
	/**
	 * @param oldX
	 * @param oldY
	 * @param x
	 * @param y
	 * @return donne l'influence d'un mouvement.
	 */
	protected int getInfluence(int oldX, int oldY, int x, int y)
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
		//si la case sur laquelle on souhaite se deplacer est vide, l'influence est egal � la force du pion qui s'y d�place
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
	
	/**
	 * recharge une nouvelles liste de d�placement avec leur influence associe.
	 */
	protected void updateListOfDisplacement()
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
	
	/**
	 * @param listDepl
	 * @return le ou les deplacement(s) avec l'influence la plus eleve.
	 */
	protected ArrayList<Deplacement> getBestDisplacement(ArrayList<Deplacement> listDepl)
	{
		if(listDepl.size()==0)
		{
			deplExploration = null;
			deplAttaque = null;
			deplDefense = null;
		}
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
	 * @return true si le pion est entour� de pion (alli� ou enemi), false s'il y a au moins une case vide.
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
	 * @return true s'il n'y a que des pions � l'IA autour de la position (x,y) ou une case qui n'existe pas.
	 */
	public boolean isOnlyMyPionAround(int x, int y)
	{
		return ((y-1<0 || isMine(x, y-1)) 
				&& (x-1<0 || isMine(x-1, y)) 
				&& (y+1>9 || isMine(x, y+1))
				&& (x+1>9 || isMine(x+1, y)));
	}
	
	/**
	 * @param x
	 * @param y
	 * @return renvoit true s'il n'y a que des pions de l'equipe true autour du pion au position (x,y)
	 */
	public boolean isOnlyEnemyAround(int x, int y)
	{
		return ((y-1<0 || isEnemy(x, y-1)) 
				&& (x-1<0 || isEnemy(x-1, y)) 
				&& (y+1>9 || isEnemy(x, y+1))
				&& (x+1>9 || isEnemy(x+1, y)));
	}
	
	/**
	 * @param name
	 * @param team
	 * @return true si il y a encore des pions dont le nom correspond a name apss� en parametre, et dans l'equipe
	 * passe en parametre.
	 */
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
	
	/**
	 * Regarde les pions qui se trouvent autour du pion au position (x,y), et additionne la force
	 * des pions alli�, si il y en a.
	 * @param x
	 * @param y
	 * @return l'influence autour d'un pion.
	 */
	protected int getInfluenceAroundPion(int x, int y)
	{
		int influence = 0;
		//on ajoute la force du pion alli� � gauche du pion en (x,y)
		if(y-1>=0 && getIaMap().getPion(x, y-1)!=null && !getIaMap().getPion(x, y-1).getTeam())
		{
			influence += getIaMap().getPion(x, y-1).getForce();
		}
		//on ajoute la force du pion alli� en haut du pion en (x,y)
		if(x-1>=0 && getIaMap().getPion(x-1, y)!=null && !getIaMap().getPion(x-1, y).getTeam())
		{
			influence += getIaMap().getPion(x-1, y).getForce();
		}
		//on ajoute la force du pion alli� � droite du pion en (x,y)
		if(y+1<10 && getIaMap().getPion(x, y+1)!=null && !getIaMap().getPion(x, y+1).getTeam())
		{
			influence += getIaMap().getPion(x, y+1).getForce();
		}
		//on ajoute la force du pion alli� en bas du pion en (x,y)
		if(x+1<10 && getIaMap().getPion(x+1, y)!=null && !getIaMap().getPion(x+1, y).getTeam())
		{
			influence += getIaMap().getPion(x+1, y).getForce();
		}
		return influence;
	}
	
	/**
	 * Ajoute tous des d�placement qu'un eclaireur peut faire (en haut, en bas, a gauche a droite)
	 * que ce soit 1 case, 2cases, 3cases, etc.
	 * @param x
	 * @param y
	 */
	public void addDeplForEclaireur(int x, int y)
	{
		int i; Pion pion; boolean end=false;
		//on regarde � gauche
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
		//on regarde � droite
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
	 * @return true si le pion en position (x,y) peut �tre pris par un pion de la team 
	 * preciser dans la variable byTeam. 
	 */
	public boolean canBeTaken(int x, int y, int perimetre, boolean byPion)
	{
		if(getIaMap().getPion(x, y)==null)
		{
			return false;
		}
		int h = 0, v;
		for(v=-perimetre; v<=0; v++)
		{
			for(int k=-h; k<=h; k++)
			{
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && getIaMap().getPion(x+v, y+k)!=null
						&& !isBlocked(x+v, y+k)
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
						&& !isBlocked(x+v, y+k)
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
		return false;
	}
	
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param perimetre
	 * @return Renvoit un objet Deplacement ou oldX, oldY represente la position du pion trouve.
	 * x et y �tant initialis�es � 0, et influence n'�tant pas initialis�.
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
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && getIaMap().getPion(x+v, y+k)!=null 
						&& !getIaMap().getPion(x+v, y+k).getTeam()
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
				if(x+v<10 && x+v>=0 && y+k<10 && y+k>=0 && getIaMap().getPion(x+v, y+k)!=null 
						&& !getIaMap().getPion(x+v, y+k).getTeam()
						&& getIaMap().getPion(x+v, y+k).getNbrDePas()!=0
						&& (getIaMap().getPion(x+v, y+k).getName().equals(name) || name.equals("osef")))
				{
					return new Deplacement(x+v,y+k,0,0);
				}
			}
			h--;
		}
		perimetre++;
		return isAPionOfMeAroundPos(name, x, y, perimetre);
	}
	
	/**
	 * trouve un pion a prendre
	 * @param perimetre
	 */
	protected void findPionToTake(int perimetre)
	{
		for(int i=9; i>=0 && deplAttaque==null; i--)
		{
			for(int j=9; j>=0 && deplAttaque==null; j--)
			{
				Pion temp = getIaMap().getPion(i, j);
				if(temp!=null && temp.getTeam() && !isOnlyEnemyAround(i, j) && temp.getVisibleByIA())
				{
					canBeTaken(i, j, perimetre, false);
				}
			}
		}
	}
	
	/**
	 * @param force
	 * @return le pion le plus faible de la carte, appartenant a l'IA, et le plus en bas
	 */
	protected Deplacement findWeakerPion(int force)
	{
		if(force==3){
			force +=1;
			return findWeakerPion(force);
		}
		for(int i=9; i>=0; i--)
		{
			for(int j=9; j>=0; j--)
			{
				if(isMine(i, j) && getIaMap().getPion(i, j).getForce()==force && !isBlocked(i, j))
				{
					return new Deplacement(i, j, 0, 0);
				}
			}
		}
		force +=1;
		return findWeakerPion(force);
	}
	
	/**
	 * Cette methode est appel� lorsqu'un eclaireur a tu� un autre eclaireur.
	 * Si elle a encore des eclaireurs, elle va essayer de trouver le plus pres dans un perimetre qui augmentera
	 * au fur et a mesure de la recherche, pour lui attribuer l'ancien deplacement programme (celui de
	 * l'eclaireur qui a tu� l'eclaireur adverse), mais en allant un case plus loin. 
	 * @param oldX
	 * @param oldY
	 * @param x
	 * @param y
	 */
	protected void exploreMore(int oldX, int oldY, int x, int y)
	{
		if(hasStillPion("eclaireur", false))
		{
			boolean find=false;
			for(int i=0; i<10 && !find; i++)
			{
				deplExploration = isAPionOfMeAroundPos("eclaireur", oldX, oldY, i);
				if(deplExploration!=null) find=true;
			}
			//si l'eclaireur est all� en bas
			if(oldX<x && oldY==y && x+1<10)
			{
				deplExploration.setX(x+1); deplExploration.setY(y);
			}
			//s'il est all� en haut
			else if(oldX>x && oldY==y && x-1>=0)
			{
				deplExploration.setX(x-1); deplExploration.setY(y);
			}
			//s'il est all� � droite
			else if(oldY<y && oldX==x && y+1<10)
			{
				deplExploration.setX(x); deplExploration.setY(y+1);
			}
			//s'il est all� � gauche
			else if(oldY>y && oldX==x && y-1>=0)
			{
				deplExploration.setX(x); deplExploration.setY(y-1);
			}
			else deplExploration=null;
		}
		
	}
	
	/**
	 * Cette m�thode check si le joueur de l'equipe true a deplacer un pion pres d'un de celui de l'IA
	 * En fonction de ce que l'IA connait des pions adverse, elle evalue un d�plcement � faire.
	 * @param x
	 * @param y
	 */
	protected void playerMoveNearMe(int x, int y)
	{
		Pion temp = getIaMap().getPion(x, y);
		//si le pion est mort en bougeant pres de moi, ou s'il n'y a rien que je puisse boug� � cot�.
		if(temp==null || isAPionOfMeAroundPos("osef", x, y, 1)==null) return;
		if(temp.getVisibleByIA())
		{
			if(canBeTaken(x, y, 1, false))
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

	/**
	 * Cette methode est appele par playerMoveNearMe
	 * Elle evalute la strategie lorsque le joeuur de l'equipe true a deplacer un pion inconnu pres 
	 * d'un pion de l'IA.
	 * Elle �value les chances qu'on puissent prendre sont pions. Si c'est n�gatif, elle tente de faire fuir son pion
	 * Sinon, elle attaque le pion inconnu.
	 * @param pionFinded
	 * @param x
	 * @param y
	 */
	protected void evaluateStrategy(Deplacement pionFinded, int x, int y)
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
			}
		}
		else 
		{
			deplAttaque = pionFinded; deplAttaque.setX(x); deplAttaque.setY(y);
			currentStrategy="Attaque";
		}

	}

	/**
	 * 
	 * @param myPion
	 * @return Renvoit la force du pion - le nombre de pion qui peuvent prendre ce pion.
	 */
	protected int howMuchPionCanTakeThis(Pion myPion) {
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

	/**
	 * Effectue un d�placement.
	 * @param depl
	 */
	private void goToDest(Deplacement depl)
	{
		if(depl==null)
		{
			setListOfDisplacement(new ArrayList<Deplacement>());
			return;
		}
		if(isOnlyMyPionAround(depl.getOldX(), depl.getOldY()))
		{
		}
		else
		{
			setListOfDisplacement(new ArrayList<Deplacement>());
			doOneMove(depl);
		}
	}

	/**
	 * Effectue un deplacement en fonction de la direction dans laquelle un pion doit aller
	 * @param depl
	 */
	private void doOneMove(Deplacement depl)
	{
		//S'il faut deplacer le pion � droite
		if(depl.getOldY()<depl.getY() && canMoveRight(depl))
		{
		}
		//S'il faut d�placer le pion � gauche
		else if(depl.getOldY()>depl.getY() && canMoveLeft(depl))
		{
		}
		//S'il faut d�placer le pion en bas
		else if(depl.getOldX()<depl.getX() && canMoveBottom(depl))
		{
		}
		//S'il faut d�placer le pion en haut
		else if(depl.getOldX()>depl.getX() && canMoveTop(depl))
		{
		}
		checkIfNeededDeleteDepl();
	}

	/**
	 * Regarde si l'une des tactiques de d�placement est accomplie, si oui, elle est mise � null.
	 */
	protected void checkIfNeededDeleteDepl()
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

	/**
	 * Regarde si on peut effectue un deplacement en haut.
	 * Si oui, elle renvoit true, en ajoutant dans la liste des daplcements le deplacement vers le haut.
	 * @param depl
	 * @return
	 */
	protected boolean canMoveTop(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldX()-i>=0 && i<=depl.getOldX()-depl.getX()
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

	/**
	 * Regarde si on peut effectue un deplacement en bas.
	 * Si oui, elle renvoit true, en ajoutant dans la liste des daplcements le deplacement vers le bas.
	 * @param depl
	 * @return
	 */
	protected boolean canMoveBottom(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldX()+i<10 && i<=depl.getX()-depl.getOldX()
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

	/**
	 * Regarde si on peut effectue un deplacement a gauche.
	 * Si oui, elle renvoit true, en ajoutant dans la liste des daplcements le deplacement vers la gauche.
	 * @param depl
	 * @return
	 */
	protected boolean canMoveLeft(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldY()-i>=0 && i<=depl.getOldY()-depl.getY()
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

	/**
	 * Regarde si on peut effectue un deplacement � droite.
	 * Si oui, elle renvoit true, en ajoutant dans la liste des daplcements le deplacement vers la droite.
	 * @param depl
	 * @return
	 */
	protected boolean canMoveRight(Deplacement depl) {
		for(int i=getIaMap().getPion(depl.getOldX(), depl.getOldY()).getNbrDePas(); i!=0; i--)
		{
			if(depl.getOldY()+i<10 && i<=depl.getY()-depl.getOldY()
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

	/**
	 * @param depl
	 * @return Renvoit un pion inconnu le plus vers le haut sous forme de d�placement, ou X et Y sont 
	 * les position du pion en question.
	 */
	protected boolean findUnknownPion(Deplacement depl)
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
	
	/**
	 * joue la tactique exploration.
	 * Soit des eclaireur si il y en a encore, soit avec des pions les plus faibles s'il n'y en a plus.
	 */
	public void playExploration()
	{
		//si je n'ai pas de mouvement retenu du pr�c�dent mouvement effectu�.
		if(deplExploration==null)
		{
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
							addDeplForEclaireur(i, j);
						}
					}
				}
			}
			//si je n'ai plus d'eclaireur ou qu'il m'en reste mais qu'ils sont bloqu�s..
			else
			{
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
		playBestDisplacement(getListOfDisplacement());
		
	}

	/**
	 * joue la tactique defense, soit s'�loigner d'un pion adverse.
	 */
	public void playDefense()
	{
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
	
	/**
	 * joue la tactique attaque, soit essayer de prendre un pion adverse.
	 */
	public void playAttaque()
	{
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
				currentStrategy="Exploration";
			}
		}
	}
	
	/**
	 * joue quelque chose lorsqu'aucune tactique n'a abouti.
	 */
	public void playSomething()
	{
		updateListOfDisplacement();
		playBestDisplacement(getListOfDisplacement());
	}

	/**
	 * joue le meilleur deplacement.
	 * @param listDepl
	 */
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
	}
	
	/**
	 * Regarde s'il faut changer de strategie en fonction d'un dpelacement du joueur true.
	 * @param depl
	 */
	protected void checkIfNeededToChangeStrategy(Deplacement depl)
	{
		if(depl==null)
		{
			return;
		}
		Pion temp = getIaMap().getPion(depl.getX(), depl.getY());
		if(temp!=null && temp.getTeam() && canBeTaken(depl.getX(), depl.getY(), 5, false))
		{
			currentStrategy="Attaque";
		}
		if(temp==null && currentStrategy.equals("Exploration"))
		{
			exploreMore(depl.getOldX(), depl.getOldY(), depl.getX(), depl.getY());
		}
	}

	/**
	 * regarde si il faut changer les coordonn�es des deplacement programm�e, ou s'il faut 
	 * les supprim�, ou ne rien faire.
	 * @param deplJoueur
	 */
	protected void checkChangeDeplProg(Deplacement deplJoueur)
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
		//s'il d�place son pion � cot� d'un de mes pions.
		playerMoveNearMe(deplJoueur.getX(), deplJoueur.getY());
	}

	/**
	 * joue une strategie.
	 */
	protected void playStrategy()
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
