package Controller;

import View.AdministratorGUI;
import Game.Game;


public abstract class AbstractController
{
	protected Game game;
	protected AdministratorGUI AG;
	
	abstract void control();
}
