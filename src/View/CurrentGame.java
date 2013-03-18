package View;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Controller.Controller;
import Game.Game;




public class CurrentGame extends BasicGameState implements InputProviderListener 
{


	   private ArrayList<MouseOverArea> Echequier = new ArrayList<MouseOverArea>();
	   private ArrayList<MouseOverArea> Force = new ArrayList<MouseOverArea>();
	   private MouseOverArea Fin ;

	   private ParticleSystem particule = new ParticleSystem("drapeau.jpg");

       private Command menu = new BasicCommand("menu");
       /** The input provider abstracting input */
       
       private InputProvider provider;
       /** The message to be displayed */
       
       private Controller controller;
       private Game game;
       
       //private Image prise = null;// représente la prise d'un pion (une image suit le curseur.
       //private boolean placement = true; // représente le fait de pouvoir placer ses pions en début de partie.
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
           controller = new Controller();
           game = controller.getGame() ;
       }
       
       public CurrentGame(Controller controller)
       {
    	   super();
    	   this.controller = controller;
    	   game = controller.getGame();
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
               
               
               setFlotte(container);
               
               
               for ( int i = 0 ; Echequier.size() > i ; i++)
               {
            	   Echequier.get(i).setNormalColor(new Color(1f,1f,1f,0.7f));
            	   Echequier.get(i).setMouseOverColor(new Color(0.5f,0.5f,0.5f,3f));
               }
               
               force(container);
               
               try
               {
            	   File xmlFile = new File ("emitter.xml");
            	   ConfigurableEmitter emitter = ParticleIO.loadEmitter(xmlFile) ;
            	   particule.addEmitter(emitter);
            	   particule.setPosition(600, 600);
               }
               catch (Exception e)
               {
            	   System.out.println("particule error");
            	   
               }
               
               particule.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
       }
       
       
       
       
       
       /**
        * pre: inconnu ?
        * post: Affiche ce qu'on lui demande d'afficher.
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
               
               if ( controller.getPrise() != null)
               {
            	   new Image(controller.getPrise()).drawCentered(container.getInput().getMouseX(), container.getInput().getMouseY());
               }
               
               particule.render();
               
       }
       
       /**
        * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
        */
       
       public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException 
       {
    	   
    	   Input input = container.getInput();
    	   
    	   //UpGame2();
    	   
    	   //Code exécuté en cas de clic gauche avec la souris.
    	   if (  input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
    	   {
    		   if ( controller.getPlacement())
    		   {
    			   
    			   ChoixPion();
    		   
    			   setPion(container);
    			   
    		   
    		   }
    		   
    		   if ( controller.getPlacement() && Fin.isMouseOver())
    		   {
    			   //placement = false;
    		   }
    		   
    		   if (!controller.getPlacement() && tour)
    		   {
    			   setMove(container);
    		   }
    		   
    		   checkCase(controller.getLastClick());
			   checkCase(controller.getNewClick());
    	 
    	   }
    		   
    	   
    	 //Code exécuté en cas de pression sur la touche T.
    	   if ( input.isKeyPressed(Input.KEY_T))
    	   {
    		   AdminGame.Tour = true;
    		   
    		   game.enterState(2,new  FadeOutTransition() , new FadeInTransition()) ;
    	   }
    	   
    	   if ( input.isKeyPressed(Input.KEY_N))
    	   {
    		   dude(container);
    	   }
    	 //Code exécuté en cas de pression sur la touche Echap.
    	   if ( input.isKeyPressed(Input.KEY_ESCAPE))
    	   {
    		   container.exit();
    	   }
    	   
    	   
    	   //particule.update(delta);
    	  
       }
       
       
       
       public void UpGame2 () throws SlickException
       {
    	   for (int i = 0 ; i < 100 ; i++)
    	   {
    		   checkCase(i);
    	   }
       }
       
       public void checkCase(int i) throws SlickException
       {
    	   if ( controller.getGame().getMap().getPion(i/10,i%10) == null)
    	   {
    		   Echequier.get(i).setNormalImage(new Image ("vert.jpg"));
    		   Echequier.get(i).setMouseOverImage(new Image ("vert.jpg"));
    	   }
    	   else
    	   {
    		   Echequier.get(i).setNormalImage(new Image (controller.getGame().getMap().getPion(i/10,i%10).getPath()));
    		   Echequier.get(i).setMouseOverImage(new Image (controller.getGame().getMap().getPion(i/10,i%10).getPath()));
    	   }
       }
       
       /**
        * pre:
        * post: crée le tableau de sélection de pions à droite de l'écran.
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
        * pre: Echequier a été initialisé.
        * post: Crée mouseoverarea avec du bleau dans les case 42,43,46,47,52,53,56,57 de l'échequier.
        */
       public void setFlotte(GameContainer container ) throws SlickException 
       {
    	 //premier bloc de flotte.
           Echequier.set(42, new MouseOverArea (container , new Image("vert - Copie.jpg") , 120 , 290));
           Echequier.set(43, new MouseOverArea (container , new Image("vert - Copie.jpg") , 175 , 290));
           Echequier.set(52, new MouseOverArea (container , new Image("vert - Copie.jpg") , 120 , 360));
           Echequier.set(53, new MouseOverArea (container , new Image("vert - Copie.jpg") , 175 , 360));
           
           //deuxième bloc de flotte.
           Echequier.set(46, new MouseOverArea (container , new Image("vert - Copie.jpg") , 340 , 290));
           Echequier.set(47, new MouseOverArea (container , new Image("vert - Copie.jpg") , 395 , 290));
           Echequier.set(56, new MouseOverArea (container , new Image("vert - Copie.jpg") , 340 , 360));
           Echequier.set(57, new MouseOverArea (container , new Image("vert - Copie.jpg") , 395 , 360));
       }
       
       
       /**
        * pre:-
        * post: permet de sélectionner un pion parmi ceux proposer sur la droite
        */
       public void ChoixPion() throws SlickException 
	   {
		   for (int i = 0 ; i < Force.size() ; i++)
		   {
			   if ( Force.get(i).isMouseOver() )
			   {
				   controller.setPrise( photos[i] );
			   }
		   }
	   }
       
       /**
        * 
        * pre: 
        * post: Pose le pion sélectionné sur la case sélectionnée, annule la variable prise si c'est fait.
        * 		et modifie le model en conséquence.
        * Si aucun pion n'est sélectionné, en sélectionne un.
        */
       public void setPion(GameContainer container) throws SlickException
       {
    	   for (int i = 0 ;  i < Echequier.size()  ; i++)
		   {

			   if ( Echequier.get(i).isMouseOver() && controller.getPrise() != null)
	   		   {
				   if( !(i == 42 || i == 43 || i == 46 || i == 47 || i == 52 || i == 53 || i == 56 || i == 57))
				   {
					  // Echequier.set(i , new MouseOverArea( container, new Image (controller.getPrise()) , Echequier.get(i).getX(),
						//	   Echequier.get(i).getY()));


					  // game.placePion(controller.getPrise(), i/10, i%10);
					   controller.setClick(i);
					   //System.out.println(controller.getGame().getMap().getPion(i/10,i%10).getName()); //.getMap().getPion(i/10,i%10).getName());
					   controller.setPrise( null );
				   }
	   			}
			  
			}

       }
       
       /**
        * pre:-
        * post: prend le pion du plateau sélectionné en "prise" si prise est vide ou si !prise place prise à cette endroit.
        */
       public void setMove(GameContainer container) throws SlickException
       {
    	   for (int i = 0 ; i < Echequier.size() ; i++)
		   {
	   			if ( Echequier.get(i).isMouseOver())
	   				{
	   					if ( controller.getPrise() == null)
	   					{
	   						System.out.println(controller.getPrise());
	   						controller.setPrise( controller.getGame().getMap().getPion(i/10,i%10).getPath() );
	   						System.out.println(controller.getPrise());
	   					}
	   					else
	   					{
	   						if( !(i == 42 || i == 43 || i == 46 || i == 47 || i == 52 || i == 53 || i == 56 || i == 57))
	   					   {
	   							controller.setClick(i);
	   							controller.setPrise(null);
	   							checkCase(controller.getLastClick());
	   							checkCase(controller.getNewClick());
	   					   }
	   					}
	   				}
	   		}
       }

       /**
        * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
        */
       public void controlPressed(Command command) 
       {
               if ( command.equals(menu))
               {
               		// new Start();
            	   new Option();
               }
       }

       /**
        * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
        */
       public void controlReleased(Command command) 
       {
       		
       }
       
       /**
        * pre-
        * post: place les pions automatiquement en début de partie pr ne pas emmerder les programmeurs.( good guy method )
        */
       public void dude (GameContainer container) throws SlickException
       {
    	   for (int i = 0 ; i < 40 ; i++)
    	   {
    		   controller.setPrise(photos[i/4]);
    		   
    		   setPion(container);
    		   checkCase(i);
    	   }
    	   
    	   for (int i = 60 ; i < 100 ; i++)
    	   {
    		   controller.setPrise(photos[(i-60)/4]);
    		   setPion(container);
    		   checkCase(i);
    	   }
       }
       
 }