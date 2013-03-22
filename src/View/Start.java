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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import Controller.Controller;

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
		
		/*JvJ = new JButton("Joueur Vs Joueur");
		URL url_JvJ = this.getClass().getResource("/JvJ.jpg");
		JvJ = new JButton(new ImageIcon(url_JvJ));
		
		JvIA = new JButton("Joueur Vs IA");
		quit = new JButton("Quitter");
		option = new JButton("Option");
		regle = new JButton("Règles");
		score = new JButton("Scores");*/
		
		URL url_JvJ = this.getClass().getResource("/JvJ.jpg");
		JvJ = new JCoolButton(new ImageIcon(url_JvJ));
		
		JvIA = new JCoolButton("Joueur Vs IA");
		quit = new JCoolButton("Quitter");
		option = new JCoolButton("Option");
		regle = new JCoolButton("Règles");
		score = new JCoolButton("Scores");
		
		//personalise les JButtons
		//personalizeButton();
		
		//intialisation Panel
		PPrincipal = new JPanel(new BorderLayout());
		PForButtons = new JPanel(new GridLayout(7,1));
		
		//intialisation JLabel
		image = new JLabel(img);
		
		//mise en place dans le Panel des boutons
		PForButtons.add(JvJ);
		PForButtons.add(JvIA);
		PForButtons.add(option);
		PForButtons.add(regle);
		PForButtons.add(score);
		PForButtons.add(quit);
		PForButtons.add(new JCoolButton("test"));
		
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
	
	public void personalizeButton()
	{
		try {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				System.out.println(info.getName());
				if("nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//JvJ.setOpaque(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b==JvJ)
		{
			setVisible(false);
			isAnIA = false;
			//AdminGame admin = new AdminGame("Stratego : Joueur Vs Joueur", controller);
			new Thread(this).start();
		}
		if(b==JvIA)
		{
			setVisible(false);
			isAnIA = true;
			//AdminGame admin = new AdminGame("Stratego : Joueur Vs Joueur", controller);
			new Thread(this).start();
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
			
		}
		if(b==regle)
		{
			
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(isAnIA)
		{
			controller.setIA(isAnIA);
			new AdminGame("Stratego : Joueur Vs Joueur", controller);
		}
		else
		{
			controller.setIA(isAnIA);
			new AdminGame("Stratego : Joueur Vs Joueur", controller);
		}
	}

}
