/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package clientebj;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;


// TODO: Auto-generated Javadoc
/**
 * The Class Instrucciones.
 * Sirve para darle al usuario un mejor entendimiento de como funciona el juego
 */
public class Instrucciones extends JFrame {
	/*
	 * JLabels y JTextArea para incluir la informacion de las instrucciones
	 */
	/** The escuchas. */
	private Escucha escuchas;
	
	/** The aceptar. */
	private JButton aceptar;
	
	/** The texto 15. */
	private JLabel texto1,imagen10,texto2,imagenBoton,texto5,texto6,texto11,texto14,texto15;
	
	/** The texto 17. */
	private JTextArea texto3,texto4,texto7,texto8,texto9,texto10,texto12,texto13,texto16,texto17,texto18;
	
	/** The imagen. */
	private ImageIcon imagen;
	
	/** The instrucciones yo. */
	private Instrucciones instruccionesYo;
	
	/**
	 * Instantiates a new instrucciones.
	 */
	public Instrucciones() {
		this.setVisible(true);
		instruccionesYo=this;
		escuchas = new Escucha();
		this.setTitle("Instrucciones");
		this.setResizable(false);
		this.setVisible(false);
		this.setBackground(java.awt.Color.green);
		initGUI();
		pack();
		this.setLocationRelativeTo(null);
	
	}
	
	/**
	 * Inits the GUI.
	 */
	public void initGUI() {
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints contraints = new GridBagConstraints();
		getContentPane().setBackground(java.awt.Color.green);
		setBackground(java.awt.Color.green);
		
		texto1 = new JLabel("1. Debes apostar 10 para poder pedir carta o plantar");
		Font fuente=new Font("Courier", Font.BOLD, 18);
		texto1.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=0;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto1,contraints);
		
		imagen10 = new JLabel();
		imagen10.setSize(45, 45);
		imagen10.setBorder(null);
		imagen = new ImageIcon(getClass().getResource("/recursos/ficha3.png"));
		imagen10.setIcon(new  ImageIcon(imagen.getImage().getScaledInstance(45,45, Image.SCALE_AREA_AVERAGING)));
		contraints.gridx=0;
		contraints.gridy=1;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(imagen10,contraints);
		
		
		texto2 = new JLabel("Durante el juego");
		texto2.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=2;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto2,contraints);
		
		Font fuente2=new Font("Courier", Font.BOLD, 15);
		texto3 = new JTextArea("*Si la suma de tus cartas es menor a 16 es obligatorio \n pedir carta, de lo contrario"
				+ "puedes plantar o pedir");
		texto3.setFont(fuente2);
		texto3.setEditable(false);
		texto3.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=3;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto3,contraints);
		
		imagen10 = new JLabel();
		imagen10.setSize(100, 45);
		imagen10.setBorder(null);
		imagen = new ImageIcon(getClass().getResource("/recursos/botones.png"));
		imagen10.setIcon(new  ImageIcon(imagen.getImage().getScaledInstance(100,45, Image.SCALE_AREA_AVERAGING)));
		contraints.gridx=0;
		contraints.gridy=4;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(imagen10,contraints);

		texto4 = new JTextArea("*Si la suma de tus cartas es mayor a 21 significa que   \n volaste,perdiste el juego");
		texto4.setFont(fuente2);
		texto4.setEditable(false);
		texto4.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=5;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto4,contraints);
		
		texto5 = new JLabel("2. Se espera a que todos jueguen hasta el Dealer");
		texto5.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=6;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto5,contraints);
		
		texto6 = new JLabel("Para determinar ganador:");
		texto6.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=7;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto6,contraints);

		texto7 = new JTextArea("*Se tendra en cuenta a los jugadores que no se hayan   \nvolado");
		texto7.setFont(fuente2);
		texto7.setEditable(false);
		texto7.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=8;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto7,contraints);

		texto8 = new JTextArea("*Si la suma de las cartas del Dealer es mayor a la de  \n los demas jugadores sin volarse"
				+ "ha ganado el Dealer");
		texto8.setFont(fuente2);
		texto8.setEditable(false);
		texto8.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=9;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto8,contraints);

		texto9 = new JTextArea("*Los jugadores que tengan la suma de las cartas mayor  \n a las del Dealer ganan");
		texto9.setFont(fuente2);
		texto9.setEditable(false);
		texto9.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=10;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto9,contraints);		

		texto10 = new JTextArea("*La carta As vale 1 u 11 dependiendo de que le conviene\n mas al jugador");
		texto10.setFont(fuente2);
		texto10.setEditable(false);
		texto10.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=11;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto10,contraints);	
		
		texto11 = new JLabel("3. Repartir ganancias");
		texto11.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=12;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto11,contraints);

		texto12 = new JTextArea("*Si el jugador gana con BlackJack se le da 15 mas de lo \n que aposto, en total 25 fichas");
		texto12.setFont(fuente2);
		texto12.setEditable(false);
		texto12.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=13;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto12,contraints);	
                                //                                                       //
		texto13 = new JTextArea("*Si no gano con BlackJack se le da 10 mas de lo que     \n aposto en total 20 fichas");
		texto13.setFont(fuente2);
		texto13.setEditable(false);
		texto13.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=14;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto13,contraints);
                                //                                                       //		
		texto18 = new JTextArea("*Si pierde contra el dealer le debe dar sus 10 fichas   \n apostadas al dealer");
		texto18.setFont(fuente2);
		texto18.setEditable(false);
		texto18.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=15;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto18,contraints);
		
		texto14 = new JLabel("Nota");
		texto14.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=16;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto14,contraints);
			
		texto15 = new JLabel("Los botones iniciar de nuevo y abandonar juego se activan al final");

		texto15.setFont(fuente);
		contraints.gridx=0;
		contraints.gridy=17;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.WEST;
		add(texto15,contraints);

		texto16 = new JTextArea("*Si alguno abandona el juego, se da por terminado el    \n juego para todos");
		texto16.setFont(fuente2);
		texto16.setEditable(false);
		texto16.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=18;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto16,contraints);

		texto17 = new JTextArea("*Para inicar de nuevo todos deben presionar el botoen   \n y se iniciara con el dinero"
				+ "que ganaron en la anterior partida");
		texto17.setFont(fuente2);
		texto17.setEditable(false);
		texto17.setBackground(Color.green);
		contraints.gridx=0;
		contraints.gridy=19;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(texto17,contraints);
		
		aceptar = new JButton("Aceptar");
		aceptar.setEnabled(true);
		aceptar.addActionListener(escuchas);
		contraints.gridx=0;
		contraints.gridy=20;
		contraints.gridwidth=1;
		contraints.gridheight=1;
		contraints.anchor = contraints.CENTER;
		add(aceptar,contraints);
	}
	
/**
 * The Class Escucha.
 */
public class Escucha implements ActionListener {
		
		/**
		 * Action performed.
		 *
		 * @param arg0 the arg 0
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource() == aceptar) {
				instruccionesYo.setVisible(false);
			}
		}
	}
	
	
}
