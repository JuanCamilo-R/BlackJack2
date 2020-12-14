package clientebj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import comunes.DatosBlackJack;

// TODO: Auto-generated Javadoc
/**
 * The Class ClienteBlackJack. 
 * 
 */
public class ClienteBlackJack extends JFrame implements Runnable{
	//Constantes de Interfaz Grafica
	public static final int WIDTH=800;
	public static final int HEIGHT=650;
	
	//Constantes de conexión con el Servidor BlackJack
	public static final int PUERTO=7372;
	public static final String IP="127.0.0.1";
	
	
	//variables de control del juego
	private String idYo, otroJugador, otroJugador3;
	private int nYo, nOtroJugador, nOtroJugador3,tesoro1=0,tesoro2=0,tesoro3=0,tesoroDealer=0;
	private ClienteBlackJack yoCliente;
	public int getTesoro1() {
		return tesoro1;
	}

	public void setTesoro1(int tesoro1) {
		this.tesoro1 = tesoro1;
	}

	public int getTesoro2() {
		return tesoro2;
	}

	public void setTesoro2(int tesoro2) {
		this.tesoro2 = tesoro2;
	}

	public int getTesoro3() {
		return tesoro3;
	}

	public void setTesoro3(int tesoro3) {
		this.tesoro3 = tesoro3;
	}

	public int getTesoroDealer() {
		return tesoroDealer;
	}

	public void setTesoroDealer(int tesoroDealer) {
		this.tesoroDealer = tesoroDealer;
	}

	private boolean turno;
	private DatosBlackJack datosRecibidos;
	
	//variables para manejar la conexión con el Servidor BlackJack
	private Socket conexion;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	//Componentes Graficos
	private JDesktopPane containerInternalFrames;
	private VentanaEntrada ventanaEntrada;
	private VentanaEspera ventanaEspera;
	private VentanaSalaJuego ventanaSalaJuego;
	
	/**
	 * Instantiates a new cliente black jack.
	 */
	public ClienteBlackJack() {
		yoCliente=this;
		initGUI();
		
		//default window settings
		this.setTitle("Juego BlackJack");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Inits the GUI.
	 */
	private void initGUI() {
		//set up JFrame Container y Layout
        
		//Create Listeners objects
		
		//Create Control objects
		turno=false;
		//Set up JComponents
	this.setBackground(Color.black);
		//this.setBackground(SystemColor.activeCaption);
		containerInternalFrames = new JDesktopPane();
		containerInternalFrames.setOpaque(false);
		this.setContentPane(containerInternalFrames);
		adicionarInternalFrame(new VentanaEntrada(this));
	}
	
	public void adicionarInternalFrame(JInternalFrame nuevoInternalFrame) {
		add(nuevoInternalFrame);
	}
	
	public void iniciarHilo() {
		ExecutorService hiloCliente = Executors.newFixedThreadPool(1);
		hiloCliente.execute(this);
		//Thread hilo = new Thread(this);
		//hilo.start();
	}
	
	public void setIdYo(String id) {
		idYo=id;
	}
	
	private void mostrarMensajes(String mensaje) {
		System.out.println(mensaje);
	}
	
	public void enviarMensajeServidor(String mensaje) {
		try {
			out.writeObject(mensaje);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	public void buscarServidor() {
		mostrarMensajes("Jugador buscando al servidor...");
		
		try {
			//buscar el servidor
			conexion = new Socket(IP,PUERTO);
			//obtener flujos E/S
			out = new ObjectOutputStream(conexion.getOutputStream());
			out.flush();
			in = new ObjectInputStream(conexion.getInputStream());
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mostrarMensajes("Jugador conectado al servidor");
		mostrarMensajes("Jugador estableció Flujos E/S");
		//mandar nombre jugador
		mostrarMensajes("Jugador envio nombre "+idYo);
		enviarMensajeServidor(idYo);
		//procesar comunicación con el ServidorBlackJack
		iniciarHilo();	
	}
	
	@Override
	public void run() {
		//datosRecibidos = new DatosBlackJack();
		// TODO Auto-generated method stub
		//mostrar bienvenida al jugador	
		   System.out.println("Corriendo papá");
			try {
				datosRecibidos = new DatosBlackJack();
				datosRecibidos = (DatosBlackJack) in.readObject();
				System.out.println("Leído");
				if(datosRecibidos.getIdJugadores()[0].equals(idYo)) {
					otroJugador=datosRecibidos.getIdJugadores()[1];
					otroJugador3 = datosRecibidos.getIdJugadores()[2];
					nYo=0;
					nOtroJugador=1;
					nOtroJugador3=2;
					turno=true;
				}else if(datosRecibidos.getIdJugadores()[1].equals(idYo)){
					otroJugador=datosRecibidos.getIdJugadores()[0];
					otroJugador3 = datosRecibidos.getIdJugadores()[2];
					nYo=1;
					nOtroJugador=0;
					nOtroJugador3=2;
				}else {
					otroJugador3 = datosRecibidos.getIdJugadores()[0];
					otroJugador = datosRecibidos.getIdJugadores()[1];
					nYo=2;
					nOtroJugador=1;
					nOtroJugador3=0;
				}
				this.habilitarSalaJuego(datosRecibidos);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//buscando nombre del OtroJugador
			
			//procesar turnos
			
			while(true) {
				try {
					datosRecibidos = new DatosBlackJack();
					datosRecibidos = (DatosBlackJack)in.readObject();
					System.out.println("El estado es "+datosRecibidos.getJugadorEstado());
					if(datosRecibidos.getApuestas()!=null) {
						System.out.println("Apuestas a enviar solicitadas por "+datosRecibidos.getJugador()+":");
						for(int i=0; i<datosRecibidos.getApuestas().length;i++) {
							System.out.println("["+i+","+datosRecibidos.getApuestas()[i]+"]");
						}
					}
					if(datosRecibidos.getJugadorEstado().equals("saliendo")) {
						enviarMensajeServidor("salgo");
						JOptionPane.showMessageDialog(null,"El jugador "+datosRecibidos.getJugador()+" ha decidido retirarse del juego");
						cerrarConexion();
					}
					if(datosRecibidos.getJugadorEstado().equals("reiniciando")) {
						ventanaEspera = new VentanaEspera(idYo);
						add(ventanaEspera);
					}
					if(datosRecibidos.getJugadorEstado().equals("reiniciar")) {
						if(nYo==0) {
							turno=true;
						}
						this.habilitarSalaJuego(datosRecibidos);
					}
					mostrarMensajes("Cliente hilo run recibiendo mensaje servidor ");
					mostrarMensajes(datosRecibidos.getJugador()+" "+datosRecibidos.getJugadorEstado());
					/*if(datosRecibidos.getEstadoJuego()) { //True significa Ronda apuestas
						System.out.println("ENTRO A PINTAR APUESTAS");
						
						ventanaSalaJuego.pintarApuestas(datosRecibidos);
						
					}else {
						ventanaSalaJuego.pintarTurno(datosRecibidos);
					}*/
					if(datosRecibidos.getGanadores() != null && datosRecibidos.getParejas() != null) {
						System.out.print("Entrandoo");
						datosRecibidos.mostrarGanancias();
						ventanaSalaJuego.pintarGanador(datosRecibidos);
						ventanaSalaJuego.repartirGanancias(datosRecibidos);
					}else if(datosRecibidos.getEstadoJuego()) {
						ventanaSalaJuego.pintarApuestas(datosRecibidos);
					}else {
						ventanaSalaJuego.pintarTurno(datosRecibidos);
					}
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
	}

	private void habilitarSalaJuego(DatosBlackJack datosRecibidos) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
				ventanaEspera = (VentanaEspera)containerInternalFrames.getComponent(0);
				ventanaEspera.cerrarSalaEspera();
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e) {
					
				}
				finally {
					ventanaSalaJuego = new VentanaSalaJuego(idYo,otroJugador, otroJugador3,nYo,nOtroJugador,nOtroJugador3,yoCliente);
					ventanaSalaJuego.pintarCartasInicio(datosRecibidos);
					adicionarInternalFrame(ventanaSalaJuego);
					if(turno) {
						ventanaSalaJuego.activarBotones(turno);
					}
				}
			}
			
		});
	}

	private void cerrarConexion() {
		// TODO Auto-generated method stub
		try {
			in.close();
			out.close();
			conexion.close();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
  
	public void setTurno(boolean turno) {
		this.turno=turno;
	}	
}
