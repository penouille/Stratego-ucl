package Game;

import java.io.Serializable;
import java.util.ArrayList;

import Pion.Pion;
import Serializer.Serializer;

public class Score implements Serializable
{
	private static ArrayList<String> Score;
	
	private Score()
	{}
	
	public static ArrayList<String> getScore() {
		return Score;
	}
	
	public static void setScore(ArrayList<String> score) {
		Score = score;
	}
	
	public static int calculateScore (ArrayList<Pion> Lost, boolean win, boolean matchNull)
	{
		int counter = 0;
		
		for (int i = 0 ; i<Lost.size() ; i++)
		{
			counter += Lost.get(i).getForce();
		}
		if(win && !matchNull) counter+=1000;
		return counter;
	}
	
	/**
	 * Ajoute un score à la liste des scores.
	 * @param game
	 * @param gagnant
	 * @param matchNull
	 */
	public static void AddScore(Game game, boolean gagnant, boolean matchNull)
	{
		setScore(ReadScore());
		Score.add(game.getJ1().getPseudo()+"-"+calculateScore(game.getLostTrue(), gagnant==true, matchNull)+
				"-"+game.getJ2().getPseudo()+"-"+calculateScore(game.getLostFalse(), gagnant==false, matchNull)+"-");
		SaveScore();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * lis les score dans le fichier texte spécifié
	 * @return la liste des scores sauvegardé.
	 */
	public static ArrayList<String> ReadScore()
	{
		return (ArrayList<String>) Serializer.Deserializer("Score_Stratego.txt");
	}
	
	/**
	 * Sauvegarde la liste des scores.
	 */
	public static void SaveScore()
	{
		Serializer.saveObject( Score , "Score_Stratego.txt");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String [] toto){
		Score = new ArrayList<String>();
		Score.add("Penouille-26-Vineuvall-1014-");
		
		for(int i=0; i!=100; i++){
			Score.add("Unknown-1000-IA Kikoo-39-");
		}
		SaveScore();
	}
	
}
