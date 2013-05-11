package System;

import View.Start;
import Controller.Controller;

public class system 
{
	
	public static void main( String args[] )
	{
		Controller controller = new Controller();
		new Start(controller);
	}

}