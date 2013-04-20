package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Regles extends StdWindow
{
	private JTextArea jta;
	
	private JPanel PPrincipal;
	
	private JScrollPane jsb;
	
	private URL url_img;
	private JLabel img;

	public Regles()
	{
		super("R�gles");
		
		//initialisation JPanel
		PPrincipal = new JPanel(new BorderLayout());
		
		//initialisation image
		url_img = this.getClass().getResource("/personnages.png");
		img = new JLabel(new ImageIcon(url_img));
		
		//intialisation de JTextArea
		jta = new JTextArea();
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setFont(new Font("Times New Roman",Font.PLAIN,14));
		
		//mise du texte
		jta.setText("R�gles du jeu : Stratego \n" +
				"\n" +
				"\n" +
				"Les rangs :\n" +
				"\n" +
				"L'espion tombe devant les �claireurs, ceux-ci tombent devant les d�mineurs, les d�mineurs tombent devant les sergents, les sergents devant les lieutenants, etc. Chaque pi�ce, quoi que soit son rang, tombe contre une bombe. Seul un d�mineur est en mesure de d�truire une bombe, apr�s quoi elle doit �tre retir�e du jeu.\n" +
				"\n" +
				"Le drapeau, qui ne peut pas �tre d�plac�, tombe devant n'importe quelle pi�ce de l'adversaire. Le mar�chal, le plus haut en grade, tombe seulement devant l'espion, s'il est pris par le dernier. Quand, au contraire, le mar�chal touche � l'espion, ce dernier tombe.\n" +
				"\n" +
				"Si un joueur ne peut plus d�placer aucune de ses pi�ces, par exemple lorsqu'il n'a plus que des bombes et le drapeau, il doit capituler. C'est le joueur qui r�ussit � s'emparer du drapeau de son adversaire, qui est le gagnant.\n" +
				"\n" +
				"Seul l'�claireur a le droit en un coup, de sauter par dessus plus d'une case libre.\n" +
				"\n" +
				"\n" +
				"\n" +
				"Disposition des pi�ces :\n" +
				"\n" +
				"Vous devez disposer vos pi�ces strat�giquement de fa�on � ce que l'attaque et la d�fense se d�ploient en faveur de chaque joueur qui les d�place.\n" +
				"\n" +
				/*"Il y a deux camps: les rouges et les bleus. �tant donn� que ce sont les rouges qui ouvrent le jeu, on tire d'abord au sort pour d�terminer � quel joueur cette couleur sera attribu�e.\n" +*/
				"Chaque joueur place ses pi�ces dans chaque case de la moiti� du terrain qui lui est attribu�e, mais de telle fa�on que l'adversaire ne voit pas ces pi�ces, afin que ce dernier ignore la valeur de chacune des pi�ces plac�es.\n" +
				"\n" +
				"Les deux rangs de cases du milieu restent inoccup�s, de m�me que les deux lacs � travers lesquels les deux arm�es ne peuvent pas se mouvoir au cours du jeu.\n" +
				"\n" +
				"La disposition des pi�ces doit �tre �tudi�e d'apr�s les r�gles du jeu d'une part, et suivant l'inspiration strat�gique du joueur d'autre part.\n" +
				"\n" +
				"\n" +
				"\n" +
				"Tour de jeu : \n" +
				"\n" +
				"A tour de r�le, les joueurs peuvent d�placer une de leurs pi�ces d'une case � la fois, soit en avant ou en arri�re, soit lat�ralement � gauche ou � droite.\n" +
				"\n" +
				"Le drapeau et les bombes ne peuvent pas �tre d�plac�s et restent donc pendant tout le jeu sur les cases o� ils ont �t� plac�s au d�but. Lorsque les bleus et les rouges se sont rapproch�s (les pi�ces se trouvant soit face � face, soit � c�t� les unes des autres) le joueur peut prendre une pi�ce adverse en la touchant. Les rangs des deux pi�ces sont alors r�v�l�s. Si le rang de la pi�ce qui a engag� le combat est plus �lev� que celui de la pi�ce adverse, alors cette derni�re tombe, et l'autre pi�ce prend sa place. Si au contraire, son rang est plus faible, alors c'est elle qui tombe, et la pi�ce adverse reste � sa place. Si les deux pi�ces sont de rangs �gaux, elles tombent toutes les deux.\n" +
				"\n" +
				"Si toutes les pi�ces se trouvent rapproch�es, il n'est pas n�cessaire d'attaquer imm�diatement; l'initiative de l'attaque peut venir de chacun des deux joueurs.\n" +
				"\n" +
				"\n" +
				"\n" +
				"Tactique : \n" +
				"\n" +
				"D'apr�s les r�gles du jeu ci-dessus indiqu�es, il ressort que la mise en place des pi�ces au d�but du jeu peut �tre d�terminante pour l'issue de la partie. Il sera donc raisonnable d'entourer le drapeau de quelques bombes, afin de bien pouvoir le d�fendre. Pour tromper l'adversaire, on fera bien, toutefois, de disposer aussi deux ou trois bombes � une certaine distance de ce drapeau. Quelques pi�ces d'un grade sup�rieur en premi�re ligne peuvent �tre d'une bonne valeur strat�gique, mais un joueur qui perd rapidement ses officiers sup�rieurs se trouve dans une position d�favorable.\n" +
				"\n" +
				"Les �claireurs ont leur raison d'�tre en premi�re ligne, car ils permettent de sonder la puissance de l'adversaire mais si l'on vient � les perdre, on doit joueur ensuite comme un aveugle.");
		
		//initialisation ScrollBar
		jsb = new JScrollPane(jta);
		jsb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsb.setAutoscrolls(true);
		
		
		centerMe(550,650,0);
		
		
		//mise de composant
		PPrincipal.add(img, BorderLayout.NORTH);
		PPrincipal.add(jsb, BorderLayout.CENTER);
		
		add(PPrincipal);
		
		setVisible(true);
		
		System.out.println(jta.getHeight());
	}

}
