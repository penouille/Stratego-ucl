package Serializer;
import java.io.*;

/**
 * Cette classe Serializer contient des méthodes permettant d'enregistrer et
 * de charger un objet sur un fichier.
 * et inversément.
 *  
 * @auteur       Faingnaert Florian et Quentin Meyers.
 * @creation     30/04/2013
 * 
 */
public class Serializer {
	
	/**
	 * 
	 * @param Repository : l'objet à sauvegarder.
	 * @param Path : nom du fichier où l'objet sera sauvegardé.
	 * 
	 * Enregistre l'état d'un objet dans un fichier texte.
	 */
	
	//TODO Pas de path String -> URL
	
	public static void saveObject( Object  Score , String Path )
	{
		
		try 
		{
			ObjectOutputStream Output = new ObjectOutputStream(new FileOutputStream(Path));
			Output.writeObject(Score);
			Output.flush();
			Output.close();
		}
	
		catch (java.io.IOException e) 
		{
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 
	 * @param Path nom du fichier à lire
	 * 
	 * Crée un objet à partir du fichier texte indiqué.
	 */
	public static Object Deserializer( String Path ) 
	{
		Object Score = null;
			
		try 
		{
			ObjectInputStream Input = new ObjectInputStream(new FileInputStream(Path));
			Score = Input.readObject();
			Input.close();
		}
		catch (java.io.IOException e) 
		{
			e.printStackTrace();	
		}
		
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
			
		return Score;
	}
		
}