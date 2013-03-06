package View;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;


@SuppressWarnings("serial")
public class Start extends JFrame implements ActionListener
{
	private JFrame Frame;
	
	private JLabel couleur;
	private JLabel difficulte;
	
	private JPanel PPrincipale;
	private JPanel PForButton; //contient leses bouton dans un gridlayout
	private JPanel PForCouleur; //contient la liste des bouton relatif à la couleur dans un gridlayout.
	private JPanel PForDifficulte;
	
	private JButton music;
	private JButton apply;
	private JButton retour;
	
	private JLabel img;
	private AudioClip clip;
	
	private JComboBox<String> comboBoxcouleurs;
	private JComboBox<String> comboBoxdifficultees;
	
	private String[] couleurs = {"Rouge", "Vert", "Bleu"}; 
	private String[] difficultees = {"Kikoo", "Facile", "Normal", "Difficile", "Extrème"};
	
	URL url_son = this.getClass().getResource("12 - Arcades.flac");
	URL url_image = this.getClass().getResource("5730371_460s.jpg");
	
	public Start()
	{
		
		Frame = new JFrame("Options");
		Frame.setSize(500, 600);
		Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		music = new JButton("Musique On");
		apply = new JButton("Appliquer");
		retour = new JButton("Retour");
		
		couleur = new JLabel("Couleur");
		difficulte = new JLabel("Difficulté");
		
		PPrincipale = new JPanel (new BorderLayout());
		img = new JLabel(new ImageIcon(url_image));
		
		PForButton = new JPanel (new GridLayout(5,1));
		PForCouleur = new JPanel (new GridLayout(1,2));
		PForDifficulte = new JPanel (new GridLayout(1,2));
		
		//Initialistaion de la boc des couleurs.
		comboBoxcouleurs = new JComboBox<String>();
		UpLabel(couleur , PForCouleur );
		initialiseCombo( comboBoxcouleurs , couleurs );
		
		//Initialisation de la box des difficultées.
		comboBoxdifficultees = new JComboBox<String>();
		UpLabel(difficulte , PForDifficulte);
		initialiseCombo( comboBoxdifficultees , difficultees );
				
		//Panel des couleurs.
		PForCouleur.add(couleur);
		PForCouleur.add(comboBoxcouleurs);
		
		//Panel des difficultees
		PForDifficulte.add(difficulte);
		PForDifficulte.add(comboBoxdifficultees);
		
		//Ajout des différents boutons au panel principal. 
		PForButton.add(music);
		
		//Ajout des panel couleur et difficultees au panel superieur
		PForButton.add(PForCouleur);
		PForButton.add(PForDifficulte);
		
		PForButton.add(apply);
		PForButton.add(retour);
		
		//Ajout de l'image et du panel des boutons.
		PPrincipale.add(img, BorderLayout.NORTH);
		PPrincipale.add(PForButton, BorderLayout.CENTER);
		
		Frame.add(PPrincipale);
		setListener();
		startSon();
		Frame.setVisible(true);
	}
	
	/**
	 * pré:-
	 * post: Raloute un label à un panel et le centre.
	 */
	private void UpLabel( JLabel label, JPanel panel)
	{
		panel.add(label);
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		
	}
	
	private void startSon() 
	{
		clip = Applet.newAudioClip(url_son);
		//clip.loop();
	}
	
	/**
	 * @pré: -
	 * @post: initialise une combobox et lui donne comme valeur les éléments du tableau de string passé en paramètre.
	 * @param combo
	 * @param string
	 */
	private void initialiseCombo( JComboBox<String> combo , String[] string) 
	{
		
		for (int i = 0 ; i < string.length ; i++) 
		{
			combo.addItem(string[i]);
		}
	}

	private void setListener() 
	{
		music.addActionListener(this);
		apply.addActionListener(this);
		retour.addActionListener(this);
		//comboBoxcouleurs.addActionListener(this);
		//comboBoxdifficultees.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		JButton b = (JButton) arg0.getSource();
		//mettre un if pr différencier les boutons des combobox.
		
		if(b==music)
		{
			changeMusic();
		}
		
		if(b==apply)
		{
			//code pour appliquer les options modifiées.
		}
		
		if(b==retour)
		{
			Frame.dispose(); //ferme la fenêtre.
		}
	}
	
	private void changeMusic() 
	{
		if(music.getText()=="Musique On")
		{
			music.setText("Musique Off");
			clip.stop();
		}
		else
		{
			music.setText("Musique On");
			clip.loop();
		}
	}
	
	public static void main (String args [])
	{
		new Start();
	}
}
