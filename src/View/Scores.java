package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controller.Controller;
import Game.Score;

public class Scores extends StdWindow
{
	
	private Controller controller;
	
	private JScrollPane jsb;
	
	private JPanel PPrincipal;
	private JPanel PIntermediaire;
	private JPanel PForHead;
	private JPanel PForScore;
	
	private JLabel head1;
	private JLabel head2;
	private JLabel head3;
	private JLabel head4;
	
	private URL url_head1;
	private URL url_head2;
	private URL url_head3;
	
	public Scores(Controller controller)
	{
		super("Scores");
		
		this.controller = controller;
		
		//ArrayList<String> listScore = Score.getScore();
		ArrayList<String> listScore = new ArrayList<String>();
		listScore.add("Penouille-26-Vineuvall-1014-");
		listScore.add("Penouille-1001-Vineuvall-13-");
		listScore.add("Unknown-1000-IA Kikoo-39-");
		
		//Instanciation des JPanel
		PPrincipal = new JPanel(new BorderLayout());
		PIntermediaire = new JPanel(new BorderLayout());
		PForHead = new JPanel(new GridLayout(1,4));
		PForScore = new JPanel(new GridLayout(listScore.size(), 4));
		
		PForScore.setBackground(new Color(7, 15, 41));
		
		//Instanciation des Head
		url_head1 = this.getClass().getResource("/head1.png");
		url_head2 = this.getClass().getResource("/head2.png");
		url_head3 = this.getClass().getResource("/head3.png");
		
		head1 = new JLabel(new ImageIcon(url_head1));
		head2 = new JLabel(new ImageIcon(url_head2));
		head3 = new JLabel(new ImageIcon(url_head3));
		head4 = new JLabel(new ImageIcon(url_head2));
		
		//Mise des composants dans PForHead
		PForHead.add(head1);
		PForHead.add(head2);
		PForHead.add(head3);
		PForHead.add(head4);
		
		//Remplissage des score
		fillScore(listScore);
		
		//mise des composants 1
		PIntermediaire.add(PForHead, BorderLayout.NORTH);
		PIntermediaire.add(PForScore, BorderLayout.CENTER);
		PIntermediaire.add(new JPanel(), BorderLayout.SOUTH);
		
		//initialisation ScrollBar
		jsb = new JScrollPane(PIntermediaire);
		jsb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsb.setAutoscrolls(true);
		
		//mise de composants 2
		PPrincipal.add(jsb, BorderLayout.CENTER);
		
		add(PPrincipal);
		
		centerMe(627, 200, 0);
		//setResizable(true);
		setVisible(true);
	}

	private void fillScore(ArrayList<String> listScore)
	{
		JLabel lScore;
		for(String score : listScore)
		{
			while(score.length()!=0)
			{
				lScore = new JLabel(score.substring(0, score.indexOf("-")));
				PForScore.add(lScore);
				lScore.setForeground(new Color(202,202,202));
				lScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				score = score.substring(score.indexOf("-")+1);
			}
		}
	}
	
	/*public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;         
	    GradientPaint gp = new GradientPaint(0, 0, Color.RED, 30, 30, Color.cyan, true);                
	    g2d.setPaint(gp);
	    g2d.fillRect(0, 0, this.getWidth(), this.getHeight()); 
	}*/
}
