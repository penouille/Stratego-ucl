package System;

import son.Son;
import View.Start;
import Controller.Controller;

public class system 
{
	
	public static void main( String args[] )
	{
		Son son = new Son();
		Controller controller = new Controller(son);
		new Start(controller, son);
	}

}