package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Start extends StdWindow
{
	private URL url_img;
	
	private JButton JvJ;
	private JButton JvIA;
	private JButton quit;
	private JButton option;
	
	private JPanel PPrincipal;
	private JPanel PForButtons;
	
	private JLabel image;
	
	private ImageIcon img;

	public Start() 
	{
		super("Menu de demarrage");
		
		//initialisation de l'URL
		url_img = this.getClass().getResource("/stratego.jpg");
		
		img = new ImageIcon(url_img);
		
		int width = img.getIconWidth(); int height = img.getIconHeight();
		centerMe(width, height, 200); //Dimensionne et centre le JFrame.
		
		//initialisation des buttons
		JvJ = new JButton("Joueur Vs Joueur");
		JvIA = new JButton("Joueur Vs IA");
		quit = new JButton("Quitter");
		option = new JButton("Option");
		
		//intialisation Panel
		PPrincipal = new JPanel(new BorderLayout());
		PForButtons = new JPanel(new GridLayout(4,1));
		
		//intialisation JLabel
		image = new JLabel(img);
		
		//mise en place dans le Panel des boutons
		PForButtons.add(JvJ);
		PForButtons.add(JvIA);
		PForButtons.add(option);
		PForButtons.add(quit);
		
		//mise en place dans le panel principal
		PPrincipal.add(image, BorderLayout.NORTH);
		PPrincipal.add(PForButtons, BorderLayout.CENTER);
		
		//les finiolages
		add(PPrincipal);
		setVisible(true);
	}
	
	
	public static void main (String [] toto)
	{
		new Start();
		
		
	}

}
