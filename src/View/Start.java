package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;
import Game.Game;

import observer.Observer;

public class Start extends StdWindow implements ActionListener //implements Observer
{
	private URL url_img;
	
	private JButton JvJ;
	private JButton JvIA;
	private JButton quit;
	private JButton option;
	private JButton regle;
	private JButton score;
	
	private JPanel PPrincipal;
	private JPanel PForButtons;
	
	private JLabel image;
	
	private ImageIcon img;
	
	private Controller controller;
	
	//private OperateurListener opeListener = new OperateurListener();

	public Start(Controller controller) 
	{
		super("Menu de demarrage");
		
		this.controller=controller;
		
		//initialisation de l'URL
		url_img = this.getClass().getResource("/stratego.jpg");
		
		img = new ImageIcon(url_img);
		
		int width = img.getIconWidth(); int height = img.getIconHeight();
		centerMe(width, height, 250); //Dimensionne et centre le JFrame.
		
		//initialisation des buttons
		JvJ = new JButton("Joueur Vs Joueur");
		JvIA = new JButton("Joueur Vs IA");
		quit = new JButton("Quitter");
		option = new JButton("Option");
		regle = new JButton("Règles");
		score = new JButton("Scores");
		
		//intialisation Panel
		PPrincipal = new JPanel(new BorderLayout());
		PForButtons = new JPanel(new GridLayout(6,1));
		
		//intialisation JLabel
		image = new JLabel(img);
		
		//mise en place dans le Panel des boutons
		PForButtons.add(JvJ);
		PForButtons.add(JvIA);
		PForButtons.add(option);
		PForButtons.add(regle);
		PForButtons.add(score);
		PForButtons.add(quit);
		
		//ajout de l'action listener
		initializeButton();
		
		//mise en place dans le panel principal
		PPrincipal.add(image, BorderLayout.NORTH);
		PPrincipal.add(PForButtons, BorderLayout.CENTER);
		
		//les finiolages
		add(PPrincipal);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void initializeButton()
	{
		JvJ.addActionListener(this);
		JvIA.addActionListener(this);
		quit.addActionListener(this);
		option.addActionListener(this);
		regle.addActionListener(this);
		score.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b==JvJ || b==JvIA)
		{
			Controller controller = new Controller();
			AdminGame admin = new AdminGame("Stratego : Joueur Vs Joueur", controller);
		}
		if(b==option)
		{
			new Option(controller);
			//TODO Il serait bien de donner le controlleur à option, mais comment ?
		}
		if(b==quit)
		{
			System.exit(EXIT_ON_CLOSE);
		}
		if(b==score)
		{
			
		}
		if(b==regle)
		{
			
		}
	}

}
