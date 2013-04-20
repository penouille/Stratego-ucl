package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.SlickException;

import Controller.Controller;


@SuppressWarnings("serial")
public class Option extends StdWindow implements ActionListener
{
	
	private JPanel PPrincipale;
	private JPanel PForButton; //contient les boutons dans un gridlayout
	private JPanel PForCouleursJ1; //contient la liste des bouton relatif à la couleur dans un gridlayout.
	private JPanel PForCouleursJ2;
	private JPanel PForDifficultees;
	private JPanel PForMusique;
	
	private JButton apply;
	private JButton retour;
	
	private JLabel img;
	private JLabel musique;
	private JLabel couleursJ1;
	private JLabel couleursJ2;
	private JLabel difficultees;
	
	private JComboBox<String> comboBoxCouleursJ1;
	private JComboBox<String> comboBoxCouleursJ2;
	private JComboBox<String> comboBoxDifficultees;
	private JComboBox<String> comboBoxMusique;
	
	private String[] TScouleurs = {"Rouge", "Vert", "Bleu", "Noir"}; 
	private String[] TSdifficultees = {"Kikoo", "Facile", "Normal", "Difficile", "Extrème"};
	private String[] TSmusique = {"ON", "OFF"};
	
	private static Clip clip;
	private URL url_son;
	
	
	private URL url_image;
	private ImageIcon image;
	
	private Controller controller;
	
	private CurrentGame CG;
	
	public Option(Controller controller)
	{
		super("Options"); //Crée un JFrame à partir de la classe stdWindow
		
		this.controller = controller;
		
		//initialisation des URL
		url_son = this.getClass().getResource("/song.wav");
		url_image = this.getClass().getResource("/option.png");
		
		//initialisation du Frame ainsi de que l'image pour en obtenir les dimensions, et les utiliser pour dimensionner le Frame.
		image = new ImageIcon(url_image);
		
		int width = image.getIconWidth(); int height = image.getIconHeight();
		centerMe(width, height, 230); //Dimensionne et centre le JFrame.
		
		//intialisation des JButtons
		apply = new JButton("Appliquer");
		retour = new JButton("Retour");
		
		//initialisation des JLabel
		musique = new JLabel("Musique");
		couleursJ1 = new JLabel("Couleur Joueur1");
		couleursJ2 = new JLabel("Couleur Joueur2");
		difficultees = new JLabel("Difficulté");
		img = new JLabel(image);
		
		//initialisation des JPanel
		PPrincipale = new JPanel (new BorderLayout());
		PForButton = new JPanel (new GridLayout(6,1));
		PForMusique = new JPanel (new GridLayout(1,2));
		PForCouleursJ1 = new JPanel (new GridLayout(1,2));
		PForCouleursJ2 = new JPanel (new GridLayout(1,2));
		PForDifficultees = new JPanel (new GridLayout(1,2));
		
		//intialisation des JComboBox + ajout des champs
		comboBoxCouleursJ1 = new JComboBox<String>();
		comboBoxCouleursJ2 = new JComboBox<String>();
		comboBoxDifficultees = new JComboBox<String>();
		comboBoxMusique = new JComboBox<String>();
		initialiseCombo(comboBoxCouleursJ1, TScouleurs);
		initialiseCombo(comboBoxCouleursJ2, TScouleurs);
		initialiseCombo(comboBoxDifficultees, TSdifficultees);
		initialiseCombo(comboBoxMusique, TSmusique);
		
		//ajout des éléments dans le Panel pour la musique
		addLabelToPanel(musique,PForMusique);
		PForMusique.add(comboBoxMusique);
		
		//ajout des éléments dans le Panel pour les couleurs pour le J1
		addLabelToPanel(couleursJ1,PForCouleursJ1);
		PForCouleursJ1.add(comboBoxCouleursJ1);
		
		//ajout des éléments dans le Panel pour les couleurs pour le J2
		addLabelToPanel(couleursJ2,PForCouleursJ2);
		PForCouleursJ2.add(comboBoxCouleursJ2);
		
		//ajout des éléments dans le Panel pour les difficulté
		addLabelToPanel(difficultees,PForDifficultees);
		PForDifficultees.add(comboBoxDifficultees);
		
		//ajout des éléments dans le Panel pour les boutons
		PForButton.add(PForMusique);
		PForButton.add(PForCouleursJ1);
		PForButton.add(PForCouleursJ2);
		PForButton.add(PForDifficultees);
		PForButton.add(apply);
		PForButton.add(retour);
		
		//ajout des éléments dans le Panel principale
		PPrincipale.add(img, BorderLayout.NORTH); //ajoute de l'image
		PPrincipale.add(PForButton, BorderLayout.CENTER); //ajout du reste
		PPrincipale.setBackground(Color.black);
		
		add(PPrincipale);
		setListener(); //Met les ActionListener aux boutons et au xomboBoxs
		startSon();
		setVisible(false);
	}
	
	
	
	private void startSon() 
	{
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream (url_son));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (LineUnavailableException exception) { }
		catch (IOException exception) {  }
		catch (UnsupportedAudioFileException exception) {  }
	}
	
	/**
	 * pré:-
	 * post: Raloute un label à un panel et le centre.
	 */
	private void addLabelToPanel(JLabel label, JPanel panel)
	{
		panel.add(label);
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		
	}

	private void setListener()
	{
		apply.addActionListener(this);
		retour.addActionListener(this);
		comboBoxCouleursJ1.addActionListener(this);
		comboBoxCouleursJ2.addActionListener(this);
		comboBoxDifficultees.addActionListener(this);
		comboBoxMusique.addActionListener(this);
	}
	
	
	/**
	 * @pré: -
	 * @post: initialise une comboBox et lui donne comme valeur les éléments du tableau de string passé en paramètre.
	 * @param combo
	 * @param string
	 */
	private void initialiseCombo(JComboBox<String> combo , String[] string) 
	{
		
		for (int i = 0 ; i < string.length ; i++)
		{
			combo.addItem(string[i]);
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.toString().contains("JButton"))
		{
			JButton b = (JButton) e.getSource();
			if(b==apply)
			{
				setVisible(false);
				System.out.println("Application en cours . . .");
				if(CG!=null){
					try {
						CG.UpGame2();
					} catch (SlickException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			if(b==retour)
			{
				setVisible(false);
			}
		}
		else
		{
			JComboBox jc = (JComboBox) e.getSource();
			if(jc == comboBoxMusique)
			{
				changeMusic();
			}
			if( jc == comboBoxCouleursJ1)
			{
				String s = (String)comboBoxCouleursJ1.getSelectedItem();
				if(s=="Vert")
				{
					PPrincipale.setBackground(new Color(0,166,54));
					controller.getGame().getJ1().setPrefColor("Vert");
				}
				if(s=="Rouge")
				{
					PPrincipale.setBackground(Color.red);
					controller.getGame().getJ1().setPrefColor("Rouge");
				}
				if(s=="Bleu")
				{
					PPrincipale.setBackground(Color.blue);
					controller.getGame().getJ1().setPrefColor("Bleu");
				}
				if(s=="Noir") 
				{
					PPrincipale.setBackground(Color.black);
					controller.getGame().getJ1().setPrefColor("");
				}
			}
			if( jc == comboBoxCouleursJ2)
			{
				String s = (String)comboBoxCouleursJ2.getSelectedItem();
				if(s=="Vert")
				{
					PPrincipale.setBackground(new Color(0,166,54));
					controller.getGame().getJ2().setPrefColor("Vert");
				}
				if(s=="Rouge")
				{
					PPrincipale.setBackground(Color.red);
					controller.getGame().getJ2().setPrefColor("Rouge");
				}
				if(s=="Bleu")
				{
					PPrincipale.setBackground(Color.blue);
					controller.getGame().getJ2().setPrefColor("Bleu");
				}
				if(s=="Noir") 
				{
					PPrincipale.setBackground(Color.black);
					controller.getGame().getJ2().setPrefColor("");
				}
			}
			if(jc == comboBoxDifficultees)
			{
				String s2 = (String)comboBoxDifficultees.getSelectedItem();
				controller.getGame().getJ1().setPrefDiff(s2);
			}
		}
	}
	
	private void changeMusic()
	{
		if((String)comboBoxMusique.getSelectedItem()=="OFF")
		{
			clip.stop();
		}
		else
		{
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}



	public void setCurrentGame(CurrentGame currentGame)
	{
		this.CG = currentGame;
	}
}
