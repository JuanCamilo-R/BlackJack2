/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package servidorbj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import comunes.Baraja;
import comunes.Carta;
import comunes.DatosBlackJack;
import javafx.util.Pair;

/* Clase encargada de realizar la gesti�n del juego, esto es, el manejo de turnos y estado del juego.
 * Tambi�n gestiona al jugador Dealer. 
 * El Dealer tiene una regla de funcionamiento definida:
 * Pide carta con 16 o menos y Planta con 17 o mas.
 */
public class ServidorBJ implements Runnable {
	// constantes para manejo de la conexion.



	public static final int PUERTO = 7371;

	public static final String IP = "127.0.0.1";
	public static final int LONGITUD_COLA = 3;

	// variables para funcionar como servidor
	private ServerSocket server;
	private Socket conexionJugador;
	private ArrayList<String> ganador;
	// variables para manejo de hilos
	private ExecutorService manejadorHilos;
	private Lock bloqueoJuego;
	private Condition esperarInicio, esperarTurno, finalizar;
	private Jugador[] jugadores;
	// variables de control del juego
	private String[] idJugadores;
	private ArrayList<Pair<String, Integer>> parejaNombreGanancia;
	private int jugadorEnTurno;
	private Baraja mazo;
	private ArrayList<ArrayList<Carta>> manosJugadores;
	private int[] apuestasJugadores;
	private ArrayList<Carta> manoJugador1;
	private ArrayList<Carta> manoJugador2;
	private ArrayList<Carta> manoJugador3;
	private ArrayList<Carta> manoDealer;
	private int[] valorManos;
	private int saberSiVolo,saberSiPlanto;
	private int numeroJugadoresReiniciando;
	private DatosBlackJack datosEnviar;
	private int[] tieneAs;

	public ServidorBJ() {
		// inicializar variables de control del juego
		inicializarVariablesControlRonda();
		// inicializar las variables de manejo de hilos
		inicializareVariablesManejoHilos();
		// crear el servidor
		try {
			mostrarMensaje("Iniciando el servidor...");
			server = new ServerSocket(PUERTO, LONGITUD_COLA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void inicializareVariablesManejoHilos() {
		// TODO Auto-generated method stub
		manejadorHilos = Executors.newFixedThreadPool(LONGITUD_COLA);
		bloqueoJuego = new ReentrantLock();
		esperarInicio = bloqueoJuego.newCondition();
		esperarTurno = bloqueoJuego.newCondition();
		finalizar = bloqueoJuego.newCondition();
		jugadores = new Jugador[LONGITUD_COLA];
	}

	private void inicializarVariablesControlRonda() {
		// TODO Auto-generated method stub
		// Variables de control del juego.
		tieneAs = new int[4];
		for(int i=0;i<tieneAs.length;i++) {
			tieneAs[i]=0;
		}
		idJugadores = new String[3];
		valorManos = new int[4];
		
		mazo = new Baraja();
		Carta carta;
		ganador = new ArrayList<String>();
		apuestasJugadores = new int[3];
		manoJugador1 = new ArrayList<Carta>();
		manoJugador2 = new ArrayList<Carta>();
		manoJugador3 = new ArrayList<Carta>();
		manoDealer = new ArrayList<Carta>();
		parejaNombreGanancia = new ArrayList<Pair<String,Integer>>(); 
		
		// gestiona las tres manos en un solo objeto para facilitar el manejo del hilo
		manosJugadores = new ArrayList<ArrayList<Carta>>(4);
		manosJugadores.add(manoJugador1);
		manosJugadores.add(manoJugador2);
		manosJugadores.add(manoJugador3);
		manosJugadores.add(manoDealer);
		// reparto inicial jugadores 1 y 2
		for (int i = 1; i <= 2; i++) {
			carta = mazo.getCarta();
			manoJugador1.add(carta);
			calcularValorMano(carta, 0);
			carta = mazo.getCarta();
			manoJugador2.add(carta);
			calcularValorMano(carta, 1);
			carta = mazo.getCarta();
			manoJugador3.add(carta);
			calcularValorMano(carta, 2);
		}
		// Carta inicial Dealer
		carta = mazo.getCarta();
		manoDealer.add(carta);
		calcularValorMano(carta, 3);

		
	}
	//Calcula el valor total de la mano de un jugador
	private void calcularValorMano(Carta carta, int i) {
		System.out.println("El �ndice "+i+" tiene As?: "+tieneAs[i]);
		// TODO Auto-generated method stub
		if (carta.getValor().equals("As")) {
			tieneAs[i]++;
			System.out.println("Cambio el As de "+i+" a "+tieneAs[i]);
			valorManos[i]+=11;
		} else {
			if (carta.getValor().equals("J") || carta.getValor().equals("Q") || carta.getValor().equals("K")) {
				valorManos[i] += 10;
			} else {
				valorManos[i] += Integer.parseInt(carta.getValor());
			}
			
		}
		int Ases = tieneAs[i];
		for(int j=0;j<Ases;j++) {
			if(valorManos[i]>21) {
				tieneAs[i]--;
				System.out.println("Cambio el As de "+i+" a "+tieneAs[i]);
				valorManos[i]-=10;
			}
		}
	}
	//Inicia el servidor
	public void iniciar() {
		// esperar a los clientes
		mostrarMensaje("Esperando a los jugadores...");

		for (int i = 0; i < LONGITUD_COLA; i++) {
			try {
				conexionJugador = server.accept();
				jugadores[i] = new Jugador(conexionJugador, i);
				manejadorHilos.execute(jugadores[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}
	//Inicia la ronda del juego
	private void iniciarRondaJuego() {

		this.mostrarMensaje("bloqueando al servidor para despertar al jugador 1");
		bloqueoJuego.lock();

		// despertar al jugador 1 porque es su turno
		try {
			this.mostrarMensaje("Despertando al jugador 1 para que inicie el juego");
			jugadores[0].setSuspendido(false);
			jugadores[1].setSuspendido(false);
			esperarInicio.signalAll();
		} catch (Exception e) {

		} finally {
			this.mostrarMensaje("Desbloqueando al servidor luego de despertar al jugador 1 para que inicie el juego");
			bloqueoJuego.unlock();
		}
	}

	//Analiza el mensaje recibido por un jugador
	private void analizarMensaje(String entrada, int indexJugador) {
		System.out.println("Analizando el mensaje del jugador con index "+indexJugador+" que dice "+entrada);
		// TODO Auto-generated method stub
		// garantizar que solo se analice la petici�n del jugador en turno.
		while (indexJugador != jugadorEnTurno && !entrada.equals("reiniciar") && !entrada.equals("abandonar")) {
			bloqueoJuego.lock();
			try {
				esperarTurno.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bloqueoJuego.unlock();
		}

		// valida turnos para jugador 0 o 1

		if (entrada.equals("pedir")) {
			// dar carta
			mostrarMensaje("Se envi� carta al jugador " + idJugadores[indexJugador]);
			Carta carta = mazo.getCarta();
			// adicionar la carta a la mano del jugador en turno
			manosJugadores.get(indexJugador).add(carta);
			calcularValorMano(carta, indexJugador);

			datosEnviar = new DatosBlackJack();
			datosEnviar.setIdJugadores(idJugadores);
			datosEnviar.setValorManos(valorManos);
			datosEnviar.setCarta(carta);
			// datosEnviar.ponerImagen(carta);
			datosEnviar.setJugador(idJugadores[indexJugador]);
			// determinar qu� sucede con la carta dada en la mano del jugador y
			// mandar mensaje a todos los jugadores
			if (valorManos[indexJugador] > 21) {
				// jugador Vol�
				datosEnviar
						.setMensaje(idJugadores[indexJugador] + " tienes " + valorManos[indexJugador] + " volaste :(");
				datosEnviar.setJugadorEstado("vol�");
				for (int i = 0; i < jugadores.length; i++) {
					if (indexJugador == i) {
						datosEnviar.setMensaje(
								idJugadores[indexJugador] + " tienes " + valorManos[indexJugador] + " volaste");
						jugadores[i].enviarMensajeCliente(datosEnviar);
					} else {
						datosEnviar.setMensaje(
								idJugadores[indexJugador] + " ahora tiene " + valorManos[indexJugador] + " volo");
						jugadores[i].enviarMensajeCliente(datosEnviar);
					}
				}
				// notificar a todos que jugador sigue
				if (jugadorEnTurno == 0) {

					datosEnviar = new DatosBlackJack();
					datosEnviar.setIdJugadores(idJugadores);
					datosEnviar.setValorManos(valorManos);
					datosEnviar.setJugador(idJugadores[1]);
					datosEnviar.setJugadorEstado("iniciar");

					datosEnviar.setMensaje("Juega " + idJugadores[1] + " y tiene: " + valorManos[1]);
					jugadores[0].enviarMensajeCliente(datosEnviar);
					jugadores[2].enviarMensajeCliente(datosEnviar);
					datosEnviar.setMensaje(idJugadores[1] + " te toca jugar y tienes " + valorManos[1]);
					jugadores[1].enviarMensajeCliente(datosEnviar);
					// levantar al jugador en espera de turno

					bloqueoJuego.lock();
					try {
						// esperarInicio.await();
						jugadores[0].setSuspendido(true);
						esperarTurno.signalAll();
						jugadorEnTurno++;
					} finally {
						bloqueoJuego.unlock();
					}
				} else if (jugadorEnTurno == 1) {
					datosEnviar = new DatosBlackJack();
					datosEnviar.setIdJugadores(idJugadores);
					datosEnviar.setValorManos(valorManos);
					datosEnviar.setJugador(idJugadores[2]);
					datosEnviar.setJugadorEstado("iniciar");
					datosEnviar.setMensaje("Juega " + idJugadores[2] + " y tiene: " + valorManos[2]);
					jugadores[0].enviarMensajeCliente(datosEnviar);
					jugadores[1].enviarMensajeCliente(datosEnviar);
					datosEnviar.setMensaje(idJugadores[2] + " te toca jugar y tienes " + valorManos[2]);
					jugadores[2].enviarMensajeCliente(datosEnviar);

					bloqueoJuego.lock();
					try {
						// esperarInicio.await();
						jugadores[1].setSuspendido(true);
						esperarTurno.signalAll();
						jugadorEnTurno++;
					} finally {
						bloqueoJuego.unlock();
					}
				} else {// era el jugador 3 entonces se debe iniciar el dealer
						// notificar a todos que le toca jugar al dealer
					datosEnviar = new DatosBlackJack();
					datosEnviar.setIdJugadores(idJugadores);
					datosEnviar.setValorManos(valorManos);
					datosEnviar.setJugador("dealer");
					datosEnviar.setJugadorEstado("iniciar");
					datosEnviar.setMensaje("Dealer se repartir� carta");

					jugadores[0].enviarMensajeCliente(datosEnviar);
					jugadores[1].enviarMensajeCliente(datosEnviar);
					jugadores[2].enviarMensajeCliente(datosEnviar);
					iniciarDealer();
				}

			} else {// jugador no se pasa de 21 puede seguir jugando
				datosEnviar.setCarta(carta);
				datosEnviar.setJugador(idJugadores[indexJugador]);
				datosEnviar.setMensaje(idJugadores[indexJugador] + " ahora tienes " + valorManos[indexJugador]);
				datosEnviar.setJugadorEstado("sigue");

				for (int i = 0; i < jugadores.length; i++) {
					if (indexJugador == i) {
						datosEnviar.setMensaje(idJugadores[indexJugador] + " ahora tienes " + valorManos[indexJugador]);
						jugadores[i].enviarMensajeCliente(datosEnviar);
					} else {
						datosEnviar.setMensaje(idJugadores[indexJugador] + " ahora tiene " + valorManos[indexJugador]);
						jugadores[i].enviarMensajeCliente(datosEnviar);
					}
				}
			}
			// Plantar
		} else if (entrada.equals("plantar")) {
			// jugador en turno plant�
			datosEnviar = new DatosBlackJack();
			datosEnviar.setIdJugadores(idJugadores);
			datosEnviar.setValorManos(valorManos);
			datosEnviar.setJugador(idJugadores[indexJugador]);
			datosEnviar.setMensaje(idJugadores[indexJugador] + " se plant�");
			datosEnviar.setJugadorEstado("plant�");

			jugadores[0].enviarMensajeCliente(datosEnviar);
			jugadores[1].enviarMensajeCliente(datosEnviar);
			jugadores[2].enviarMensajeCliente(datosEnviar);

			// notificar a todos el jugador que sigue en turno
			if (jugadorEnTurno == 0) {

				datosEnviar = new DatosBlackJack();
				datosEnviar.setIdJugadores(idJugadores);
				datosEnviar.setValorManos(valorManos);
				datosEnviar.setJugador(idJugadores[1]);
				datosEnviar.setJugadorEstado("iniciar");
				datosEnviar.setMensaje(idJugadores[1] + " te toca jugar y tienes " + valorManos[1]);
				jugadores[1].enviarMensajeCliente(datosEnviar);
				datosEnviar.setMensaje("Juega " + idJugadores[1] + " y tiene: " + valorManos[1]);
				jugadores[0].enviarMensajeCliente(datosEnviar);
				jugadores[2].enviarMensajeCliente(datosEnviar);
				// levantar al jugador en espera de turno

				bloqueoJuego.lock();
				try {
					// esperarInicio.await();
					jugadores[indexJugador].setSuspendido(true);
					esperarTurno.signalAll();
					jugadorEnTurno++;
				} finally {
					bloqueoJuego.unlock();
				}
			} else if (jugadorEnTurno == 1) {
				datosEnviar = new DatosBlackJack();
				datosEnviar.setIdJugadores(idJugadores);
				datosEnviar.setValorManos(valorManos);
				datosEnviar.setJugador(idJugadores[2]);
				datosEnviar.setJugadorEstado("iniciar");
				datosEnviar.setMensaje(idJugadores[2] + " te toca jugar y tienes " + valorManos[2]);
				jugadores[2].enviarMensajeCliente(datosEnviar);
				datosEnviar.setMensaje("Juega " + idJugadores[2] + " y tiene: " + valorManos[2]);
				jugadores[0].enviarMensajeCliente(datosEnviar);
				jugadores[1].enviarMensajeCliente(datosEnviar);

				// levantar al jugador en espera de turno

				bloqueoJuego.lock();
				try {
					// esperarInicio.await();
					jugadores[indexJugador].setSuspendido(true);
					esperarTurno.signalAll();
					jugadorEnTurno++;
				} finally {
					bloqueoJuego.unlock();
				}

			} else {
				// notificar a todos que le toca jugar al dealer
				datosEnviar = new DatosBlackJack();
				datosEnviar.setIdJugadores(idJugadores);
				datosEnviar.setValorManos(valorManos);
				datosEnviar.setJugador("dealer");
				datosEnviar.setJugadorEstado("iniciar");
				datosEnviar.setMensaje("Dealer se repartir� carta");

				jugadores[0].enviarMensajeCliente(datosEnviar);
				jugadores[1].enviarMensajeCliente(datosEnviar);
				jugadores[2].enviarMensajeCliente(datosEnviar);
				iniciarDealer();
			}
			// APUESTA
		}
		else if(entrada.equals("abandonar")) {
			datosEnviar.setJugador(idJugadores[indexJugador]);
			terminarJuego();
		}
		else if(entrada.equals("salgo")) {
			
		}
		else if(entrada.equals("reiniciar")) {
			numeroJugadoresReiniciando++;
			if(numeroJugadoresReiniciando==3) {
				reiniciar();
			}
			else {
				datosEnviar.setJugadorEstado("reiniciando");
				jugadores[indexJugador].enviarMensajeCliente(datosEnviar);
				System.out.println("Enviando reiniciar al jugador con index "+indexJugador);
			}
		}
		else {
			datosEnviar = new DatosBlackJack();
			apuestasJugadores[indexJugador] = Integer.parseInt(entrada);

			datosEnviar.setIdJugadores(idJugadores);
			datosEnviar.setValorManos(valorManos);
			datosEnviar.setJugador(idJugadores[indexJugador]);
			datosEnviar.setMensaje(idJugadores[indexJugador] + " aposto " + entrada);
			System.out.println(indexJugador+" aposto "+entrada);
			datosEnviar.setEstadoJuego(true);
			datosEnviar.setJugadorEstado("apuesta");
			datosEnviar.setApuestas(apuestasJugadores);
			System.out.println("Apuestas a enviar:");
			for(int i=0; i<apuestasJugadores.length;i++) {
				System.out.println("["+i+","+apuestasJugadores[i]+"]");
			}

			jugadores[0].enviarMensajeCliente(datosEnviar);
			jugadores[1].enviarMensajeCliente(datosEnviar);
			jugadores[2].enviarMensajeCliente(datosEnviar);

			datosEnviar.setJugadorEstado("iniciar");
			datosEnviar.setMensaje("");
			datosEnviar.setEstadoJuego(false);// Ronda de pedir cartas.
			datosEnviar.setApuestas(apuestasJugadores);
			jugadores[0].enviarMensajeCliente(datosEnviar);
			jugadores[1].enviarMensajeCliente(datosEnviar);
			jugadores[2].enviarMensajeCliente(datosEnviar);

		}
	}
	//Reinicia la partida
	private void reiniciar() {
		// TODO Auto-generated method stub
		int[] apuestas = {0,0,0};
		String[] idJugadores = this.idJugadores;
		inicializarVariablesControlRonda();
		this.idJugadores=idJugadores;
		datosEnviar = new DatosBlackJack();
		datosEnviar.setManoDealer(manosJugadores.get(3));
		datosEnviar.setManoJugador1(manosJugadores.get(0));
		datosEnviar.setManoJugador2(manosJugadores.get(1));
		datosEnviar.setManoJugador3(manosJugadores.get(2));
		datosEnviar.setMazo(mazo);
		datosEnviar.setIdJugadores(idJugadores);
		datosEnviar.setValorManos(valorManos);
		datosEnviar.setEstadoJuego(true); // Ronda de apuestas activado.
		datosEnviar.setMensaje("Inicias " + idJugadores[0] + " tienes " + valorManos[0]);
		datosEnviar.setJugadorEstado("reiniciar");
		datosEnviar.setJugador(idJugadores[0]);
		datosEnviar.setApuestas(apuestas);
		jugadores[0].enviarMensajeCliente(datosEnviar);
		jugadores[1].enviarMensajeCliente(datosEnviar);
		jugadores[2].enviarMensajeCliente(datosEnviar);
		jugadorEnTurno = 0;
		numeroJugadoresReiniciando=0;
	}

	private void terminarJuego() {
		// TODO Auto-generated method stub
		jugadores[0].cerrar();
		jugadores[1].cerrar();
		jugadores[2].cerrar();
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void iniciarDealer() {
		// le toca turno al dealer.
		Thread dealer = new Thread(this);
		dealer.start();
	}

	/*
	 * The Class Jugador. Clase interna que maneja el servidor para gestionar la
	 * comunicaci�n con cada cliente Jugador que se conecte
	 */
	private class Jugador implements Runnable {

		// varibles para gestionar la comunicaci�n con el cliente (Jugador) conectado
		private Socket conexionCliente;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private String entrada;

		// variables de control
		private int indexJugador;
		private boolean suspendido,jugar=true;

		public Jugador(Socket conexionCliente, int indexJugador) {
			this.conexionCliente = conexionCliente;
			this.indexJugador = indexJugador;
			suspendido = true;
			// crear los flujos de E/S
			try {
				out = new ObjectOutputStream(conexionCliente.getOutputStream());
				out.flush();
				in = new ObjectInputStream(conexionCliente.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Suspende al jugador
		private void setSuspendido(boolean suspendido) {
			this.suspendido = suspendido;
			System.out.println("Jugador " + (indexJugador + 1) + " cambia suspendido a " + suspendido);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// procesar los mensajes eviados por el cliente

			// ver cual jugador es
			if (indexJugador == 0) {
				// es jugador 1, debe ponerse en espera a la llegada del otro jugador

				try {
					// guarda el nombre del primer jugador
					idJugadores[0] = (String) in.readObject();

					mostrarMensaje("Hilo establecido con jugador (1) " + idJugadores[0]);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mostrarMensaje("bloquea servidor para poner en espera de inicio al jugador 1");
				bloqueoJuego.lock(); // bloquea el servidor

				while (suspendido) {
					mostrarMensaje("Parando al Jugador 1 en espera del otro jugador...");
					try {
						esperarInicio.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						mostrarMensaje("Desbloquea Servidor luego de bloquear al jugador 1");
						bloqueoJuego.unlock();
					}
				}

				// ya se conect� el otro jugador,
				// le manda al jugador 1 todos los datos para montar la sala de Juego
				// le toca el turno a jugador 1
				
				
				mostrarMensaje("jugador 1 manda al jugador 1 todos los datos para montar SalaJuego");
				//Le env�a al jugador los datos que debe mostrar su ventana
				datosEnviar = new DatosBlackJack();
				datosEnviar.setManoDealer(manosJugadores.get(3));
				datosEnviar.setManoJugador1(manosJugadores.get(0));
				datosEnviar.setManoJugador2(manosJugadores.get(1));
				datosEnviar.setManoJugador3(manosJugadores.get(2));
				datosEnviar.setMazo(mazo);
				datosEnviar.setIdJugadores(idJugadores);
				datosEnviar.setValorManos(valorManos);
				datosEnviar.setEstadoJuego(true); // Ronda de apuestas activado.
				datosEnviar.setMensaje("Inicias " + idJugadores[0] + " tienes " + valorManos[0]);
				enviarMensajeCliente(datosEnviar);
				jugadorEnTurno = 0;
			} else if (indexJugador == 1) {
				//Es jugador 2
				try {
					// guarda el nombre del primer jugador
					idJugadores[1] = (String) in.readObject();
				
					mostrarMensaje("Hilo establecido con jugador (2) " + idJugadores[1]);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mostrarMensaje("bloquea servidor para poner en espera de inicio al jugador 2");
				bloqueoJuego.lock(); // bloquea el servidor

				while (suspendido) {
					mostrarMensaje("Parando al Jugador 2 en espera del otro jugador...");
					try {
						esperarInicio.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						mostrarMensaje("Desbloquea Servidor luego de bloquear al jugador 2");
						bloqueoJuego.unlock();
					}
				}

				mostrarMensaje("jugador 2 manda al jugador 1 todos los datos para montar SalaJuego");
				datosEnviar = new DatosBlackJack();
				datosEnviar.setManoDealer(manosJugadores.get(3));
				datosEnviar.setManoJugador1(manosJugadores.get(0));
				datosEnviar.setManoJugador2(manosJugadores.get(1));
				datosEnviar.setManoJugador3(manosJugadores.get(2));
				datosEnviar.setMazo(mazo);
				datosEnviar.setIdJugadores(idJugadores);
				datosEnviar.setValorManos(valorManos);
				datosEnviar.setEstadoJuego(true); // Ronda de apuestas activado.
				datosEnviar.setMensaje("Inicia " + idJugadores[0] + " tiene " + valorManos[0]);
				enviarMensajeCliente(datosEnviar);
				jugadorEnTurno = 0;
			} else {
				// Es jugador 3
				try {
					idJugadores[2] = (String) in.readObject();
					
					mostrarMensaje("Hilo jugador (3)" + idJugadores[2]);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mostrarMensaje("manda al jugador 2 el nombre del jugador 1");

				datosEnviar = new DatosBlackJack();
				datosEnviar.setManoDealer(manosJugadores.get(3));
				datosEnviar.setManoJugador1(manosJugadores.get(0));
				datosEnviar.setManoJugador2(manosJugadores.get(1));
				datosEnviar.setManoJugador3(manosJugadores.get(2));
				datosEnviar.setIdJugadores(idJugadores);
				datosEnviar.setValorManos(valorManos);
				datosEnviar.setEstadoJuego(true); // Ronda de apuestas activado.
				datosEnviar.setMensaje("Inicia " + idJugadores[0] + " tiene " + valorManos[0]);
				enviarMensajeCliente(datosEnviar);

				iniciarRondaJuego(); // despertar al jugador 1 para iniciar el juego
				mostrarMensaje("Bloquea al servidor para poner en espera de turno al jugador 2");
				bloqueoJuego.lock();
				try {
					mostrarMensaje("Pone en espera de turno al jugador 2");
					esperarTurno.await();
					mostrarMensaje("Despierta de la espera de inicio del juego al jugador 1");
					//
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					bloqueoJuego.unlock();
				}
			}
			//Mientras ning�n jugador decida retirarse del juego al final de la partida
			while (jugar) {
				try {
					entrada = (String) in.readObject();
					analizarMensaje(entrada, indexJugador);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// controlar cuando se cierra un cliente
				}
			}
			try {
				in.close();
				out.close();
				conexionCliente.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Un hilo muere");
			// cerrar conexi�n

		}
		
		//Env�a un mensaje al jugador
		public void enviarMensajeCliente(Object mensaje) {
			try {
				DatosBlackJack prueba = (DatosBlackJack)mensaje;
				if(prueba.getApuestas()!=null) {
					System.out.println("Apuestas a enviar cuando envio mensaje al cliente "+indexJugador+":");
					for(int i=0; i<prueba.getApuestas().length;i++) {
						System.out.println("["+i+","+prueba.getApuestas()[i]+"]");
					}
				}
				out.reset();
				out.writeObject(mensaje);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Termina al jugador y su conexi�n con �l
		public void cerrar() {
			jugar=false;
			datosEnviar.setJugadorEstado("saliendo");
			enviarMensajeCliente(datosEnviar);
		}
	}// fin inner class Jugador
	//Determina el o los ganadores del juego
	 public void determinarGanador() {
		
		 if(valorManos[3]>21) {

			 for(int i=0;i<3;i++) {
				 if(valorManos[i]<=21) {
					 ganador.add(idJugadores[i]);
				 }
			 }
		 }else if(((valorManos[0] <= valorManos[3]) && (valorManos[1] <= valorManos[3]) &&( valorManos[2] <= valorManos[3]))
				 && valorManos[3] <= 21){

			 ganador.add("dealer");

			// numGanadores ++;
		 }else if(valorManos[0]>21 && valorManos[1] >21  && valorManos[2]>21 && valorManos[3] <= 21) {

			 ganador.add("dealer");

		 }
		 else { 
			 for(int i=0;i<3;i++) {
				 if(valorManos[i]>valorManos[3] && valorManos[i]<=21) {
					 ganador.add(idJugadores[i]);
				 }
			 }
		 }
		 
		 if(valorManos[0] > 21 && valorManos[1] > 21 && valorManos[2] > 21 && 
				 valorManos[3] > 21){
			
		 }else if(valorManos[3]<21 && ganador.size()==0){
			 ganador.add("dealer");
		 }
	 }
	 //Determina si la jugada del jugador fue o no un BlackJack
	 public boolean verificarJugadaBJ(ArrayList<Carta> manoJugador) {
		 if(manoJugador.size() == 2) {
			 for(int i = 0; i < manoJugador.size(); i++) {
				 if(manoJugador.get(i).getValor().equals("As")) {
					 for(int j = 0; j < 2; j++) {
						 if(manoJugador.get(j).getValor().equals("K") || manoJugador.get(j).getValor().equals("J") ||
							manoJugador.get(j).getValor().equals("Q")) {
							return true; 
						 }
					 }
				 }
			 }
			 return false;
		 }else {
			 return false;
		 }
		 
	 }
	 //Da las apuestas de los jugadores al dealer
	 public int verificarCantidadGanadores() {
		 if(ganador.size() == 1 && !ganador.contains("dealer")) {
			 return 20;
		 }else if(ganador.size() == 2 && !ganador.contains("dealer")) {
			 return 10;
		 }else if(ganador.size() == 3 && !ganador.contains("dealer")){
			 return 0;
		 }else if(ganador.size() == 1 && ganador.contains("dealer")) {
			 return 30;
		 }
		 return 0;
	 }
	 //Determina si el jugador en cuestion esta entre los ganadores
	 public boolean contieneJugador(ArrayList<String> ganador, String jugadorBuscado) {
		 for(int i = 0; i < ganador.size(); i++) {
			if(ganador.get(i).equals(jugadorBuscado)) {
				return true;
			}
		 }
		 return false;
	 }
	 //Reparte las ganancias al dealer
	 public void repartirGananciasDealer() {
		 if(contieneJugador(ganador,"dealer")) {
			 if(verificarJugadaBJ(manosJugadores.get(3))) {
				 System.out.println("Pareja nombre agregado dealer");
				 int cantidadGanancia = verificarCantidadGanadores() + 15;
				 parejaNombreGanancia.add(new Pair<String, Integer>("dealer", cantidadGanancia));
			 }else {
				 System.out.println("Pareja nombre agregado --> dealer");
				 int cantidadGanancia = verificarCantidadGanadores()+10;
				 parejaNombreGanancia.add(new Pair<String, Integer>("dealer",cantidadGanancia));
			 }
		 }else {
			 int cantidadGanancia = verificarCantidadGanadores();
			 parejaNombreGanancia.add(new Pair<String, Integer>("dealer",cantidadGanancia));
		 }
		 
	 }
	 //Reparte las ganancias a los jugadores
	 public void repartirGanancias() {
		 
		 System.out.println("JUGADORES EN GANANCIAS:");
		 for(int i = 0; i < idJugadores.length;i++) {
			 System.out.println("idJugador ["+i+"] : "+idJugadores[i]);
		 }
		 System.out.println("GANADORES EN GANANCIA");
		 for(int i = 0; i < ganador.size();i++) {
			 System.out.println("Ganador ["+i+"] : "+ganador.get(i));
		 }
		 if(ganador.size() >= 1) {
			 for(int i = 0; i < idJugadores.length; i++) {

				 if(contieneJugador(ganador, idJugadores[i])) {
					 System.out.println("Entra ganador "+idJugadores[i]);
					 if(verificarJugadaBJ(manosJugadores.get(i))) {
						 System.out.println("Pareja nombre agregado: "+idJugadores[i]);
						 parejaNombreGanancia.add(new Pair<String, Integer>(idJugadores[i],25));
					 }else {
						 System.out.println("Pareja nombre agregado --> "+idJugadores[i]);
						 parejaNombreGanancia.add(new Pair<String, Integer>(idJugadores[i],20));
					 }
				 }
			 } 
		 }else {
			 System.out.println("no gan� nadie");
			 parejaNombreGanancia.add(new Pair<String, Integer>("null",0));
		 }
	 }
		 
	
	// Jugador dealer emulado por el servidor
	
	
	public void run() {
		// TODO Auto-generated method stub
		mostrarMensaje("Incia el dealer ...");

		datosEnviar = new DatosBlackJack();
		datosEnviar.setJugador("dealer");
		datosEnviar.setJugadorEstado("apuesta");
		datosEnviar.setMensaje("El dealer aposto 10");
		datosEnviar.setEstadoJuego(true);
		datosEnviar.setApuestas(apuestasJugadores);
		jugadores[0].enviarMensajeCliente(datosEnviar);
		jugadores[1].enviarMensajeCliente(datosEnviar);
		jugadores[2].enviarMensajeCliente(datosEnviar);
		datosEnviar.setEstadoJuego(false);
		datosEnviar.setMensaje("");
		datosEnviar.setApuestas(apuestasJugadores);
		jugadores[0].enviarMensajeCliente(datosEnviar);
		jugadores[1].enviarMensajeCliente(datosEnviar);
		jugadores[2].enviarMensajeCliente(datosEnviar);

		boolean pedir = true;

		while (pedir) {
			Carta carta = mazo.getCarta();
			// adicionar la carta a la mano del dealer
			manosJugadores.get(3).add(carta);
			calcularValorMano(carta, 3);

			mostrarMensaje("El dealer recibe " + carta.toString() + " suma " + valorManos[3]);

			datosEnviar = new DatosBlackJack();
			datosEnviar.setCarta(carta);
			datosEnviar.setJugador("dealer");

			if (valorManos[3] <= 16) {
				datosEnviar.setJugadorEstado("sigue");
				datosEnviar.setMensaje("Dealer ahora tiene " + valorManos[3]);
				mostrarMensaje("El dealer sigue jugando");
			} else {
				if (valorManos[3] > 21) {
					saberSiVolo=1;
					datosEnviar.setJugadorEstado("vol�");
					datosEnviar.setMensaje("Dealer ahora tiene " + valorManos[3] + " vol� :(");
					pedir = false;
					mostrarMensaje("El dealer vol�"); 
					
				} else {
					saberSiPlanto=1;
					datosEnviar.setJugadorEstado("plant�");
					datosEnviar.setMensaje("Dealer ahora tiene " + valorManos[3] + " plant�");
					pedir = false;
					mostrarMensaje("El dealer plant�");

				}
				
			}
			// envia la jugada a los otros jugadores
			datosEnviar.setCarta(carta);
			jugadores[0].enviarMensajeCliente(datosEnviar);
			jugadores[1].enviarMensajeCliente(datosEnviar);
			jugadores[2].enviarMensajeCliente(datosEnviar);
		} // fin while
		if(saberSiPlanto==1 || saberSiVolo==1) {
			System.out.println("Entro al if despues del while");
			determinarGanador();
			repartirGanancias();
			repartirGananciasDealer();
			datosEnviar.setGanadores(ganador);
			datosEnviar.setParejas(parejaNombreGanancia);
			datosEnviar.setMensaje("El ganador es "+ganador);
			if(parejaNombreGanancia.size() == 1 && parejaNombreGanancia.get(0).getKey().equals("null")) {
				datosEnviar.setMensajeGanancias("Nadie ha ganado, las apuestas han sido devueltas!");
			}else {
				datosEnviar.setMensajeGanancias("Se han dado las ganancias, revisa las tuyas!! "+parejaNombreGanancia.get(0).getValue());
			}
			System.out.println("DESPU�S DEL IF");
			jugadores[0].enviarMensajeCliente(datosEnviar);
			jugadores[1].enviarMensajeCliente(datosEnviar);
			jugadores[2].enviarMensajeCliente(datosEnviar);
		
		}
		
			
	}

}// Fin class ServidorBJ
