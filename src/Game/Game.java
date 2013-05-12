package Game;

import java.util.ArrayList;
import java.util.Random;

import Intelligence.Joueur;
import Pion.Bombe;
import Pion.Capitaine;
import Pion.Colonel;
import Pion.Commandant;
import Pion.Demineur;
import Pion.Drapeau;
import Pion.Eclaireur;
import Pion.Espion;
import Pion.Fight;
import Pion.General;
import Pion.Lieutenant;
import Pion.Marechal;
import Pion.Pion;
import Pion.Sergent;


/**
 * Classe représentant l'état d'une partie à un moment donné.
 * 
 * @author Florian
 *
 *
 */
public class Game
{
	
	private Map map;
	
	private Joueur J1;
	private Joueur J2;
	
	private ArrayList<Pion> LostTrue;
	private ArrayList<Pion> LostFalse;
	
	public Game()
	{
		map = new Map();
		J1 = new Joueur(true);
		J2 = new Joueur(false);
		
		LostTrue = new ArrayList<Pion>();
		LostFalse = new ArrayList<Pion>();
	}
	
	public Joueur getJ1()
	{
		return this.J1;
	}
	public Joueur getJ2()
	{
		return this.J2;
	}
	
	public int sizeMap()
	{
		return map.getSize();
	}
	
	public Map getMap() 
	{
		return this.map;
	}
	
	public boolean logicalXOR(boolean x, boolean y) 
	{
	    return ( ( x || y ) && ! ( x && y ) );
	}
	
	public ArrayList<Pion> getLostTrue()
	{
		return LostTrue;
	}
	
	public ArrayList<Pion> getLostFalse()
	{
		return LostFalse;
	}
	
	/**
	 * @param oldX, oldY, x, y, nbrPas
	 * @return Cette methode renvoit true si il y a un obstacle sur le trajet, et false sinon.
	 */
	public boolean checkObstacleOnWay(int oldX, int oldY, int x, int y, int nbrPas)
	{
		//si le pion descend.
		if(x>oldX)
		{
			for(int i=oldX+1; i<x; i++)
			{
				if(map.getPion(i, y)!=null) return true;
			}
			return false;
		}
		//si le pion monte
		else if(oldX>x)
		{
			for(int i=x+1; i<oldX; i++)
			{
				if(map.getPion(i, y)!=null) return true;
			}
			return false;
		}
		//si le pion va à gauche
		else if(y<oldY)
		{
			for(int i=y+1; i<oldY; i++)
			{
				if(map.getPion(x, i)!=null) return true;
			}
			return false;
		}
		//si le pion va à droite
		else if(oldY<y)
		{
			for(int i=oldY+1; i<y; i++)
			{
				if(map.getPion(x, i)!=null) return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * @param oldX, oldY, x, y, joueur
	 * @return 	renvoit true si le pion de la case (oldX,oldY) peut être déplacer sur la case (x,y)
	 * 			Attention: 	Si la case sur laquelle on souhaite déplacer son pion est occupée par un 
	 * 						pion adverse, cette methode renvoit true.
	 */
	public boolean canMoveOnNewCase(int oldX, int oldY, int x, int y, boolean joueur)
	{
		Pion attaquant = map.getPion(oldX,oldY);
		if(attaquant==null)	//verification "inutile", mais on est jamais trop prudent.
		{
			return false;
		}
		//Si on tente de déplacer un pion qui ne nous appartient pas.
		else if(attaquant.getTeam()!=joueur)
		{
			return false;
		}
		else
		{
			byte nbrPas = attaquant.getNbrDePas();
			
							//Soit un déplacement verticale (donc les x)
			if(logicalXOR(((0<oldX-x && oldX-x<=nbrPas) || (0<x-oldX && x-oldX<=nbrPas)), 
							//Soit un déplacement horizontale (donc les y)
					((0<oldY-y && oldY-y<=nbrPas) || (0<y-oldY && y-oldY<=nbrPas))))
			{
				if(oldX!=x && oldY!=y)
				{
					return false;
				}
				//Verifie s'il il n'y as pas de pion sur le trajet, lorsque la distance parcourue est différente de 1.
				if(nbrPas!=1 && checkObstacleOnWay(oldX, oldY, x, y, nbrPas))
				{
					return false;
				}
				Pion defenseur = map.getPion(x, y);
				if(defenseur==null)
				{
					return true;
				}
				//si blackout
				if(defenseur.getName().equals("blackout"))
				{
					return false;
				}
				//deplacer un pion sur un pion adverse.
				else if(defenseur.getTeam() != attaquant.getTeam())
				{
					return true;
				}
				//tenter de deplacer un pion sur un autre pion qui nous appartient, ou sur une case interdite.
				else
				{
					return false;
				}
			}
			else return false;
		}
	}
	
	/**
	 * @param x, y, joueur
	 * @return 	renvoit true si le pion à la position (x,y) peut etre deplacer sur une autre case 
	 * 			(gauche, droite, haut, ou bas), false sinon.
	 */
	public boolean canMove(int x, int y, boolean joueur)
	{
		for(int i=1; i<=map.getPion(x, y).getNbrDePas(); i++)
		{
			if( (x-i>=0) && canMoveOnNewCase(x, y, x-i, y, joueur)) return true;
			else if( (x+i<10) && canMoveOnNewCase(x, y, x+i, y, joueur)) return true;
			else if( (y-i>=0) && canMoveOnNewCase(x, y, x, y-i, joueur)) return true;
			else if( (y+i<10) && canMoveOnNewCase(x, y, x, y+i, joueur)) return true;
		}
		return false;
	}
	
	/**
	 * Renvoit la liste des cases sur lesquels un pion peut se déplacer.
	 * @param x
	 * @param y
	 * @param joueur
	 * @return
	 */
	public ArrayList<Integer> whichCaseCanGo(int x, int y, boolean joueur)
	{
		ArrayList<Integer> listCase = new ArrayList<Integer>();
		for(int i=1; i<=map.getPion(x, y).getNbrDePas(); i++)
		{
			if( (x-i>=0) && canMoveOnNewCase(x, y, x-i, y, joueur)) listCase.add(((x-i)*10)+y);
			if( (x+i<10) && canMoveOnNewCase(x, y, x+i, y, joueur)) listCase.add(((x+i)*10)+y);
			if( (y-i>=0) && canMoveOnNewCase(x, y, x, y-i, joueur)) listCase.add((x*10)+y-i);
			if( (y+i<10) && canMoveOnNewCase(x, y, x, y+i, joueur)) listCase.add((x*10)+y-i);
		}
		return listCase;
	}
	
	/**
	 * @param Pion1 et Pion2
	 * @return implémentation d'un combat entre pions sur la carte.
	 * 			renvoit 0 si les deux pions meurt
	 * 			renvoit 1 si le pion attaquant meurt
	 * 			ou renvoit 2 si le pion defenseur meurt.
	 */
	public int fight(Pion P1 , Pion P2)
	{
		int result = Fight.fightResult[P1.getForce()][P2.getForce()];
		
		if ( result == 0)
		{
			LostTrue.add(P1);
			LostFalse.add(P2);
			return result;
		}
		if ( result == 1 && P1.getTeam() ) 
		{
			LostTrue.add(P1);
		}
		if ( result == 2 && P2.getTeam() ) 
		{
			LostTrue.add(P2);
		}
		
		if ( result == 1 && P2.getTeam() ) 
		{
			LostFalse.add(P1);
		}
		
		if ( result == 2 && P1.getTeam() ) 
		{
			LostFalse.add(P2);
		}
		
		return result;
	}

	/**
	 * place le pion au coordonné (oldX, oldY) au position (x,y) sur la carte
	 * @param oldX
	 * @param oldY
	 * @param x
	 * @param y
	 */
	public void placePion(int oldX, int oldY, int x, int y) 
	{
		map.setEtat(x, y, map.getPion(oldX, oldY));
		map.setEtat(oldX, oldY, null);
	}

	/**
	 * cree le pion correspondant au nom donné en parametre, et l'associe au joueur correspondant
	 * @param pionPath
	 * @param joueur
	 * @return
	 */
	private Pion getTypePion(String pionPath, boolean joueur)
	{
		Joueur player;
		if(joueur) player = J1;
		else player = J2;
		if(pionPath.contains("bombe")) return new Bombe(joueur, player);
		if(pionPath.contains("drapeau")) return new Drapeau(joueur, player);
		if(pionPath.contains("espion")) return new Espion(joueur, player);
		if(pionPath.contains("colonel")) return new Colonel(joueur, player);
		if(pionPath.contains("capitaine")) return new Capitaine(joueur, player);
		if(pionPath.contains("commandant")) return new Commandant(joueur, player);
		if(pionPath.contains("demineur")) return new Demineur(joueur, player);
		if(pionPath.contains("eclaireur")) return new Eclaireur(joueur, player);
		if(pionPath.contains("espion")) return new Espion(joueur, player);
		if(pionPath.contains("general")) return new General(joueur, player);
		if(pionPath.contains("lieutenant")) return new Lieutenant(joueur, player);
		if(pionPath.contains("marechal")) return new Marechal(joueur, player);
		if(pionPath.contains("sergent")) return new Sergent(joueur, player);
		else return null;
	}

	/**
	 * 
	 * @param pionPath
	 * @return renvoit true si on peut placer le pion desirer, en fonction de si on en as encore à placer.
	 */
	public boolean checkNumberOfPion(String pionPath, int min, int max) 
	{
		int count = 0; int nbrPion = -1;
		for(int i = min; i<=max; i++)
		{
			for(int j = 0; j<10; j++)
			{
				if(map.getPion(i, j)!=null && pionPath.contains(map.getPion(i, j).getName()))
				{
					count++;
					if(nbrPion==-1) nbrPion = map.getPion(i, j).getNombre();
				}
			}
		}
		return nbrPion!=count;
	}
	
	/**
	 * pre:
	 * post: retourne le nombre de pion qui peuvent encore être placé par ce joueur et ce pion.
	 */
	public int checkNumberOfPion(String pionPath , boolean Tour) 
	{
		int count = 0; int nbrPion;
		int min ; int max;
		Pion pion = getTypePion(pionPath , true);
		
		nbrPion = pion.getNombre();
		
		if (Tour)
		{
			min = 6;
			max = 9;
		}
		else
		{
			min = 0;
			max = 3;
		}
		
		
		for(int i = min; i<=max; i++)
		{
			for(int j = 0; j<10; j++)
			{
				if(map.getPion(i, j)!=null && pionPath.contains(map.getPion(i, j).getName()))
				{
					count++;
				}
			}
		}
		return nbrPion - count;
	}

	/**
	 * crée et place un pion sur la carte
	 * @param pionPath
	 * @param x
	 * @param y
	 * @param joueur
	 */
	public void createAndPlacePion(String pionPath, int x, int y, boolean joueur) 
	{
		Pion pion = getTypePion(pionPath, joueur);
		map.setEtat(x, y, pion);
	}

	/**
	 * @param oldX, oldY, x, y
	 * @return 	renvoit le resultat d'un eventuel combat lors du deplacement d'un pion
	 * 			renvoit 10 si la case de destination etait inocupee.
	 *			renvoit 0 si les deux pions meurt 
	 * 			renvoit 1 si le pion attaquant meurt
	 * 			ou renvoit 2 si le pion defenseur meurt.
	 */
	public int checkNewCase(int oldX, int oldY, int x, int y)
	{
		Pion attaquant = map.getPion(oldX,oldY);
		Pion defenseur = map.getPion(x, y);
		//Si la case sur laquelle on veut placer le pion est vide.
		if(defenseur==null) return 10;
		else return fight(attaquant, defenseur);
	}

	/**
	 * retir un pion de la carte.
	 * @param x
	 * @param y
	 */
	public void removePion(int x, int y) 
	{
		map.resetPosition(x, y);
	}
	
	/**
	 * @param joueur
	 * @return 	renvoit true si le joueur a perdu parce qu'il ne sait plus déplacer un seul pion
	 * 			false sinon.
	 */
	public boolean checkLost(boolean joueur)
	{
		boolean lost = true;
		for(int i = 0; lost && i<10; i++)
		{
			for(int j = 0; lost && j<10; j++)
			{
				if(map.getPion(i, j)!=null && map.getPion(i, j).getTeam()==joueur)
				{
					if(canMove(i, j, joueur)) lost=false;
				}
			}
		}
		return lost;
	}

	
	/**
	 * @param min, max
	 * @return 	Regarde si le joueur a bien placé tous ses pions.
	 * 			Renvoit true si il a placé tous ses pions, false sinon.
	 */
	public boolean checkHaveAllPionsPlaced(int min, int max)
	{
		for(int i=min; i<=max; i++)
		{
			for(int j=0; j<10;j++)
			{
				if(map.getPion(i, j)==null) return false;
			}
		}
		return true;
	}
	
	
	
	
	
	/**
	 * @pre La matrice doit etre carrée.
	 * @param matrix
	 * @return renvoit la matrice inversé : (0,0) = (max, max).
	 */
	public void reverse(Pion [] [] matrix)
	{
		Pion temp=null; int maxI, maxY=matrix.length;
		if(matrix.length%2==0) maxI=matrix.length/2;
		else maxI=(matrix.length/2);
		
		for(int i=0; i<=maxI; i++)
		{
			for(int j=0; j<maxY; j++)
			{
				temp = matrix[(matrix.length-1)-i][(matrix.length-1)-j];
				matrix[(matrix.length-1)-i][(matrix.length-1)-j] = matrix [i][j];
				matrix [i][j] = temp;
				if(i==maxI) maxY=maxI;
			}
		}
	}
	
	/**
	 * initialise la liste des pions à placer sur le terrain de jeu.
	 * @param team
	 * @param isAnIA
	 * @return
	 */
	public ArrayList<Pion> initializePions(boolean team, boolean isAnIA)
	{
		int i; ArrayList<Pion> listPion = new ArrayList<Pion>(); Joueur J;
		if(team) J = J1;
		else J = J2;
		listPion.add(new Drapeau(team, J));
		for(i=1; i<7; i++)
		{
			listPion.add(new Bombe(team, J));
		}
		listPion.add(new Espion(team, J));
		for(i=8; i<16; i++)
		{
			listPion.add(new Eclaireur(team, J));
		}
		for(i=16; i<21; i++)
		{
			listPion.add(new Demineur(team, J));
		}
		for(i=21; i<25; i++)
		{
			listPion.add(new Sergent(team, J));
		}
		for(i=25; i<29; i++)
		{
			listPion.add(new Lieutenant(team, J));
		}
		for(i=29; i<33; i++)
		{
			listPion.add(new Capitaine(team, J));
		}
		for(i=33; i<36; i++)
		{
			listPion.add(new Commandant(team, J));
		}
		for(i=36; i<38; i++)
		{
			listPion.add(new Colonel(team, J));
		}
		listPion.add(new General(team, J));
		listPion.add(new Marechal(team, J));
		
		if(!team && isAnIA){
			for(Pion pion: listPion){
				pion.setVisibleByIA(true);
			}
		}
		return listPion;
	}
	
	/**
	 * 
	 * @param name
	 * @return renvoit le pion et le retir de la liste des pions si il y en a encore un, renvoit null sinon, et ne
	 * touche pas à la liste des pions.
	 */
	protected Pion getPion(ArrayList<Pion> listPion, String name)
	{
		for(Pion i : listPion)
		{
			if(i.getName().equals(name))
			{
				listPion.remove(i);
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
		if(x>=0 && x<10 && y>=0 && y<10) return map.getPion(x, y)==null;
		else return false;
	}
	
	/**
	 * place le drapeau avec des bombes autour
	 * @param listPion
	 */
	protected void setDrapeau(ArrayList<Pion> listPion)
	{
		int t; Random r = new Random();
		t = r.nextInt(30);
		if(t==20 || t==21 || t==24 || t==25 || t==28 || t==29) setDrapeau(listPion);
		else
		{
			map.setEtat(t/10, t%10, getPion(listPion, "drapeau"));
			//une bombe à gauche du drapeau
			if(isNothingOnCase(t/10,t%10-1)) map.setEtat(t/10, t%10-1, getPion(listPion, "bombe"));
			//une bombe en haut du drapeau
			if(isNothingOnCase(t/10-1,t%10)) map.setEtat(t/10-1, t%10, getPion(listPion, "bombe"));
			//une bombe à droite du drapeau
			if(isNothingOnCase(t/10,t%10+1)) map.setEtat(t/10, t%10+1, getPion(listPion, "bombe"));
			//une bombe en bas du drapeau
			if(isNothingOnCase(t/10+1,t%10)) map.setEtat(t/10+1, t%10, getPion(listPion, "bombe"));
		}
	}
	
	/**
	 * place les eclaireurs
	 * @param listPion
	 */
	protected void setEclaireur(ArrayList<Pion> listPion)
	{
		map.setEtat(3, 0, getPion(listPion, "eclaireur"));
		map.setEtat(3, 1, getPion(listPion, "eclaireur"));
		map.setEtat(3, 4, getPion(listPion, "eclaireur"));
		map.setEtat(3, 5, getPion(listPion, "eclaireur"));
		map.setEtat(3, 8, getPion(listPion, "eclaireur"));
		map.setEtat(3, 9, getPion(listPion, "eclaireur"));
		Pion temp = getPion(listPion, "eclaireur");
		int t; Random r = new Random();
		while(temp!=null)
		{
			t = r.nextInt(10);
			if(isNothingOnCase(2, t))
			{
				map.setEtat(2, t, temp);
				temp = getPion(listPion, "eclaireur");
			}
				
		}
	}
	
	/**
	 * place les bombes comme si elle protegeait le drapeau, sauf qu'elle protège un sergent.
	 * @param listPion
	 */
	protected void setBombe(ArrayList<Pion> listPion)
	{
		int t; Random r = new Random();
		t = r.nextInt(20);
		if(isNothingOnCase(t/10+1, t%10) && isNothingOnCase(t/10, t%10-1) && isNothingOnCase(t/10, t%10+1))
		{
			map.setEtat(t/10+1, t%10, getPion(listPion, "bombe"));
			if(t%10<5)
			{
				//Si la case designé par t est plus à gauche, je commence par mettre à droite
				map.setEtat(t/10, t%10+1, getPion(listPion, "bombe"));
				if(isNothingOnCase(t/10, t%10-1)) map.setEtat(t/10, t%10-1, getPion(listPion, "bombe"));
			}
			else
			{
				//Si la case designé par t est plus à droite, je commence par mettre à gauche
				map.setEtat(t/10, t%10-1, getPion(listPion, "bombe"));
				if(isNothingOnCase(t/10, t%10+1)) map.setEtat(t/10, t%10+1, getPion(listPion, "bombe"));
			}
			if(isNothingOnCase(t/10, t%10)) map.setEtat(t/10, t%10, getPion(listPion, "sergent"));
				
				
		}
		else setBombe(listPion);
		Pion temp = getPion(listPion, "bombe");
		while(temp!=null)
		{
			t = r.nextInt(40);
			if(isNothingOnCase(t/10, t%10))
			{
				map.setEtat(t/10, t%10, temp);
				temp = getPion(listPion, "bombe");
			}
				
		}
	}
	
	/**
	 * place le maréchal et l'espion
	 * @param listPion
	 */
	protected void setEspionAndMarechal(ArrayList<Pion> listPion)
	{
		int t; Random r = new Random();
		t = r.nextInt(30);
		if(isNothingOnCase(t/10, t%10) && 
				(isNothingOnCase(t/10-1, t%10) || isNothingOnCase(t/10, t%10-1) || isNothingOnCase(t/10, t%10+1)))
		{
			map.setEtat(t/10, t%10, getPion(listPion, "marechal"));
			if(isNothingOnCase(t/10-1, t%10)) map.setEtat(t/10-1, t%10, getPion(listPion, "espion"));
			else if(isNothingOnCase(t/10, t%10-1)) map.setEtat(t/10, t%10-1, getPion(listPion, "espion"));
			else map.setEtat(t/10, t%10+1, getPion(listPion, "espion"));
		}
		else setEspionAndMarechal(listPion);
	}
	
	/**
	 * place les démineurs
	 * @param listPion
	 */
	protected void setDemineur(ArrayList<Pion> listPion)
	{
		int t, i=0; Random r = new Random();
		Pion temp = getPion(listPion, "demineur");
		while(i<3)
		{
			t = r.nextInt(20);
			if(isNothingOnCase(t/10, t%10))
			{
				map.setEtat(t/10, t%10, temp);
				temp = getPion(listPion, "demineur");
				i++;
			}	
		}
		while(temp!=null)
		{
			t = 20+r.nextInt(20);
			if(isNothingOnCase(t/10, t%10))
			{
				map.setEtat(t/10, t%10, temp);
				temp = getPion(listPion, "demineur");
			}	
		}
	}
	
	/**
	 * place les sergents
	 * @param listPion
	 */
	protected void setSergent(ArrayList<Pion> listPion)
	{
		int t; Random r = new Random();
		Pion temp = getPion(listPion, "sergent");
		while(temp!=null)
		{
			t = r.nextInt(40);
			if(isNothingOnCase(t/10, t%10))
			{
				map.setEtat(t/10, t%10, temp);
				temp = getPion(listPion, "sergent");
			}	
		}
	}
	
	/**
	 * place les lieutenants
	 * @param listPion
	 */
	protected void setLieutenant(ArrayList<Pion> listPion)
	{
		int t; Random r = new Random();
		Pion temp = getPion(listPion, "lieutenant");
		while(temp!=null)
		{
			t = r.nextInt(40);
			if(isNothingOnCase(t/10, t%10))
			{
				map.setEtat(t/10, t%10, temp);
				temp = getPion(listPion, "lieutenant");
			}	
		}
	}
	
	/**
	 * place les autres pions
	 * @param listPion
	 */
	protected void setOthersPions(ArrayList<Pion> listPion)
	{
		int t, place; Random r = new Random();
		while(listPion.size()!=0)
		{
			t = r.nextInt(listPion.size());
			place = r.nextInt(40);
			if(isNothingOnCase(place/10, place%10))
			{
				map.setEtat(place/10, place%10, listPion.get(t));
				listPion.remove(t);
			}
		}
	}
	
	/**
	 * méthode qui place automatiquement les pions du joueurs.
	 * @param joueur
	 * @param isAnIA
	 */
	public void dude(boolean joueur, boolean isAnIA)
	{
		ArrayList<Pion> listPion = initializePions(joueur, isAnIA);
		
		if(joueur){
			reverse(map.getMap());
		}
		
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<10; j++)
			{
				removePion(i, j);
			}
		}
		
		setDrapeau(listPion);
		setEclaireur(listPion);
		setBombe(listPion);
		setEspionAndMarechal(listPion);
		setDemineur(listPion);
		setSergent(listPion);
		setLieutenant(listPion);
		setOthersPions(listPion);
		
		if(joueur){
			reverse(map.getMap());
		}
	}
	
	
}
