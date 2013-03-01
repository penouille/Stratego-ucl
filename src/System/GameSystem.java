package System;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class GameSystem {
	
	
	public String getPath()
	{
		String path = "/" + this.getClass().getName().replace('.', '/') + ".class";
	    URL url = getClass().getResource(path);
	    try {
			path = URLDecoder.decode(url.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	    // suppression de  la classe ou du jar du path de l'url
	    int index = path.lastIndexOf("/");
	    path = path.substring(0, index);
	    //Supprime du path le dossier dans lequelle se trouve la classe
	    index = path.lastIndexOf("/");
	    path = path.substring(0, index);
	 
	    if (path.startsWith("jar:file:"))
	    {
	      // suppression de jar:file: de l'url d'un jar
	      // ainsi que du path de la classe dans le jar
	      index = path.indexOf("!");
	      path = path.substring(9, index);
	    }
	    else
	    {
	      // suppresion du file: de l'url si c'est une classe en dehors d'un jar
	      // et suppression du path du package si il est présent.
	      path = path.substring(5, path.length());
	      Package pack = getClass().getPackage();
	      if (null != pack)
	      {
	        String packPath = pack.toString().replace('.', '/');
	        if (path.endsWith(packPath))
	        {
	          path = path.substring(0, (path.length() - packPath.length()));
	        }
	      }
	    }
	 
	    System.out.println("Répertoire contenant la classe: " + path);
	    return path;
		
	}
	
	
	public static void main(String [] thePowerOfTheStratego)
	{
		
	}

}
