package System;

import View.Option;
import View.Start;
import Controller.Controller;

public class system 
{
	
	public system()
	{
		
	}
	
	public static void main( String args[] )
	{
		//Creation des Models
		//Game game = new Game();
		//creation du controller
		Controller controller = new Controller();
		new Start(controller);
	}

}
