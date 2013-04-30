package View;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Regles extends StdWindow
{
	
	private JPanel PPrincipal;
	
	private JScrollPane jsb;
	
	private URL url_img;
	private ImageIcon img;
	
	private URL url_regle1;
	private ImageIcon regle1;
	
	private URL url_regle2;
	private ImageIcon regle2;
	
	private URL url_regle3;
	private ImageIcon regle3;

	public Regles()
	{
		super("Règles");
		
		//initialisation JPanel
		PPrincipal = new JPanel(new BorderLayout());
		
		//initialisation image
		url_img = this.getClass().getResource("/personnages.png");
		img = new ImageIcon(url_img);
		
		url_regle1 = this.getClass().getResource("/regle1.png");
		regle1 = new ImageIcon(url_regle1);
		
		url_regle2 = this.getClass().getResource("/regle2.png");
		regle2 = new ImageIcon(url_regle2);
		
		url_regle3 = this.getClass().getResource("/regle3.png");
		regle3 = new ImageIcon(url_regle3);
		
		//Mis des images dans les JPanels
		JPanel listImgRegle1 = new JPanel(new BorderLayout());
		listImgRegle1.add(new JLabel(img), BorderLayout.NORTH);
		listImgRegle1.add(new JLabel(regle1), BorderLayout.CENTER);
		
		JPanel listImgRegle2 = new JPanel(new BorderLayout());
		listImgRegle2.add(new JLabel(regle2), BorderLayout.NORTH);
		listImgRegle2.add(new JLabel(regle3), BorderLayout.CENTER);
		
		listImgRegle1.add(listImgRegle2, BorderLayout.SOUTH);
		
		//initialisation ScrollBar
		jsb = new JScrollPane(listImgRegle1);
		jsb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsb.setAutoscrolls(true);
		
		
		centerMe(580,img.getIconHeight(),400);
		
		
		//mise de composant
		PPrincipal.add(jsb, BorderLayout.CENTER);
		
		add(PPrincipal);
		
		setVisible(true);
	}

}
