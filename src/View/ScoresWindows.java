package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Game.Score;

@SuppressWarnings("serial")
public class ScoresWindows extends StdWindow implements ActionListener
{
	
	private JScrollPane jsb;
	
	private JPanel PPrincipal;
	private JPanel PIntermediaire;
	private JPanel PForHead;
	private JPanel PForScore;
	private JPanel PForButton;
	
	private JLabel head1;
	private JLabel head2;
	private JLabel head3;
	private JLabel head4;
	
	private URL url_head1;
	private URL url_head2;
	private URL url_head3;
	
	private JButton retour;
	private JButton reset;
	
	public ScoresWindows()
	{
		super("Scores");
		
		
		ArrayList<String> listScore = Score.ReadScore();
		
		//Instanciation des JPanel
		PPrincipal = new JPanel(new BorderLayout());
		PIntermediaire = new JPanel(new BorderLayout());
		PForHead = new JPanel(new GridLayout(1,4));
		PForScore = new JPanel(new GridLayout(listScore.size(), 4));
		PForButton = new JPanel(new GridLayout(1,5));
		
		PForScore.setBackground(new Color(7, 15, 41));
		PForButton.setBackground(new Color(7, 15, 41));
		PPrincipal.setBackground(new Color(7, 15, 41));
		
		//JButton
		reset = new JButton ("Effacer les scores");
		retour = new JButton ("Retour");
		
		reset.addActionListener(this);
		retour.addActionListener(this);
		
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
		
		//Mise des bouttons
		PForButton.add(new JLabel());
		PForButton.add(reset);
		PForButton.add(new JLabel());
		PForButton.add(retour);
		PForButton.add(new JLabel());
		
		//mise des composants 1
		PIntermediaire.add(PForScore, BorderLayout.NORTH);
		
		//initialisation ScrollBar
		jsb = new JScrollPane(PIntermediaire);
		jsb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsb.setAutoscrolls(true);
		
		//mise de composants 2
		PPrincipal.add(PForHead, BorderLayout.NORTH);
		PPrincipal.add(jsb, BorderLayout.CENTER);
		PPrincipal.add(PForButton, BorderLayout.SOUTH);
		revalidate();
		
		
		add(PPrincipal);
		
		dimensionMe();
		setVisible(true);
	}

	private void dimensionMe()
	{
		pack();
		if(this.getHeight()>600){
			centerMe(598, 600, 0);
		}
		else{
			centerMe(598, this.getHeight(),0);
		}
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton) e.getSource();
		if(b==reset)
		{
			Score.setScore(new ArrayList<String>());
			Score.SaveScore();
			PForScore.removeAll();
			dimensionMe();
			PForScore.updateUI();
		}
		else if(b==retour)
		{
			this.dispose();
		}
	}
}
