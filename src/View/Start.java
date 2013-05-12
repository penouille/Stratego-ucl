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

import son.Son;

import Controller.Controller;

@SuppressWarnings("serial")
public class Start extends StdWindow implements ActionListener, Runnable
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
	
	private boolean isAnIA;
	
	private Son music;

	public Start(Controller controller, Son music) 
	{
		super("Menu de demarrage");
		
		this.controller=controller;
		this.music = music;
		
		//initialisation de l'URL
		url_img = this.getClass().getResource("/stratego.jpg");
		
		img = new ImageIcon(url_img);
		
		int width = img.getIconWidth(); int height = img.getIconHeight();
		centerMe(width, height, 250); //Dimensionne et centre le JFrame.
		
		//initialisation des buttons		
		URL url_JvJ = this.getClass().getResource("/JvJ.png");
		URL url_JvIA = this.getClass().getResource("/JvIA.png");
		URL url_options = this.getClass().getResource("/options.png");
		URL url_regles = this.getClass().getResource("/reglesStart.png");
		URL url_scores = this.getClass().getResource("/scores.png");
		URL url_quitter = this.getClass().getResource("/quitter.png");
		
		JvJ = new JCoolButton(new ImageIcon(url_JvJ));
		JvIA = new JCoolButton(new ImageIcon(url_JvIA));
		option = new JCoolButton(new ImageIcon(url_options));
		regle = new JCoolButton(new ImageIcon(url_regles));
		score = new JCoolButton(new ImageIcon(url_scores));
		quit = new JCoolButton(new ImageIcon(url_quitter));
		
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
		if(controller.isSon()) music.playCanon();
		JButton b = (JButton)e.getSource();
		if(b==JvJ)
		{
			music.changeSong(true);
			setVisible(false);
			isAnIA = false;
			new Thread(this).start();
			new Pseudo(controller);
		}
		if(b==JvIA)
		{
			music.changeSong(true);
			setVisible(false);
			isAnIA = true;
			new Thread(this).start();
			new Pseudo(controller);
		}
		if(b==option)
		{
			new Option(controller);
		}
		if(b==quit)
		{
			System.exit(EXIT_ON_CLOSE);
		}
		if(b==score)
		{
			new ScoresWindows();
		}
		if(b==regle)
		{
			new Regles();
		}
	}

	@Override
	public void run()
	{
		controller.setIA(isAnIA);
		if(isAnIA)
		{
			new AdminGame("Stratego : Joueur Vs IA", controller);
		}
		else
		{
			new AdminGame("Stratego : Joueur Vs Joueur", controller);
		}
	}

}
