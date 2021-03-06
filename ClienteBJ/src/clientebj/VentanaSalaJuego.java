/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package clientebj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javafx.util.*;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import comunes.Baraja;
import comunes.Carta;
import comunes.DatosBlackJack;


// TODO: Auto-generated Javadoc
/**
 * The Class VentanaSalaJuego.
 */
public class VentanaSalaJuego extends JInternalFrame {
		
		/** The Constant DINERO_INICIAL. 
		 * Dinero con el que empiezan todos los jugadores inluido el Dealer
		 *  */
		public static final int DINERO_INICIAL=100;
		
		/** Paneles de los jugadores. */
		private PanelJugador dealer, yo, jugador2, jugador3;
		
		/** Mensajes para ver el historial del juego. */
		private JTextArea areaMensajes;
		
		/** 
		 * Botones para el desarrollo del juego
		 * . */
		private JButton pedir, plantar, apostar,confirmarApuesta, reiniciarJuego, abandonarJuego,instrucciones;
		
		/** Labels para mostrar en el JFrame el dinero de cada jugador y apuestas */
		private JLabel apuesta1, apuesta2, apuesta3, apuestaDealer, dinero1, dinero2, dinero3, dineroDealer;
		
		/** Labels para mostrar la palabra "Apuesta" en cada Panel */
		private JLabel palabraApuesta1, palabraApuesta2, palabraApuesta3, palabraApuestaDealer;
		
		/** Labels para mostrar la palabra "Dinero" en cada Panel */
		private JLabel palabraDinero1, palabraDinero2, palabraDinero3, palabraDineroDealer;
		
		/** Paneles donde estan los jugadores y otros para organizar la GUI*/
		private JPanel panelYo, panelBotones, yoFull, panelDealer,panelJugador2, panelJugador3, panelControles;
		
		/** Paneles para organizar la GUI */
		private JPanel paneltextoDealer,paneltexto1,paneltexto2,paneltexto3;
		
		/** The baraja nueva. */
		private Baraja barajaNueva;
		
		/** Array para mostrar cuales fueron las ganancias de cada jugador. */
		private ArrayList<Pair<String, Integer>> parejaNombreGanancia;
		
		/**  Array para tener la suma de cada mano de los jugadores. */
		private int[] valorManos;
		
		/** JFrame para darle instrucciones al usuario */
		private Instrucciones instrucciones2;
		
		/** Nombres de los jugadores */
		private String yoId, jugador2Id, jugador3Id;
		
		/** Turno de cada jugador */
		private int yoN,jugador2N,jugador3N;
		
		/** The constraints. */
		private GridBagConstraints constraints;
		
		/** The icono. */
		private JLabel icono;
		
		/** The cantidad apuesta. */
		private int cantidadApuesta;
		
		/** The aposto. */
		private boolean aposto;
		
		/** The yo clase. */
		private JInternalFrame yoClase;
		
		/** The yo cliente. */
		private ClienteBlackJack yoCliente;
		
		/** The imagen. */
		private ImageIcon imagen;
		
		/** The escucha. */
		private Escucha escucha;
		
		/**
		 * Instantiates a new ventana sala juego.
		 *
		 * @param yoId the yo id
		 * @param jugador2Id the jugador 2 id
		 * @param jugador3Id the jugador 3 id
		 * @param yoN the yo N
		 * @param jugador2N the jugador 2 N
		 * @param jugador3N the jugador 3 N
		 * @param cliente the cliente
		 */
		public VentanaSalaJuego(String yoId, String jugador2Id, String jugador3Id, int yoN, int jugador2N, int jugador3N, ClienteBlackJack cliente) {
			this.yoId = yoId;
			yoClase = this;
			yoCliente=cliente;
			this.jugador2Id = jugador2Id;
			this.jugador3Id = jugador3Id;
			this.yoN= yoN;
			this.jugador2N = jugador2N;
			this.jugador3N = jugador3N;
			parejaNombreGanancia = new ArrayList<Pair<String, Integer>>();
			barajaNueva = new Baraja();	
			aposto = false;
			cantidadApuesta = 0;
			instrucciones2 = new  Instrucciones();
			initGUI();
			
			//default window settings
			this.setTitle("Sala de juego BlackJack - Jugador: "+yoId);
			this.pack();
			this.setLocation((ClienteBlackJack.WIDTH-this.getWidth())/2, 
			         (ClienteBlackJack.HEIGHT-this.getHeight())/2);
			this.setResizable(false);
			this.show();
		}

		/**
		 * Inits the GUI.
		 */
		private void initGUI() {
			// TODO Auto-generated method stub

		
			escucha = new Escucha();
			//Configuracion de la ventana
			this.getContentPane().setLayout(new GridBagLayout());
			this.getContentPane().setBackground(Color.GREEN);
			
			constraints = new GridBagConstraints();
			
			//Informacion relacionada con el Dealer
			palabraDineroDealer = new JLabel("Dinero: ");
			palabraDineroDealer.setPreferredSize(new Dimension(50,20));
			palabraApuestaDealer = new JLabel("      Apuesta: ");
			palabraApuestaDealer.setPreferredSize(new Dimension(80,20));
			//getTesoroDealer hace referencia a las ganancias que ha obtenido el Dealer
			dineroDealer = new JLabel(String.valueOf(yoCliente.getTesoroDealer()));
			if(yoCliente.getTesoroDealer()==0)
			dineroDealer = new JLabel(String.valueOf(DINERO_INICIAL));
			dineroDealer.setPreferredSize(new Dimension(50,20));
			apuestaDealer = new JLabel("0");
			apuestaDealer.setPreferredSize(new Dimension(50,20));
			paneltextoDealer = new JPanel();
			paneltextoDealer.setBackground(Color.GREEN);
			paneltextoDealer.setLayout(new GridBagLayout());
			
			panelDealer = new JPanel();
			panelDealer.setBackground(Color.GREEN);
			panelDealer.setLayout(new GridBagLayout());
			dealer = new PanelJugador("Dealer");
			dealer.setBackground(Color.GREEN);
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelDealer.add(dealer,constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltextoDealer.add(palabraDineroDealer,constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltextoDealer.add(dineroDealer,constraints);
			
			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltextoDealer.add(palabraApuestaDealer, constraints);
			
			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltextoDealer.add(apuestaDealer, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth = 4;
			constraints.gridheight = 1;
			panelDealer.add(paneltextoDealer,constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			add(panelDealer,constraints);		
			
			//Informacion relacionada con el segundo jugador
			
			palabraDinero2 = new JLabel("Dinero: ");
			palabraDinero2.setPreferredSize(new Dimension(50,20));
			palabraApuesta2 = new JLabel("      Apuesta: ");
			palabraApuesta2.setPreferredSize(new Dimension(80,20));
			dinero2 = new JLabel(String.valueOf(yoCliente.getTesoro2()));
			if(yoCliente.getTesoro2()==0)
			dinero2 = new JLabel(String.valueOf(DINERO_INICIAL));
			dinero2.setPreferredSize(new Dimension(50,20));
			apuesta2 = new JLabel("0");
			apuesta2.setPreferredSize(new Dimension(50,20));
			paneltexto2 = new JPanel();
			
			paneltexto2.setLayout(new GridBagLayout());
			paneltexto2.setBackground(Color.GREEN);
			panelJugador2 = new JPanel();
			panelJugador2.setBackground(Color.GREEN);
			panelJugador2.setLayout(new GridBagLayout());
			jugador2= new PanelJugador(jugador2Id);
			jugador2.setBackground(Color.GREEN);
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelJugador2.add(jugador2,constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto2.add(palabraDinero2,constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto2.add(dinero2,constraints);
			
			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto2.add(palabraApuesta2, constraints);
			
			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto2.add(apuesta2, constraints);
		
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth = 4;
			constraints.gridheight = 1;
			panelJugador2.add(paneltexto2,constraints);
			
			constraints.gridx = 2;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			add(panelJugador2,constraints);	
			
			
			//Informacion relacionada con el tercer jugador
			palabraDinero3 = new JLabel("Dinero: ");
			palabraDinero3.setPreferredSize(new Dimension(50,20));
			palabraApuesta3 = new JLabel("      Apuesta: ");
			palabraApuesta3.setPreferredSize(new Dimension(80,20));
			dinero3 = new JLabel(String.valueOf(yoCliente.getTesoro3()));
			if(yoCliente.getTesoro3()==0)
			dinero3 = new JLabel(String.valueOf(DINERO_INICIAL));
			dinero3.setPreferredSize(new Dimension(50,20));
			apuesta3 = new JLabel("0");
			apuesta3.setPreferredSize(new Dimension(50,20));
			paneltexto3 = new JPanel();
			paneltexto3.setBackground(Color.GREEN);
			paneltexto3.setLayout(new GridBagLayout());
			
			panelJugador3 = new JPanel();
			panelJugador3.setLayout(new GridBagLayout());
			jugador3 = new PanelJugador(jugador3Id);
			jugador3.setBackground(Color.GREEN);
			panelJugador3.setBackground(Color.GREEN);
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelJugador3.add(jugador3,constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto3.add(palabraDinero3,constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto3.add(dinero3,constraints);
			
			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto3.add(palabraApuesta3, constraints);
			
			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto3.add(apuesta3, constraints);
		
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth = 4;
			constraints.gridheight = 1;
			panelJugador3.add(paneltexto3,constraints);
			
			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			add(panelJugador3, constraints);
			
			//Historial del juego
			areaMensajes = new JTextArea(8,18);
			areaMensajes.setBackground(Color.white);
			System.out.println("Tamano areaMensajes: "+areaMensajes.getSize().width+","+areaMensajes.getSize().height);
			JScrollPane scroll = new JScrollPane(areaMensajes);
			System.out.println("Tamano scroll: "+scroll.getSize().width+","+scroll.getSize().height);
			Border blackline;
			blackline = BorderFactory.createLineBorder(Color.black);
			TitledBorder bordes;
			bordes = BorderFactory.createTitledBorder(blackline, "Area de Mensajes");
	        bordes.setTitleJustification(TitledBorder.CENTER);
	       
			scroll.setBorder(bordes);
			areaMensajes.setEditable(false);

			scroll.getViewport().setOpaque(false);
			scroll.setBackground(Color.white);
			scroll.setOpaque(false);
			scroll.setSize(new Dimension(225,150));
			scroll.setMinimumSize(scroll.getSize());
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			add(scroll,constraints);
			System.out.println("Tamano areaMensajes: "+areaMensajes.getSize().width+","+areaMensajes.getSize().height);
			System.out.println("Tamano scroll: "+scroll.getViewport().getSize().width+","+scroll.getViewport().getSize().height);
			
			/**
			 * Se agregan botones como reiniciarJuego,abandonar el juego e instrucciones
			 */
			panelControles = new JPanel();
			panelControles.setLayout(new GridBagLayout());
			panelControles.setPreferredSize(new Dimension(210,50));//170  50
			panelControles.setMinimumSize(panelControles.getPreferredSize());
			panelControles.setOpaque(false);
			
			
			reiniciarJuego = new JButton("Iniciar nueva ronda");
			reiniciarJuego.setPreferredSize(new Dimension(160,20));
			reiniciarJuego.setMinimumSize(reiniciarJuego.getPreferredSize());
			reiniciarJuego.setMaximumSize(panelControles.getSize());
			reiniciarJuego.setEnabled(false);
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelControles.add(reiniciarJuego,constraints);
			
			abandonarJuego = new JButton("Abandonar el juego");
			abandonarJuego.setPreferredSize(new Dimension(160,20));
			abandonarJuego.setMinimumSize(abandonarJuego.getPreferredSize());
			abandonarJuego.setEnabled(false);
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelControles.add(abandonarJuego,constraints);

			instrucciones = new JButton("Instrucciones");
			instrucciones.setPreferredSize(new Dimension(160,20));
			instrucciones.addActionListener(escucha);
			instrucciones.setMinimumSize(instrucciones.getPreferredSize());
			instrucciones.setEnabled(true);
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelControles.add(instrucciones,constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			constraints.anchor = constraints.NORTH;
			add(panelControles,constraints);
			
			panelYo = new JPanel();
			panelYo.setBackground(Color.GREEN);
			panelYo.setLayout(new BorderLayout());
			yo = new PanelJugador(yoId);
			yo.setBackground(Color.GREEN);
			panelYo.add(yo);
				
			pedir = new JButton("Carta");
			pedir.setEnabled(false);
			pedir.addActionListener(escucha);
			plantar = new JButton("Plantar");
			plantar.setEnabled(false);
			plantar.addActionListener(escucha);
			panelBotones = new JPanel();
			panelBotones.add(pedir);
			panelBotones.add(plantar);
			
			confirmarApuesta = new JButton("Confirmar apuesta");
			confirmarApuesta.setPreferredSize(new Dimension(140,20));
			confirmarApuesta.setMinimumSize(confirmarApuesta.getPreferredSize());
			palabraDinero1 = new JLabel("Dinero: ");
			palabraDinero1.setPreferredSize(new Dimension(50,20));
			palabraApuesta1 = new JLabel("      Apuesta: ");
			palabraApuesta1.setPreferredSize(new Dimension(80,20));
			dinero1 = new JLabel(String.valueOf(yoCliente.getTesoro1()));
			if(yoCliente.getTesoro1()==0)
			dinero1 = new JLabel(String.valueOf(DINERO_INICIAL));
			dinero1.setPreferredSize(new Dimension(50,20));
			apuesta1 = new JLabel("0");
			apuesta1.setPreferredSize(new Dimension(50,20));
			
			//Datos referentes el jugador que esta jugando
			paneltexto1 = new JPanel();
			paneltexto1.setBackground(Color.GREEN);
			paneltexto1.setLayout(new GridBagLayout());
			
			yoFull = new JPanel();
			yoFull.setBackground(Color.GREEN);
			yoFull.setLayout(new GridBagLayout());
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth =2;
			constraints.gridheight = 1;
			yoFull.add(panelYo, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth =1;
			constraints.gridheight = 1;
			panelBotones.setBackground(Color.GREEN);
			yoFull.add(panelBotones, constraints);
			
			apostar = new JButton();
			apostar.setSize(45, 45);
			apostar.setBorder(null);
			apostar.setContentAreaFilled(false);
			imagen = new ImageIcon(getClass().getResource("/recursos/ficha3.png"));
			apostar.setIcon(new  ImageIcon(imagen.getImage().getScaledInstance(45,45, Image.SCALE_AREA_AVERAGING)));
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.gridwidth =1;
			constraints.gridheight = 1;
			yoFull.add(apostar, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto1.add(palabraDinero1,constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto1.add(dinero1,constraints);
			
			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto1.add(palabraApuesta1, constraints);
			
			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto1.add(apuesta1, constraints);
			
			constraints.gridx = 5;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto1.add(confirmarApuesta, constraints);
		
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth = 4;
			constraints.gridheight = 1;
			yoFull.add(paneltexto1,constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth =2;
			constraints.gridheight = 2;
			constraints.anchor = constraints.SOUTHWEST;
			add(yoFull,constraints);	
			
			//Icono del BlackJack
			icono = new JLabel();
			imagen = new ImageIcon(getClass().getResource("/recursos/imagen1.png"));
			icono.setIcon(new  ImageIcon(imagen.getImage().getScaledInstance(50,50, Image.SCALE_AREA_AVERAGING)));
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.gridwidth =1;
			constraints.gridheight = 1;
			constraints.anchor = constraints.CENTER;
			add(icono,constraints);
	
		}
		
		/**
		 * Activar botones.
		 * Se activan los botones de carta y plantar si el turno es el del jugador
		 * @param turno the turno
		 */
		public void activarBotones(boolean turno) {
			pedir.setEnabled(turno);
			plantar.setEnabled(turno);
			if(turno && !aposto) {
				apostar.setEnabled(turno);
				confirmarApuesta.setEnabled(turno);
				apostar.addActionListener(escucha);
				confirmarApuesta.addActionListener(escucha);
			}else{
				apostar.setEnabled(false);
				confirmarApuesta.setEnabled(false);
				apostar.removeActionListener(escucha);
				confirmarApuesta.removeActionListener(escucha);
			}
			
		}
		
		/**
		 * Colocar imagen carta
		 *Se crea una nueva baraja para guardar la que fue creada por el servidor y as� poderle asignar 
		 *una imagen y mostrarla en pantalla
		 *Se compara que la baraja sea la misma con la funcion revisarCarta de la clase Baraja
		 * @param datosRecibidos the datos recibidos
		 * @return the carta
		 */
		//Coloca la imagen a la carta pedida por el usuario (dada por el servidor)
		public Carta colocarImagenCarta(DatosBlackJack datosRecibidos) {
			barajaNueva = new Baraja();
			Carta carta = datosRecibidos.getCarta();
			System.out.println("Carta dada por el servidor: "+carta);
			return barajaNueva.revisarCarta(carta);
		}
		
		/**
		 * Asignar cartas.
		 * Retorna la Baraja (mazo) dependiendo del jugador
		 * Se usa de parametro para PintarCartasInicio
		 *	
		 * @param manoJugador the mano jugador
		 * @return the array list
		 */
		public ArrayList<Carta> asignarCartas(ArrayList<Carta> manoJugador) {
			
			ArrayList<Carta> manoJugadorAuxiliar = new ArrayList<Carta>();
			System.out.println(manoJugador);
			for(int i = 0; i < manoJugador.size(); i++) {
				manoJugadorAuxiliar.add(barajaNueva.revisarCarta(manoJugador.get(i)));		
			}
			return manoJugadorAuxiliar;
		}
		
		/**
		 * Pintar cartas inicio.
		 * Recibe los datos que recibio el Cliente desde el servidor para mostrar
		 * las cartas en pantalla
		 *
		 * @param datosRecibidos the datos recibidos
		 */
		public void pintarCartasInicio(DatosBlackJack datosRecibidos) {
			valorManos = datosRecibidos.getValorManos();
			
			if(datosRecibidos.getIdJugadores()[0].equals(yoId)) {
				yo.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador1()));
				jugador2.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador2()));
				jugador3.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador3()));
			}else if(datosRecibidos.getIdJugadores()[1].equals(yoId)){
				yo.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador2()));
				jugador2.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador1()));
				jugador3.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador3()));
			}else {
				yo.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador3()));
				jugador2.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador2()));
				jugador3.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador1()));
			}
			dealer.pintarCartasInicio(asignarCartas(datosRecibidos.getManoDealer()));
			if(!datosRecibidos.getMensaje().equals(""))
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");
		}
		
		/**
		 * Pintar turno.
		 * Le notifica a los demas jugadores las movidas de los otros y quien sigue
		 * @param datosRecibidos the datos recibidos
		 */
		public void pintarTurno(DatosBlackJack datosRecibidos) {
			valorManos = datosRecibidos.getValorManos();
			if(!datosRecibidos.getMensaje().equals(""))
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");	
			ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
			
			if(datosRecibidos.getJugador().contentEquals(yoId)){
				if(datosRecibidos.getJugadorEstado().equals("iniciar")) {
					activarBotones(true);
				}else {
					if(datosRecibidos.getJugadorEstado().equals("plant�") ){
						cliente.setTurno(false);
					}else {
						yo.dibujarCarta(colocarImagenCarta(datosRecibidos));
						if(datosRecibidos.getJugadorEstado().equals("vol�")) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									activarBotones(false);
									cliente.setTurno(false);
								}});			
						      }
						}
					} 
			 }else {//movidas de los otros jugadores
					if(datosRecibidos.getJugador().equals(jugador2Id)) {
						//mensaje para PanelJuego jugador2
						if(datosRecibidos.getJugadorEstado().equals("sigue")||
						   datosRecibidos.getJugadorEstado().equals("vol�")) {
							jugador2.dibujarCarta(colocarImagenCarta(datosRecibidos));
						}
						//mensaje para PanelJuego jugador3
					}else if(datosRecibidos.getJugador().equals(jugador3Id)) {
						if(datosRecibidos.getJugadorEstado().equals("sigue")||
						   datosRecibidos.getJugadorEstado().equals("vol�")) {
							jugador3.dibujarCarta(colocarImagenCarta(datosRecibidos));
						}
					}
					else {
						//mensaje para PanelJuego dealer
						if(datosRecibidos.getJugadorEstado().equals("sigue") ||
						   datosRecibidos.getJugadorEstado().equals("vol�")	||
						   datosRecibidos.getJugadorEstado().equals("plant�")) {
							dealer.dibujarCarta(colocarImagenCarta(datosRecibidos));
						}
					}
					
				}
						 	
		}
		
		/**
		 * Repartir ganancias.
		 * Funcion que se ejecuta al final, cuando se tiene el ganador
		 * Se le da a cada jugador lo que gano y se muestra en sus Labels de Dinero
		 *
		 * @param datosRecibidos the datos recibidos
		 */
		public void repartirGanancias(DatosBlackJack datosRecibidos) {

			parejaNombreGanancia = datosRecibidos.getParejas();
			if(!datosRecibidos.getMensaje().equals(""))
				//Muestro el mensaje en el JTextArea
			areaMensajes.append(datosRecibidos.getMensajeGanancias());

			reiniciarJuego.setEnabled(true);
			abandonarJuego.setEnabled(true);
			reiniciarJuego.addActionListener(escucha);
			abandonarJuego.addActionListener(escucha);


			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//Se actualizan los labels
					if(parejaNombreGanancia.size() == 2 && parejaNombreGanancia.get(0).getKey().equals("null")) {
						dinero1.setText(String.valueOf(Integer.parseInt(dinero1.getText())+Integer.parseInt(apuesta1.getText())));
						dinero2.setText(String.valueOf(Integer.parseInt(dinero2.getText())+Integer.parseInt(apuesta2.getText())));
						dinero3.setText(String.valueOf(Integer.parseInt(dinero3.getText())+Integer.parseInt(apuesta3.getText())));
						dineroDealer.setText(String.valueOf(Integer.parseInt(dineroDealer.getText())+Integer.parseInt(apuestaDealer.getText())));
					}else {
						
						for(int i = 0; i < parejaNombreGanancia.size(); i++) {
							if(parejaNombreGanancia.get(i).getKey().equals(yoId)) {
								yoClase.pack();
								int cantidadTotal = Integer.parseInt(dinero1.getText())+parejaNombreGanancia.get(i).getValue();
								dinero1.setText(String.valueOf(cantidadTotal));
								yoClase.pack();
							}else if(parejaNombreGanancia.get(i).getKey().equals(jugador2Id)) {
								yoClase.pack();
								int cantidadTotal = Integer.parseInt(dinero2.getText())+parejaNombreGanancia.get(i).getValue();
								dinero2.setText(String.valueOf(cantidadTotal));
								yoClase.pack();
							}else if(parejaNombreGanancia.get(i).getKey().equals(jugador3Id)) {
								yoClase.pack();
								int cantidadTotal = Integer.parseInt(dinero3.getText())+parejaNombreGanancia.get(i).getValue();
								dinero3.setText(String.valueOf(cantidadTotal));
								yoClase.pack();
							}else if(parejaNombreGanancia.get(i).getKey().equals("dealer")){
								System.out.println("Ganancia4: "+parejaNombreGanancia.get(i).getValue());
								yoClase.pack();
								int cantidadTotal = Integer.parseInt(dineroDealer.getText())+parejaNombreGanancia.get(i).getValue();
								dineroDealer.setText(String.valueOf(cantidadTotal));
								yoClase.pack();
							}
						}
					}
				}});
		}
		
		/**
		 * Pintar apuestas.
		 * Para mostrar en el JtextArea y Labels lo que ha apostado acada jugador
		 * @param datosRecibidos the datos recibidos
		 */
		public void pintarApuestas(DatosBlackJack datosRecibidos) {
			System.out.println("APUESTAS: ");
			int[] apuestas = datosRecibidos.getApuestas();
			for(int i = 0; i < 3; i++) {
				System.out.println("Apuesta #"+(i+1)+" "+apuestas[i]);
			}
			if(!datosRecibidos.getMensaje().equals(""))
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");	
			ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
			
			if(datosRecibidos.getJugador().contentEquals(yoId)){
				if(datosRecibidos.getJugadorEstado().equals("iniciar")) {
					activarBotones(true);
				}else {
					if(datosRecibidos.getJugadorEstado().equals("plant�") ){
						cliente.setTurno(false);
					}else {
						System.out.println("Apuesta[0] = "+datosRecibidos.getApuestas()[0]);
						//yo.dibujarCarta(datosRecibidos.getCarta());
						apostar(String.valueOf(datosRecibidos.getApuestas()[yoN]), "1");
						if(datosRecibidos.getJugadorEstado().equals("vol�")) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									activarBotones(false);
									cliente.setTurno(false);
								}});			
						      }
						}
					} 
			 }else {//movidas de los otros jugadores
					if(datosRecibidos.getJugador().equals(jugador2Id)) {
						//mensaje para PanelJuego jugador2
						if(datosRecibidos.getJugadorEstado().equals("apuesta")) {
							System.out.println("Apuesta["+jugador2N+"] = "+datosRecibidos.getApuestas()[jugador2N]);
							apostar(String.valueOf(datosRecibidos.getApuestas()[jugador2N]), "2");
							
						}
						//mensaje para PanelJuego jugador3
					}else if(datosRecibidos.getJugador().equals(jugador3Id)) {
						if(datosRecibidos.getJugadorEstado().equals("apuesta")) {
							System.out.println("Apuesta["+jugador3N+"] = "+datosRecibidos.getApuestas()[jugador3N]);
							
							apostar(String.valueOf(datosRecibidos.getApuestas()[jugador3N]),"3");
						}
					}
					else {
						//mensaje para PanelJuego dealer
						if(datosRecibidos.getJugadorEstado().equals("apuesta")) {
							apostar("10", "dealer");
						}
					}
					
				}
		}
		
		/**
		 * Pintar ganador.
		 * Muestra el ganador en el JTextArea
		 * @param datosRecibidos the datos recibidos
		 */
		public void pintarGanador(DatosBlackJack datosRecibidos) {
			if(!datosRecibidos.getMensaje().equals(""))
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");	
			ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();	
		}
		
		/**
		 * Apostar.
		 * Funcion para actualizar la GUI cuando un jugador apuesta
		 * @param valor the valor
		 * @param jugador the jugador
		 */
		private void apostar(String valor, String jugador) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(jugador.equals("1")) {
						yoClase.pack();
						palabraDinero1.setPreferredSize(new Dimension(50,20));
						apuesta1.setPreferredSize(new Dimension(50,20));
						apuesta1.setText(valor);
						palabraDinero1.setPreferredSize(new Dimension(50,20));
						dinero1.setPreferredSize(new Dimension(50,20));
						dinero1.setText(Integer.parseInt(dinero1.getText())-Integer.parseInt(valor)+"");
						yoClase.pack();
					}else if(jugador.equals("2")) {
						yoClase.pack();
						palabraDinero2.setPreferredSize(new Dimension(50,20));
						apuesta2.setPreferredSize(new Dimension(50,20));
						apuesta2.setText(valor);
						palabraDinero2.setPreferredSize(new Dimension(50,20));
						apuesta2.setPreferredSize(new Dimension(50,20));
						dinero2.setText(Integer.parseInt(dinero2.getText())-Integer.parseInt(valor)+"");
						yoClase.pack();
					}else if(jugador.equals("3")) {
						yoClase.pack();
						palabraDinero3.setPreferredSize(new Dimension(50,20));
						apuesta3.setPreferredSize(new Dimension(50,20));
						apuesta3.setText(valor);
						palabraDinero3.setPreferredSize(new Dimension(50,20));
						apuesta3.setPreferredSize(new Dimension(50,20));
						dinero3.setText(Integer.parseInt(dinero3.getText())-Integer.parseInt(valor)+"");
						yoClase.pack();
					}else {
						apuestaDealer.setText(valor);
						dineroDealer.setText(Integer.parseInt(dineroDealer.getText())-Integer.parseInt(valor)+"");
					}
				}});
		}
	   
	   /**
   	 * Enviar datos al servidor
   	 *
   	 * @param mensaje the mensaje
   	 */
   	private void enviarDatos(String mensaje) {
			// TODO Auto-generated method stub
		  ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
		  cliente.enviarMensajeServidor(mensaje);
		}
		   
	  
	   
	   /**
   	 * The Class Escucha.
   	 */
   	private class Escucha implements ActionListener{
		   
		/**
		 * Action performed.
		 *
		 * @param actionEvent the action event
		 */
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// TODO Auto-generated method stub
			if(actionEvent.getSource()==pedir) {
				//enviar pedir carta al servidor
				if(aposto) {
					enviarDatos("pedir");
				}else {
					JOptionPane.showMessageDialog(null,"Debes apostar primero!");
				}
								
			}else if(actionEvent.getSource() == plantar) {
				//enviar plantar al servidor
				if(aposto) {
					if(valorManos[yoN] > 16) {
						System.out.println("VALOR MANO: "+valorManos[0]);
						enviarDatos("plantar");
						activarBotones(false);
					}else {
						JOptionPane.showMessageDialog(null, "No puedes plantar, debes pedir carta.");
					}
						
				}else {
					JOptionPane.showMessageDialog(null,"Debes apostar primero!");
				}
				
				//Ficha 10 de apostar
			}else if(actionEvent.getSource() == apostar) {
				cantidadApuesta = 10;
				apuesta1.setPreferredSize(new Dimension(50,20));
				apuesta1.setText((Integer.parseInt(apuesta1.getText())+10)+"");
				yoClase.pack();
				apostar.setEnabled(false);
				apostar.removeActionListener(escucha);
			}else if(actionEvent.getSource() == confirmarApuesta){
				//Confirma la apuesta
				if(cantidadApuesta > 0) {
					yoClase.pack();
					aposto = true;
					enviarDatos(apuesta1.getText());
					cantidadApuesta = 0;
					confirmarApuesta.setEnabled(false);
					confirmarApuesta.removeActionListener(escucha);
					apostar.setEnabled(false);
					apostar.removeActionListener(escucha);
					yoClase.pack();
				}else {
					JOptionPane.showMessageDialog(null,"Debes apostar primero!");
				}
			
				//Le envia datos al servidor de que alguien abandono la partida, entonces el juego se termina
			}else if(actionEvent.getSource() == abandonarJuego){
				enviarDatos("abandonar");
			}
			//Se actualizan los Labels del dinero con las ganancias que se obtuvieron en el juego anterior
			//y se avisa al servidor que se iniciara una nueva ronda
			else if(actionEvent.getSource() == reiniciarJuego) {
				yoCliente.setTesoro1(Integer.parseInt(dinero1.getText()));
				yoCliente.setTesoro2(Integer.parseInt(dinero2.getText()));
				yoCliente.setTesoro3(Integer.parseInt(dinero3.getText()));
				yoCliente.setTesoroDealer(Integer.parseInt(dineroDealer.getText()));
				enviarDatos("reiniciar");
				dispose();
			}
			//JFrame con las instrucciones
			if (actionEvent.getSource() == instrucciones) {
				instrucciones2.setVisible(true);
			}
		}
	   }
	 
}
