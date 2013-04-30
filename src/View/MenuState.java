package View;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/*les classes GUI de slick2D*/

import org.newdawn.slick.gui.AbstractComponent;
//import org.newdawn.slick.gui.BasicComponent; //deprecated
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

public class MenuState extends BasicGameState implements ComponentListener{

public static final int ID = 0;

private StateBasedGame game;
private GameContainer container;

/* les deux boutons en bas du ChoixState*/

private MouseOverArea quit;
private MouseOverArea play;

@Override
public int getID() 
{
	return ID;
}

@Override
public void init(GameContainer container, StateBasedGame game) throws SlickException {

quit = new MouseOverArea(container,new Image("espion.jpg"), 350, 620,this);
quit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
quit.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));

play = new MouseOverArea(container,new Image("bombe.jpg"),150, 430, this);
play.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
play.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));

}

@Override
public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

	
quit.render(container, g);
play.render(container, g);

 g.drawString("Hello dans MenuState",100,100);
 }

@Override
public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
//throw new UnsupportedOperationException("Not supported yet.");
}
@Override
public void componentActivated(AbstractComponent source) { //methode de l'interface ComponentListener

if (source == quit) {

this.container.exit();

}
if (source == play) {

game.enterState(1);

}
} // fin méthode

}// fin classe

