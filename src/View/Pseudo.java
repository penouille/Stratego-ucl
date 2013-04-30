package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Controller;

public class Pseudo extends StdWindow implements ActionListener, WindowListener
{
	private Controller controller;
	
	private JPanel PPrincipale;
	private JPanel PForName;
	private JPanel PForButton;
	
	private JLabel name1;
	private JLabel name2;
	
	private JTextField tfName1;
	private JTextField tfName2;
	
	private JButton ok;
	private JButton osef;
	
	public Pseudo(Controller controller)
	{
		super("Choix des pseudos");
		
		this.controller = controller;
		
		//Instanciation des JPanel
		PPrincipale = new JPanel(new BorderLayout());
		PForName = new JPanel(new GridLayout(2,2));
		PForButton = new JPanel(new GridLayout(1,2));
		
		//Instanciation des JLabel
		name1 = new JLabel("Nom/Pseudo du joueur 1 : ");
		name2 = new JLabel("Nom/Pseudo du joueur 1 : ");
		
		//Instanciation des JTextField
		tfName1 = new JTextField();
		tfName2 = new JTextField();
		
		//Instanciation des JButton
		ok = new JButton("Ok");
		osef = new JButton("Nobody cares");
		
		ok.addActionListener(this);
		osef.addActionListener(this);
		
		//Mise des composant
		PForName.add(name1);
		PForName.add(tfName1);
		PForName.add(name2);
		PForName.add(tfName2);
		
		PForButton.add(ok);
		PForButton.add(osef);
		
		PPrincipale.add(PForName, BorderLayout.NORTH);
		PPrincipale.add(PForButton, BorderLayout.CENTER);
		
		add(PPrincipale);
		
		centerMe(300, 150, 0);
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		if(controller.getIsAnIA()) changeForIA();
		setVisible(true);
	}

	private void changeForIA()
	{
		System.out.println("Il y a une IA");
		while(controller.getIA()==null)
		setTitle("Choix du pseudo");
		name2.setText("Type d'IA");
		tfName2.setText(controller.getIA().getForceIA());
		tfName2.setEditable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		JButton b = (JButton) e.getSource();
		if(b==ok)
		{
			if(lookForTf())
			{
				
			}
			else
			{
				new Attention("Les champs ne sont pas remplis correctement !");
			}
		}
		else if(b==osef)
		{
			dispose();
		}
	}

	private boolean lookForTf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
		if(lookForTf())
		{
			
		}
		else
		{
			new Attention("Les champs ne sont pas remplis correctement !");
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if(lookForTf())
		{
			
		}
		else
		{
			new Attention("Les champs ne sont pas remplis correctement !");
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}
}
