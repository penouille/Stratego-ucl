package Game;

import java.io.Serializable;
import java.util.ArrayList;

import Pion.Pion;
import Serializer.Serializer;

import Controller.Controller;
import Intelligence.Artificielle;
import Intelligence.Joueur;

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
	
	public static int calculateScore (ArrayList<Pion> Lost)
	{
		int counter = 0;
		
		for (int i = 0 ; i<Lost.size() ; i++)
		{
			counter += Lost.get(i).getForce();
		}
		
		return counter;
	}
	
	public static void AddScore(Joueur j1, Artificielle IA, boolean gagnant )
	{
		Score.add(j1.getPseudo()+"-"+Score(controller.getGame().getLostTrue())+
				"-"+IA.getForceIA()+"-"+Score(controller.getGame().getLostFalse())+"-"+controller.getGagnant()+"-");
	}
	
	public static void AddScore(Joueur joueur , Joueur joueur1, boolean gagnant)
	{
		Score.add(joueur.getPseudo()+"-"+Score(controller.getGame().getLostTrue())+
				"-"+joueur.getPseudo()+"-"+Score(controller.getGame().getLostFalse())+"-"+controller.getGagnant()+"-");
	}
	
	public static void ReadScore()
	{
		Serializer.Deserializer("data/Score.txt");
	}
	
	public static void SaveScore()
	{
		Serializer.saveObject( Score , "data/Score.txt");
	}
	
}
