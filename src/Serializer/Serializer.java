package Serializer;
import java.io.*;

/**
 * Cette classe Serializer contient des m�thodes permettant d'enregistrer et
 * de charger un objet sur un fichier.
 * et invers�ment.
 *  
 * @auteur       Faingnaert Florian et Quentin Meyers.
 * @creation     30/04/2013
 * 
 */
public class Serializer {
	
	/**
	 * 
	 * @param Repository : l'objet � sauvegarder.
	 * @param Path : nom du fichier o� l'objet sera sauvegard�.
	 * 
	 * Enregistre l'�tat d'un objet dans un fichier texte.
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
	 * @param Path nom du fichier � lire
	 * 
	 * Cr�e un objet � partir du fichier texte indiqu�.
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