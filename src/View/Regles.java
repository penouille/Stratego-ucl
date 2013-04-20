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
		super("Règles");
		
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
		jta.setText("Règles du jeu : Stratego \n" +
				"\n" +
				"\n" +
				"Les rangs :\n" +
				"\n" +
				"L'espion tombe devant les éclaireurs, ceux-ci tombent devant les démineurs, les démineurs tombent devant les sergents, les sergents devant les lieutenants, etc. Chaque pièce, quoi que soit son rang, tombe contre une bombe. Seul un démineur est en mesure de détruire une bombe, après quoi elle doit être retirée du jeu.\n" +
				"\n" +
				"Le drapeau, qui ne peut pas être déplacé, tombe devant n'importe quelle pièce de l'adversaire. Le maréchal, le plus haut en grade, tombe seulement devant l'espion, s'il est pris par le dernier. Quand, au contraire, le maréchal touche à l'espion, ce dernier tombe.\n" +
				"\n" +
				"Si un joueur ne peut plus déplacer aucune de ses pièces, par exemple lorsqu'il n'a plus que des bombes et le drapeau, il doit capituler. C'est le joueur qui réussit à s'emparer du drapeau de son adversaire, qui est le gagnant.\n" +
				"\n" +
				"Seul l'éclaireur a le droit en un coup, de sauter par dessus plus d'une case libre.\n" +
				"\n" +
				"\n" +
				"\n" +
				"Disposition des pièces :\n" +
				"\n" +
				"Vous devez disposer vos pièces stratégiquement de façon à ce que l'attaque et la défense se déploient en faveur de chaque joueur qui les déplace.\n" +
				"\n" +
				/*"Il y a deux camps: les rouges et les bleus. Étant donné que ce sont les rouges qui ouvrent le jeu, on tire d'abord au sort pour déterminer à quel joueur cette couleur sera attribuée.\n" +*/
				"Chaque joueur place ses pièces dans chaque case de la moitié du terrain qui lui est attribuée, mais de telle façon que l'adversaire ne voit pas ces pièces, afin que ce dernier ignore la valeur de chacune des pièces placées.\n" +
				"\n" +
				"Les deux rangs de cases du milieu restent inoccupés, de même que les deux lacs à travers lesquels les deux armées ne peuvent pas se mouvoir au cours du jeu.\n" +
				"\n" +
				"La disposition des pièces doit être étudiée d'après les règles du jeu d'une part, et suivant l'inspiration stratégique du joueur d'autre part.\n" +
				"\n" +
				"\n" +
				"\n" +
				"Tour de jeu : \n" +
				"\n" +
				"A tour de rôle, les joueurs peuvent déplacer une de leurs pièces d'une case à la fois, soit en avant ou en arrière, soit latéralement à gauche ou à droite.\n" +
				"\n" +
				"Le drapeau et les bombes ne peuvent pas être déplacés et restent donc pendant tout le jeu sur les cases où ils ont été placés au début. Lorsque les bleus et les rouges se sont rapprochés (les pièces se trouvant soit face à face, soit à côté les unes des autres) le joueur peut prendre une pièce adverse en la touchant. Les rangs des deux pièces sont alors révélés. Si le rang de la pièce qui a engagé le combat est plus élevé que celui de la pièce adverse, alors cette dernière tombe, et l'autre pièce prend sa place. Si au contraire, son rang est plus faible, alors c'est elle qui tombe, et la pièce adverse reste à sa place. Si les deux pièces sont de rangs égaux, elles tombent toutes les deux.\n" +
				"\n" +
				"Si toutes les pièces se trouvent rapprochées, il n'est pas nécessaire d'attaquer immédiatement; l'initiative de l'attaque peut venir de chacun des deux joueurs.\n" +
				"\n" +
				"\n" +
				"\n" +
				"Tactique : \n" +
				"\n" +
				"D'après les règles du jeu ci-dessus indiquées, il ressort que la mise en place des pièces au début du jeu peut être déterminante pour l'issue de la partie. Il sera donc raisonnable d'entourer le drapeau de quelques bombes, afin de bien pouvoir le défendre. Pour tromper l'adversaire, on fera bien, toutefois, de disposer aussi deux ou trois bombes à une certaine distance de ce drapeau. Quelques pièces d'un grade supérieur en première ligne peuvent être d'une bonne valeur stratégique, mais un joueur qui perd rapidement ses officiers supérieurs se trouve dans une position défavorable.\n" +
				"\n" +
				"Les éclaireurs ont leur raison d'être en première ligne, car ils permettent de sonder la puissance de l'adversaire mais si l'on vient à les perdre, on doit joueur ensuite comme un aveugle.");
		
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
