package View;


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
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;




public class CurrentGame extends BasicGameState implements InputProviderListener 
{
		
	
	   private ArrayList<MouseOverArea> Echequier = new ArrayList<MouseOverArea>();
	   private ArrayList<MouseOverArea> Force = new ArrayList<MouseOverArea>();
	   
       private Command menu = new BasicCommand("menu");
       private Command echap = new BasicCommand("echap");
       /** The input provider abstracting input */
       
       private InputProvider provider;
       /** The message to be displayed */
       
       private String message = "";
       
       /**
        * Create a new image rendering test
        */
       public CurrentGame() {
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
               provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), echap);
               
               
               for ( int i = 0 ; i < 8 ; i++)
               {
            	   for ( int j = 0 ; j < 10 ; j++)
            	   {
            		   Echequier.add(0, new MouseOverArea( container, new Image("Vert.jpg") ,90*j+5 , 90*i+5 ));
            	   }
               }
               
               force(container);
               
               Echequier.get(79).setNormalImage(new Image("bombe.jpg"));
               Echequier.get(79).setMouseOverImage(new Image("bombe.jpg"));
               Echequier.get(79) ;
               
               for ( int i = 0 ; Echequier.size() > i ; i++)
               {
            	   Echequier.get(i).setNormalColor(new Color(1f,1f,1f,0.7f));
            	   Echequier.get(i).setMouseOverColor(new Color(0.5f,0.5f,0.5f,3f));
               }
               
       }
       
       public void force ( GameContainer container ) throws SlickException 
       {
    	   
    	   Force.add(new MouseOverArea ( container , new Image ("drapeau.jpg"), 910 , 5));
    	   Force.get(0).setNormalColor(new Color(1f,1f,1f,0.7f));
    	   
       }
       /**
        * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
        */
       public void render(GameContainer container,StateBasedGame game, Graphics g) 
       {
               for ( int i = 0 ; i < 80 ; i++)
               {
            	   Echequier.get(i).render(container, g);
               }
               
               Force.get(0).render(container , g);
               
       }

       /**
        * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
        */
       public void update(GameContainer container,StateBasedGame game, int delta) 
       {
    	   if ( container.getInput().isKeyPressed(Input.KEY_T))
    	   {
    		   AdminGame.Tour = true;
    		   
    		   game.enterState(2,new  FadeOutTransition() , new FadeInTransition()) ;
    	   }
    	   
    	   if ( container.getInput().isKeyPressed(Input.KEY_ESCAPE))
    	   {
    		   container.exit();
    	   }
       }

       /**
        * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
        */
       public void controlPressed(Command command) {
               if ( command.equals(menu))
               {
               	new Start();
               }
       }

       /**
        * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
        */
       public void controlReleased(Command command) 
       {
       		
       }
       
 }