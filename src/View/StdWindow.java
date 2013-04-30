package View;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class StdWindow extends JFrame
{
	//JFrame Frame;
	
	public StdWindow(String name)
	{
		super(name);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setDesign();
		setResizable(false);
	}
	
	public void centerMe (int x, int yPrime, int placeForComponent)
	{
		int y = yPrime+placeForComponent;
		setSize(x, y);
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		setLocation((width/2)-(x/2), (height/2)-(y/2));
	}
	
	protected void setDesign()
	{
		for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if("Nimbus".equals(info.getName()))
			{
				try {
					UIManager.setLookAndFeel(info.getClassName());
					SwingUtilities.updateComponentTreeUI(this);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
