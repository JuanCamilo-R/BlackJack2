/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class DatosBlackJack.
 */
public class DatosBlackJack implements Serializable{
	
	/** The id jugadores. */
	private String[] idJugadores;
	
	/** The mano jugador 3. */
	private ArrayList<Carta> manoJugador1, manoJugador2, manoDealer, manoJugador3;
	
	/** The apuestas jugadores. */
	private int[] apuestasJugadores;
	
	/** The valor manos. */
	private int[] valorManos;
	
	/** The carta. */
	private Carta carta;
	
	/** The mensaje. */
	private String mensaje;
	
	/** The mensaje ganancia. */
	private String mensajeGanancia;
	
	/** The jugador estado. */
	private String jugador,jugadorEstado;
	
	/** The pareja nombre ganancia. */
	private ArrayList<Pair<String, Integer>> parejaNombreGanancia;
	
	/** The baraja. */
	private Baraja baraja;
	
	/** The estado juego. */
	private boolean estadoJuego;
	
	/** The ganadorr. */
	private String ganadorr;
	
	/** The ganadores. */
	private ArrayList<String> ganadores;
	
	/**
	 * Gets the jugador.
	 *
	 * @return the jugador
	 */
	public String getJugador() {
		return jugador;
	}
	
	/**
	 * Sets the jugador.
	 *
	 * @param jugador the new jugador
	 */
	public void setJugador(String jugador) {
		this.jugador = jugador;
	}
	
	/**
	 * Sets the ganadores.
	 *
	 * @param ganadores the new ganadores
	 */
	public void setGanadores(ArrayList<String> ganadores) {
		this.ganadores = ganadores;
	}
	
	/**
	 * Gets the ganadores.
	 *
	 * @return the ganadores
	 */
	public ArrayList<String> getGanadores(){
		return ganadores;
	}
	
	/**
	 * Sets the ganador.
	 *
	 * @param ganador the new ganador
	 */
	public void setGanador(String ganador) {
		this.ganadorr = ganador;
	}
	
	/**
	 * Gets the ganador.
	 *
	 * @return the ganador
	 */
	public String getGanador() {
		return ganadorr;
	}
	
	/**
	 * Gets the jugador estado.
	 *
	 * @return the jugador estado
	 */
	public String getJugadorEstado() {
		return jugadorEstado;
	}
	
	/**
	 * Sets the jugador estado.
	 *
	 * @param jugadorEstado the new jugador estado
	 */
	public void setJugadorEstado(String jugadorEstado) {
		this.jugadorEstado = jugadorEstado;
	}
		
	/**
	 * Gets the id jugadores.
	 *
	 * @return the id jugadores
	 */
	public String[] getIdJugadores() {
		return idJugadores;
	}
	
	/**
	 * Sets the id jugadores.
	 *
	 * @param idJugadores the new id jugadores
	 */
	public void setIdJugadores(String[] idJugadores) {
		this.idJugadores = idJugadores;
	}
	
	/**
	 * Gets the mano jugador 1.
	 *
	 * @return the mano jugador 1
	 */
	public ArrayList<Carta> getManoJugador1() {
		return manoJugador1;
	}
	
	/**
	 * Sets the mano jugador 1.
	 *
	 * @param manoJugador1 the new mano jugador 1
	 */
	public void setManoJugador1(ArrayList<Carta> manoJugador1) {
		this.manoJugador1 = manoJugador1;
	}
	
	
	
	/**
	 * Gets the mano jugador 2.
	 *
	 * @return the mano jugador 2
	 */
	public ArrayList<Carta> getManoJugador2() {
		return manoJugador2;
	}
	
	/**
	 * Sets the parejas.
	 *
	 * @param parejaLlegada the pareja llegada
	 */
	public void setParejas(ArrayList<Pair<String, Integer>> parejaLlegada) {
		this.parejaNombreGanancia = parejaLlegada;
	}
	
	/**
	 * Gets the parejas.
	 *
	 * @return the parejas
	 */
	public ArrayList<Pair<String, Integer>> getParejas(){
		return this.parejaNombreGanancia;
	}
	
	/**
	 * Sets the mensaje ganancias.
	 *
	 * @param mensaje the new mensaje ganancias
	 */
	public void setMensajeGanancias(String mensaje) {
		this.mensajeGanancia = mensaje;
	}
	
	/**
	 * Gets the mensaje ganancias.
	 *
	 * @return the mensaje ganancias
	 */
	public String getMensajeGanancias() {
		return this.mensajeGanancia;
	}
	
	/**
	 * Sets the mano jugador 3.
	 *
	 * @param manoJugador3 the new mano jugador 3
	 */
	public void setManoJugador3(ArrayList<Carta> manoJugador3) {
		this.manoJugador3 = manoJugador3;
	}
	
	/**
	 * Sets the mano jugador 2.
	 *
	 * @param manoJugador2 the new mano jugador 2
	 */
	public void setManoJugador2(ArrayList<Carta> manoJugador2) {
		this.manoJugador2 = manoJugador2;
	}
	
	/**
	 * Gets the mano dealer.
	 *
	 * @return the mano dealer
	 */
	public ArrayList<Carta> getManoDealer() {
		return manoDealer;
	}
	
	/**
	 * Sets the estado juego.
	 *
	 * @param estado the new estado juego
	 */
	public void setEstadoJuego(boolean estado) {
		//Verdadera si esta en ronda de apuestas
		//Falsa si no.
		estadoJuego = estado;
	}
	
	/**
	 * Gets the estado juego.
	 *
	 * @return the estado juego
	 */
	public boolean getEstadoJuego() {
		return estadoJuego;
	}
	
	/**
	 * Sets the apuestas jugadores.
	 *
	 * @param apuestas the new apuestas
	 */
	public void setApuestas(int[] apuestas) {
		apuestasJugadores = apuestas;
	}
	
	/**
	 * Gets the apuestas jugadores.
	 *
	 * @return the apuestas
	 */
	public int[] getApuestas(){
		return apuestasJugadores;
	}
	
	/**
	 * Mostrar ganancias.
	 */
	public void mostrarGanancias() {
		System.out.println("GANANCIAS DATOS RECIBIDOS: ");
		for(int i = 0; i < this.parejaNombreGanancia.size(); i++) {
			System.out.println(this.parejaNombreGanancia.get(i).getKey());
		}
	}
	
	/**
	 * Gets the mano jugador 3.
	 *
	 * @return the mano jugador 3
	 */
	public ArrayList<Carta> getManoJugador3(){
		return manoJugador3;
	}
	
	/**
	 * Sets the mano dealer.
	 *
	 * @param manoDealer the new mano dealer
	 */
	public void setManoDealer(ArrayList<Carta> manoDealer) {
		this.manoDealer = manoDealer;
	}
	
	/**
	 * Mensaje mal.
	 */
	public void mensajeMal() {
		System.out.println("NO ENTRE AL IF DE GANADORES Y GANANCIAS");
	}
	
	/**
	 * Gets the mensaje.
	 *
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	
	/**
	 * Sets the mensaje.
	 *
	 * @param mensaje the new mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	/**
	 * Sets the valor manos.
	 *
	 * @param valorManos the new valor manos
	 */
	public void setValorManos(int[] valorManos) {
		this.valorManos=valorManos;
	}
	
	/**
	 * Gets the valor manos.
	 *
	 * @return the valor manos
	 */
	public int[] getValorManos() {
		return valorManos;	
	}
	
	/**
	 * Sets the carta.
	 *
	 * @param carta the new carta
	 */
	public void setCarta(Carta carta) {
		this.carta=carta;
	}
	
	/**
	 * Gets the carta.
	 *
	 * @return the carta
	 */
	public Carta getCarta() {
		return carta;
	}
	
	/**
	 * Sets the mazo.
	 *
	 * @param mazo the new mazo
	 */
	public void setMazo(Baraja mazo) {
		baraja = mazo;
	}
	
}
