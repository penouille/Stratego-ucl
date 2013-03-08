package View;


import java.awt.GridLayout;

import javax.swing.*;


public class administratorGUI 
{
	
	JFrame frame;
	JLabel label;
	JPanel PForButton;
	JButton button;
	
	public administratorGUI ()
	{
		
		frame = new JFrame();
		PForButton = new JPanel (new GridLayout(11,11));
		for (int i = 0 ; i < 11 ; i++)
		{
			for (int j = 0 ; j< 11 ; i++)
			{
				button = new JButton();
				PForButton.add(button);
			}
		}
	}
	
}
