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
	private static final int ANCHO = 200;
	private static final int ALTO = 90;
	
	//variables para control del graficado
	private ArrayList<Recuerdo> cartasRecuerdo;
	private int x;
	    
	public PanelJugador(String nombreJugador) {
		//this.setBackground(Color.GREEN);
		cartasRecuerdo = new ArrayList<Recuerdo>();
		this.setPreferredSize(new Dimension(ANCHO,ALTO));
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
		x+=17;
		repaint();
	}
	/*
	public void pintarLaCarta (Carta carta) {
		dibujoRecordar.add(new Recuerdo(carta,x));
		x+=27;
		repaint();
	}
	*/
	public void paintComponent(Graphics g)  {
		super.paintComponent(g); 
		//Pinta con memoria
		
			for(Recuerdo carta : cartasRecuerdo) {
				g.drawImage(carta.getImagenRecordar(), carta.getxRecordar(),20, this);
			}
			//clase.seguir=true;		
		}
	/*
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font font =new Font(Font.DIALOG,Font.BOLD,12);
		g.setFont(font);
				
		//pinta la mano inicial
		for(int i=0;i<dibujoRecordar.size();i++) {
			g.drawString(dibujoRecordar.get(i).getCartaRecordar(), dibujoRecordar.get(i).getxRecordar(),35);
		}	
	}
	*/
	
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
	/*
	private class Recuerdo{
		private Carta cartaRecordar;
		private int xRecordar;

		public Recuerdo(Carta cartaRecordar, int xRecordar) {
			this.cartaRecordar = cartaRecordar;
			this.xRecordar = xRecordar;
		}

		public String getCartaRecordar() {
			return cartaRecordar.toString();
		}

		public int getxRecordar() {
			return xRecordar;
		}
	}
	*/

}
