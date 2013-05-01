package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Attention extends StdWindow implements ActionListener
{
	JPanel PPrincipal;
	
	JLabel Lmessage;
	
	JButton ok;
	
	public Attention(String message)
	{
		super("Attention");
		
		PPrincipal = new JPanel(new BorderLayout());
		Lmessage = new JLabel(message);
		ok = new JButton("Ok");
		ok.addActionListener(this);
		
		PPrincipal.add(Lmessage, BorderLayout.CENTER);
		Lmessage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		PPrincipal.add(ok, BorderLayout.SOUTH);
		ok.setPreferredSize(new Dimension(100,50));
		ok.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		
		
		add(PPrincipal);
		
		centerMe(300,200, 0);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton) e.getSource();
		if(b==ok)
		{
			dispose();
		}
	}
}
