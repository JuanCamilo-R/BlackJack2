package comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.util.*;

public class DatosBlackJack implements Serializable{
	private String[] idJugadores;
	private ArrayList<Carta> manoJugador1, manoJugador2, manoDealer, manoJugador3;
	private int[] apuestasJugadores;
	private int[] valorManos;
	private Carta carta;
	private String mensaje;
	private String mensajeGanancia;
	private String jugador,jugadorEstado;
	private List<Pair<String, Integer>> parejaNombreGanancia;
	private Baraja baraja;
	private boolean estadoJuego;
	private String ganadorr;
	private ArrayList<String> ganadores;
	public String getJugador() {
		return jugador;
	}
	public void setJugador(String jugador) {
		this.jugador = jugador;
	}
	
	public void setGanadores(ArrayList<String> ganadores) {
		this.ganadores = ganadores;
	}
	
	public ArrayList<String> getGanadores(){
		return ganadores;
	}
	
	public void setGanador(String ganador) {
		this.ganadorr = ganador;
	}
	
	public String getGanador() {
		return ganadorr;
	}
	
	public String getJugadorEstado() {
		return jugadorEstado;
	}
	public void setJugadorEstado(String jugadorEstado) {
		this.jugadorEstado = jugadorEstado;
	}
		
	public String[] getIdJugadores() {
		return idJugadores;
	}
	public void setIdJugadores(String[] idJugadores) {
		this.idJugadores = idJugadores;
	}
	
	public ArrayList<Carta> getManoJugador1() {
		return manoJugador1;
	}
	public void setManoJugador1(ArrayList<Carta> manoJugador1) {
		this.manoJugador1 = manoJugador1;
	}
	
	
	
	public ArrayList<Carta> getManoJugador2() {
		return manoJugador2;
	}
	
	public void setParejas(List<Pair<String, Integer>> parejaLlegada) {
		this.parejaNombreGanancia = parejaLlegada;
	}
	
	public List<Pair<String, Integer>> getParejas(){
		return this.parejaNombreGanancia;
	}
	
	public void setMensajeGanancias(String mensaje) {
		this.mensajeGanancia += mensaje;
	}
	
	public String getMensajeGanancias() {
		return this.mensajeGanancia;
	}
	
	public void setManoJugador3(ArrayList<Carta> manoJugador3) {
		this.manoJugador3 = manoJugador3;
	}
	public void setManoJugador2(ArrayList<Carta> manoJugador2) {
		this.manoJugador2 = manoJugador2;
	}
	
	public ArrayList<Carta> getManoDealer() {
		return manoDealer;
	}
	
	public void setEstadoJuego(boolean estado) {
		//Verdadera si esta en ronda de apuestas
		//Falsa si no.
		estadoJuego = estado;
	}
	
	public boolean getEstadoJuego() {
		return estadoJuego;
	}
	public void setApuestas(int[] apuestas) {
		apuestasJugadores = apuestas;
	}
	
	public int[] getApuestas(){
		return apuestasJugadores;
	}
	
	public void mostrarGanancias() {
		System.out.println("GANANCIAS DATOS RECIBIDOS: ");
		for(int i = 0; i < this.parejaNombreGanancia.size(); i++) {
			System.out.println(this.parejaNombreGanancia.get(i).getKey());
		}
	}
	
	public ArrayList<Carta> getManoJugador3(){
		return manoJugador3;
	}
	public void setManoDealer(ArrayList<Carta> manoDealer) {
		this.manoDealer = manoDealer;
	}
	public void mensajeMal() {
		System.out.println("NO ENTRE AL IF DE GANADORES Y GANANCIAS");
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public void setValorManos(int[] valorManos) {
		this.valorManos=valorManos;
	}
	public int[] getValorManos() {
		return valorManos;	
	}
	public void setCarta(Carta carta) {
		this.carta=carta;
	}
	public Carta getCarta() {
		return carta;
	}
	
	public void setMazo(Baraja mazo) {
		baraja = mazo;
	}
	
}
