package Pion;

/**
 * 
 * 
 * @author Florian
 *
 */
public class Fight 
{
	
	// 0 : tt le monde meurt
	// 1 : l'attaquant meurt
	// 2 : le d�fenseur meurt
	// la premi�re entr�e repr�sente l'attaquant et la seconde le d�fenseur.
	public static final int[][] fightResult = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //drapeaux
				                               {2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 }, //espion
		                                       {2, 2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //�claireur
		                                       {2, 2, 2, 0, 1, 1, 1, 1, 1, 1, 1, 2 }, //d�mineur 
		                                       {2, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1, 1 }, //sergent
		                                       {2, 2, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1 }, //lieutenant
		                                       {2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 1, 1 }, //capitaine
		                                       {2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 1 }, //commandant
		                                       {2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1 }, //colonel
		                                       {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1 }, //g�n�ral
		                                       {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 }, //mar�chal
		                                       {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }}; //bombe
}
