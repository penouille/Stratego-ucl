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


@SuppressWarnings("serial")
public class Option extends StdWindow implements ActionListener
{
	
	private JPanel PPrincipale;
	private JPanel PForButton; //contient les boutons dans un gridlayout
	private JPanel PForCouleurs; //contient la liste des bouton relatif à la couleur dans un gridlayout.
	private JPanel PForDifficultees;
	private JPanel PForMusique;
	
	private JButton apply;
	private JButton retour;
	
	private JLabel img;
	private JLabel musique;
	private JLabel couleurs;
	private JLabel difficultees;
	
	private JComboBox<String> comboBoxCouleurs;
	private JComboBox<String> comboBoxDifficultees;
	private JComboBox<String> comboBoxMusique;
	
	private String[] TScouleurs = {"Rouge", "Vert", "Bleu", "Noir"}; 
	private String[] TSdifficultees = {"Kikoo", "Facile", "Normal", "Difficile", "Extrème"};
	private String[] TSmusique = {"ON", "OFF"};
	
	private Clip clip;
	private URL url_son;
	
	
	private URL url_image;
	private ImageIcon image;
	
	public Option()
	{
		super("Options"); //Crée un JFrame à partir de la classe stdWindow
		
		//initialisation des URL
		url_son = this.getClass().getResource("/song.wav");
		url_image = this.getClass().getResource("/option.png");
		
		//initialisation du Frame ainsi de que l'image pour en obtenir les dimensions, et les utiliser pour dimensionner le Frame.
		image = new ImageIcon(url_image);
		
		int width = image.getIconWidth(); int height = image.getIconHeight();
		centerMe(width, height, 200); //Dimensionne et centre le JFrame.
		
		//intialisation des JButtons
		apply = new JButton("Appliquer");
		retour = new JButton("Retour");
		
		//initialisation des JLabel
		musique = new JLabel("Musique");
		couleurs = new JLabel("Couleur");
		difficultees = new JLabel("Difficulté");
		img = new JLabel(image);
		
		//initialisation des JPanel
		PPrincipale = new JPanel (new BorderLayout());
		PForButton = new JPanel (new GridLayout(5,1));
		PForMusique = new JPanel (new GridLayout(1,2));
		PForCouleurs = new JPanel (new GridLayout(1,2));
		PForDifficultees = new JPanel (new GridLayout(1,2));
		
		//intialisation des JComboBox + ajout des champs
		comboBoxCouleurs = new JComboBox<String>();
		comboBoxDifficultees = new JComboBox<String>();
		comboBoxMusique = new JComboBox<String>();
		initialiseCombo(comboBoxCouleurs, TScouleurs);
		initialiseCombo(comboBoxDifficultees, TSdifficultees);
		initialiseCombo(comboBoxMusique, TSmusique);
		
		//ajout des éléments dans le Panel pour la musique
		addLabelToPanel(musique,PForMusique);
		PForMusique.add(comboBoxMusique);
		
		//ajout des éléments dans le Panel pour les couleurs
		addLabelToPanel(couleurs,PForCouleurs);
		PForCouleurs.add(comboBoxCouleurs);
		
		//ajout des éléments dans le Panel pour les difficulté
		addLabelToPanel(difficultees,PForDifficultees);
		PForDifficultees.add(comboBoxDifficultees);
		
		//ajout des éléments dans le Panel pour les boutons
		PForButton.add(PForMusique);
		PForButton.add(PForCouleurs);
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
		setVisible(true);
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
		comboBoxCouleurs.addActionListener(this);
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
				System.out.println("Application en cours . . .");
			}
			if(b==retour)
			{
				dispose();
			}
		}
		else
		{
			JComboBox jc = (JComboBox) e.getSource();
			if(jc == comboBoxMusique)
			{
				changeMusic();
			}
			if( jc == comboBoxCouleurs)
			{
				String s = (String)comboBoxCouleurs.getSelectedItem();
				if(s=="Vert") PPrincipale.setBackground(Color.green);
				if(s=="Rouge") PPrincipale.setBackground(Color.red);
				if(s=="Bleu") PPrincipale.setBackground(Color.blue);
				if(s=="Noir") PPrincipale.setBackground(Color.black);
			}
			if(jc == comboBoxDifficultees)
			{
				String s2 = (String)comboBoxDifficultees.getSelectedItem();
				if(s2=="Kikoo")
				{
					JFrame Fmoment = new JFrame("Gros kikoo");
					Fmoment.add(new JPanel().add(new JLabel("T'es pas sérieux ?!? Tu vas pas jouer en mode kikoo quand même ?!?")));
					Fmoment.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					Fmoment.pack();
					Fmoment.setVisible(true);
				}
				if(s2=="Facile")
				{
					JFrame Fmoment = new JFrame("Noob");
					Fmoment.add(new JPanel().add(new JLabel("Bah le noob, il joue en mode facile !")));
					Fmoment.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					Fmoment.pack();
					Fmoment.setVisible(true);
				}
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

	public static void main (String args [])
	{
		new Option();
	}
}
