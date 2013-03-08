package View;

import java.awt.Dimension;

import javax.swing.JFrame;

public class StdWindow extends JFrame
{
	//JFrame Frame;
	
	public StdWindow(String name)
	{
		//Frame = new JFrame(name);
		super(name);
		//Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//DISPOSE_ON_CLOSE
		
		//Frame.setVisible(true);
		//setVisible(true);
		//Frame.setResizable(false);
		setResizable(false);
	}
	
	public void centerMe (int x, int yPrime, int placeForComponent)
	{
		//Frame.setSize(x, y);
		int y = yPrime+placeForComponent;
		setSize(x, y);
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		System.out.println("height : "+height+" et width : "+width);
		//Frame.setLocation((width/2)-(x/2), (height/2)-(y/2));
		setLocation((width/2)-(x/2), (height/2)-(y/2));
	}
}
