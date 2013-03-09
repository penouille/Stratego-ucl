package View;


import java.net.URL;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;




public class CurrentGame extends BasicGameState implements InputProviderListener 
{
		
		private ArrayList<MouseOverArea> Echequier = new ArrayList<MouseOverArea>();
		private ArrayList<MouseOverArea> Force = new ArrayList<MouseOverArea>();
		private MouseOverArea Fin ;
	   
		private Command menu = new BasicCommand("menu");
		/** The input provider abstracting input */
       
		private InputProvider provider;
		/** The message to be displayed */
       
		private Image prise = null;// représente la prise d'un pion (une image suit le curseur.
		private boolean placement = true; // représente le fait de pouvoir placer ses pions en début de partie.
       	private String[] photos =  {"drapeau.jpg" ,"espion.jpg" , "eclaireur.jpg"  , "demineur.jpg",
    		                       "sergent.jpg" , "lieutenant.jpg", "capitaine.jpg", "commandant.jpg" ,
    		                       "colonel.jpg" ,"general.jpg", "marechal.jpg" , "bombe.jpg"};
		
       
       	private boolean tour = true;
       
       	/**
       	 * Create a new image rendering test
       	 */
       	public CurrentGame() 
       	{
       		super();
       	}
       
       	public int getID() 
       	{
       		return 0;
       	}
       
       	/**
       	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
       	 */
       	public void init(GameContainer container,StateBasedGame game) throws SlickException 
       	{
       		AdminGame.Tour= true;
    	   	   
       		provider = new InputProvider(container.getInput());
       		provider.addListener(this);
       		
       		provider.bindCommand(new KeyControl(Input.KEY_M), menu);
       		
       		Fin = new MouseOverArea(container , new Image("vert.jpg") , 810 , 450);
       		Fin.setNormalColor(new Color(1f,1f,1f,0.7f));
       		Fin.setMouseOverColor(new Color(0.5f,0.5f,0.5f,3f));
       		
       		// Mise en place de l'échequier.
       		for ( int i = 0 ; i < 10 ; i++)
       		{
       			for ( int j = 0 ; j < 10 ; j++)
       			{
       				Echequier.add( new MouseOverArea( container, new Image("vert.jpg") ,55*j+10 , 70*i+10 , 44 , 62 ));
            	   }		
       		}
               
       		//premier bloc de flotte.
       		Echequier.set(42, new MouseOverArea (container , new Image("bleu.jpg") , 120 , 290));
       		Echequier.set(43, new MouseOverArea (container , new Image("bleu.jpg") , 175 , 290));
       		Echequier.set(52, new MouseOverArea (container , new Image("bleu.jpg") , 120 , 360));
       		Echequier.set(53, new MouseOverArea (container , new Image("bleu.jpg") , 175 , 360));
       			
       		//deuxième bloc de flotte.
       		Echequier.set(46, new MouseOverArea (container , new Image("bleu.jpg") , 340 , 290));
       		Echequier.set(47, new MouseOverArea (container , new Image("bleu.jpg") , 395 , 290));
       		Echequier.set(56, new MouseOverArea (container , new Image("bleu.jpg") , 340 , 360));
       		Echequier.set(57, new MouseOverArea (container , new Image("bleu.jpg") , 395 , 360));
       		
       		for ( int i = 0 ; Echequier.size() > i ; i++)
       		{
       			Echequier.get(i).setNormalColor(new Color(1f,1f,1f,0.7f));
       			Echequier.get(i).setMouseOverColor(new Color(0.5f,0.5f,0.5f,3f));
       		}	
               	
       		force(container);
               
       	}
       
       
       /**
        * 
        * Crée les mouseoverarea a partir desquelles on pourra placer ses pions en début de partie
        */
       	public void force ( GameContainer container ) throws SlickException 
       	{
       		for ( int i = 0 ; i < 6 ; i++)
       		{
       			Force.add(new MouseOverArea ( container , new Image (photos[i]), 810 , 10+70*i ));
       			Force.get(i).setNormalColor(new Color(1f,1f,1f,0.7f));
       		}
    	   
       		for ( int i = 0 ; i < 6 ; i++)
       		{
       			Force.add(new MouseOverArea ( container , new Image (photos[i+6]), 870 , 10+70*i ));
       			Force.get(i+6).setNormalColor(new Color(1f,1f,1f,0.7f));
       		}
       	}
       
       
       
       
       /**
        * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
        */
       public void render(GameContainer container,StateBasedGame game, Graphics g) throws SlickException 
       {
    	  
           	   //affichage de l'échaquier
               for ( int i = 0 ; i < Echequier.size() ; i++)
               {
            	   Echequier.get(i).render(container, g);
               }
               
               //affichage des pions disponibles.
               for ( int i = 0 ; i < Force.size() ; i++)
               {
            	   Force.get(i).render(container , g);
               }
               
               Fin.render(container, g);
               
               if ( prise != null)
               {
            	   prise.drawCentered(container.getInput().getMouseX(), container.getInput().getMouseY());
               }
       }
       
       /**
        * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
        */
       
       public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException 
       {
    	   
    	   Input input = container.getInput();
    	   
    	   //code relatif au placement des pions en début de partie.
    	   if (  input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
    	   {
    		   if ( placement)
    		   {
    			   
    			   for (int i = 0 ; i < Force.size() ; i++)
    			   {
    				   if ( Force.get(i).isMouseOver() )
    				   {
    					   prise = new Image (photos[i]);
    				   }
    			   }
    		   
    			   for (int i = 0 ;  i < Echequier.size()  ; i++)
    			   {
    				   
    				   if ( Echequier.get(i).isMouseOver() && prise != null)
			   		   {
    					   if( !(i == 42 || i == 43 || i == 46 || i == 47 || i == 52 || i == 53 || i == 56 || i == 57))
        				   {
    						   Echequier.set(i , new MouseOverArea( container, prise , Echequier.get(i).getX(), Echequier.get(i).getY()));

			   					prise = null;
        				   }
			   			}
    				   
    			   }
    		   
    		   }
    		   
    		   if ( placement && Fin.isMouseOver())
    		   {
    			   placement = false;
    		   }
    		   
    		   if (!placement && tour)
    		   {
    			   for (int i = 0 ; i < Echequier.size() ; i++)
    			   {
			   			if ( Echequier.get(i).isMouseOver())
			   				{
			   					if ( prise == null)
			   					{
			   						// à faire après le controller.
			   					}
			   					else
			   					{
			   						Echequier.set(i , new MouseOverArea( container, prise , Echequier.get(i).getX(), Echequier.get(i).getY()));
			   					}
			   				}
			   		}
    		   }
    	 
    	   }
    		   
    		   
    	   if ( input.isKeyPressed(Input.KEY_T))
    	   {
    		   AdminGame.Tour = true;
    		   
    		   game.enterState(2,new  FadeOutTransition() , new FadeInTransition()) ;
    	   }
    	   
    	   if ( input.isKeyPressed(Input.KEY_ESCAPE))
    	   {
    		   container.exit();
    	   }
    	   
    	   
       }
       
       
       
       
       

       /**
        * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
        */
       public void controlPressed(Command command) 
       {
               if ( command.equals(menu))
               {
               		 new Option();
               }
       }

       /**
        * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
        */
       public void controlReleased(Command command) 
       {
       		
       }
       
 }