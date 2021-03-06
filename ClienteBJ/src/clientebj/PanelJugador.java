/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package clientebj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import comunes.Carta;

public class PanelJugador extends JPanel {
	//constantes de clase
	private static final int ANCHO = 250;
	private static final int ALTO = 95;
	
	//variables para control del graficado
	private ArrayList<Recuerdo> cartasRecuerdo;
	private int x;
	    
	public PanelJugador(String nombreJugador) {
		cartasRecuerdo = new ArrayList<Recuerdo>();
		
		//Configuracion de la ventana.
		this.setPreferredSize(new Dimension(ANCHO,ALTO));
		this.setMinimumSize(this.getPreferredSize());
		TitledBorder bordes;
		bordes = BorderFactory.createTitledBorder(nombreJugador);
		this.setBorder(bordes);
	}
	
	public void pintarCartasInicio(ArrayList<Carta> manoJugador) {
		x=5;
	    for(int i=0;i<manoJugador.size();i++) {
	    	cartasRecuerdo.add(new Recuerdo(manoJugador.get(i).getImagen(),x));
	    	x+=27;
	    }			
	    repaint();
	}
	
	
	public void dibujarCarta(Carta carta) {
		cartasRecuerdo.add(new Recuerdo(carta.getImagen(),x));
		x+=17; //Espacio entre las cartas
		repaint();
	}
	
	public void paintComponent(Graphics g)  {
		super.paintComponent(g); 
		//Pinta con memoria
			for(Recuerdo carta : cartasRecuerdo) {
				g.drawImage(carta.getImagenRecordar(), carta.getxRecordar(),20, this);
			}		
		}

	//Clase que recuerda las cartas anteriores.
	private class Recuerdo{
		private Image imagenRecordar;
		private int xRecordar;

		public Recuerdo(Image imagenRecordar, int xRecordar) {
			this.imagenRecordar = imagenRecordar;
			this.xRecordar = xRecordar;
		}

		public Image getImagenRecordar() {
			return imagenRecordar;
		}

		public int getxRecordar() {
			return xRecordar;
		}
	}


}
