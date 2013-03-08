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
	// 2 : le défenseur meurt
	// la première entrée représente l'attaquant et la seconde le défenseur.
	public static final int[][] fightResult = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //drapeaux
				                               {2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 }, //espion
		                                       {2, 2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //éclaireur
		                                       {2, 2, 2, 0, 1, 1, 1, 1, 1, 1, 1, 2 }, //démineur 
		                                       {2, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1, 1 }, //sergent
		                                       {2, 2, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1 }, //lieutenant
		                                       {2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 1, 1 }, //capitaine
		                                       {2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 1 }, //commandant
		                                       {2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1 }, //colonel
		                                       {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1 }, //général
		                                       {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 }, //maréchal
		                                       {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }}; //bombe
}
