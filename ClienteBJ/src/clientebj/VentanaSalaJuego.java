package clientebj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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

public class VentanaSalaJuego extends JInternalFrame {
	    
		private PanelJugador dealer, yo, jugador2, jugador3;
		private JTextArea areaMensajes;
		private JButton pedir, plantar, apostar,confirmarApuesta;
		private JLabel apuesta1, apuesta2, apuesta3, apuestaDealer, dinero1, dinero2, dinero3, dineroDealer,espacio;
		private JLabel palabraApuesta1, palabraApuesta2, palabraApuesta3, palabraApuestaDealer;
		private JLabel palabraDinero1, palabraDinero2, palabraDinero3, palabraDineroDealer;
		private JPanel panelYo, panelBotones, yoFull, panelDealer,panelJugador2, panelJugador3;
		private JPanel paneltextoDealer,paneltexto1,paneltexto2,paneltexto3;
		private Baraja barajaNueva; 
		private String yoId, jugador2Id, jugador3Id;
		private GridBagConstraints constraints;
		private JLabel icono;
		private int cantidadApuesta;
		private boolean aposto;
		private JInternalFrame yoClase;
		private ImageIcon imagen;
		//private DatosBlackJack datosRecibidos;
		private Escucha escucha;
		
		public VentanaSalaJuego(String yoId, String jugador2Id, String jugador3Id) {
			this.yoId = yoId;
			yoClase = this;
			this.jugador2Id = jugador2Id;
			this.jugador3Id = jugador3Id;
			//this.datosRecibidos=datosRecibidos;
			barajaNueva = new Baraja();	
			aposto = false;
			cantidadApuesta = 0;
			initGUI();
			
			//default window settings
			this.setTitle("Sala de juego BlackJack - Jugador: "+yoId);
			this.pack();
			this.setLocation((ClienteBlackJack.WIDTH-this.getWidth())/2, 
			         (ClienteBlackJack.HEIGHT-this.getHeight())/2);
			this.setResizable(false);
			this.show();
		}

		private void initGUI() {
			// TODO Auto-generated method stub
			//set up JFrame Container y Layout
	
			//Create Listeners objects
			
		
			escucha = new Escucha();
			//Create Control objects
		
	
			//Set up JComponents
			this.getContentPane().setLayout(new GridBagLayout());
			this.getContentPane().setBackground(Color.GREEN);
			
			constraints = new GridBagConstraints();
			
			palabraDineroDealer = new JLabel("Dinero: ");
			palabraApuestaDealer = new JLabel("      Apuesta dealer: ");
			dineroDealer = new JLabel("4000");
			apuestaDealer = new JLabel("0");
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
			
			palabraDinero2 = new JLabel("Dinero: ");
			palabraApuesta2 = new JLabel("      Apuesta: ");
			dinero2 = new JLabel("4000");
			apuesta2 = new JLabel("0");
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
			//panelJugador2.add(jugador2);
			add(panelJugador2,constraints);	
			
			
			
			palabraDinero3 = new JLabel("Dinero: ");
			palabraApuesta3 = new JLabel("      Apuesta: ");
			//palabraDinero3 .setBackground(Color.GREEN);
			dinero3 = new JLabel("4000");
			apuesta3 = new JLabel("0");
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
			
			areaMensajes = new JTextArea(8,18);
			areaMensajes.setBackground(Color.white);
			JScrollPane scroll = new JScrollPane(areaMensajes);	
			Border blackline;
			blackline = BorderFactory.createLineBorder(Color.black);
			TitledBorder bordes;
			bordes = BorderFactory.createTitledBorder(blackline, "Area de Mensajes");
	        bordes.setTitleJustification(TitledBorder.CENTER);
	       
			scroll.setBorder(bordes);
		//	areaMensajes.setOpaque(false);
			//areaMensajes.setBackground(new Color(0, 0, 0, 0));
			areaMensajes.setEditable(false);

			scroll.getViewport().setOpaque(false);
			scroll.setBackground(Color.white);
			scroll.setOpaque(false);
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			add(scroll,constraints);
			
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
			palabraDinero1 = new JLabel("Dinero: ");
			palabraApuesta1 = new JLabel("      Apuesta: ");
			dinero1 = new JLabel("4000");
			apuesta1 = new JLabel("0");
			espacio = new JLabel("           ");
			paneltexto1 = new JPanel();
			paneltexto1.setBackground(Color.GREEN);
			paneltexto1.setLayout(new GridBagLayout());
			
			yoFull = new JPanel();
			yoFull.setBackground(Color.GREEN);
			yoFull.setLayout(new GridBagLayout());
			//yoFull.setPreferredSize(new Dimension(400,225));//206 100  190 190
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
			imagen = new ImageIcon(getClass().getResource("/recursos/ficha2.png"));
			apostar.setIcon(new  ImageIcon(imagen.getImage().getScaledInstance(45,45, Image.SCALE_AREA_AVERAGING)));
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.gridwidth =1;
			constraints.gridheight = 1;
			//constraints.fill = constraints.NORTHEAST;
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
			
			constraints.gridx = 4;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			paneltexto1.add(espacio, constraints);
			
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
			//constraints.fill = constraints.SOUTHWEST;
			add(yoFull,constraints);	
			
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
		
		public void activarBotones(boolean turno) {
			pedir.setEnabled(turno);
			plantar.setEnabled(turno);
			if(turno) {
				apostar.setEnabled(turno);
				confirmarApuesta.setEnabled(turno);
				apostar.addActionListener(escucha);
				confirmarApuesta.addActionListener(escucha);
			}else {
				apostar.setEnabled(turno);
				confirmarApuesta.setEnabled(turno);
				apostar.removeActionListener(escucha);
				confirmarApuesta.addActionListener(escucha);
			}
			
		}
		//Coloca la imagen a la carta pedida por el usuario (dada por el servidor)
		public Carta colocarImagenCarta(DatosBlackJack datosRecibidos) {
			Carta carta = datosRecibidos.getCarta();
			System.out.println("Carta dada por el servidor: "+carta);
			return barajaNueva.revisarCarta(carta);
		}
		public ArrayList<Carta> asignarCartas(ArrayList<Carta> manoJugador) {
			
			ArrayList<Carta> manoJugadorAuxiliar = new ArrayList<Carta>();
			System.out.println(manoJugador);
			for(int i = 0; i < manoJugador.size(); i++) {
				manoJugadorAuxiliar.add(barajaNueva.revisarCarta(manoJugador.get(i)));		
			}
			return manoJugadorAuxiliar;
		}
		public void pintarCartasInicio(DatosBlackJack datosRecibidos) {
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
			//dealer.pintarCartasInicio(datosRecibidos.getManoDealer());
			dealer.pintarCartasInicio(asignarCartas(datosRecibidos.getManoDealer()));
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");
		}
		
		public void pintarTurno(DatosBlackJack datosRecibidos) {
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");	
			ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
			
			if(datosRecibidos.getJugador().contentEquals(yoId)){
				if(datosRecibidos.getJugadorEstado().equals("iniciar")) {
					activarBotones(true);
				}else {
					if(datosRecibidos.getJugadorEstado().equals("plantó") ){
						cliente.setTurno(false);
					}else {
						//yo.dibujarCarta(datosRecibidos.getCarta());
						yo.dibujarCarta(colocarImagenCarta(datosRecibidos));
						if(datosRecibidos.getJugadorEstado().equals("voló")) {
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
						   datosRecibidos.getJugadorEstado().equals("voló")) {
							//jugador2.dibujarCarta(datosRecibidos.getCarta());
							jugador2.dibujarCarta(colocarImagenCarta(datosRecibidos));
						}
					}else if(datosRecibidos.getJugador().equals(jugador3Id)) {
						if(datosRecibidos.getJugadorEstado().equals("sigue")||
						   datosRecibidos.getJugadorEstado().equals("voló")) {
							jugador3.dibujarCarta(colocarImagenCarta(datosRecibidos));
						}
					}
					else {
						//mensaje para PanelJuego dealer
						if(datosRecibidos.getJugadorEstado().equals("sigue") ||
						   datosRecibidos.getJugadorEstado().equals("voló")	||
						   datosRecibidos.getJugadorEstado().equals("plantó")) {
							//dealer.dibujarCarta(datosRecibidos.getCarta());
							dealer.dibujarCarta(colocarImagenCarta(datosRecibidos));
						}
					}
					
				}
						 	
		}
		
		public void pintarApuestas(DatosBlackJack datosRecibidos) {
			System.out.println("APUESTAS: ");
			int[] apuestas = datosRecibidos.getApuestas();
			for(int i = 0; i < 3; i++) {
				System.out.println("Apuesta #"+(i+1)+" "+apuestas[i]);
			}
			areaMensajes.append(datosRecibidos.getMensaje()+"\n");	
			ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
			
			if(datosRecibidos.getJugador().contentEquals(yoId)){
				if(datosRecibidos.getJugadorEstado().equals("iniciar")) {
					activarBotones(true);
				}else {
					if(datosRecibidos.getJugadorEstado().equals("plantó") ){
						cliente.setTurno(false);
					}else {
						System.out.println("Apuesta[0] = "+datosRecibidos.getApuestas()[0]);
						//yo.dibujarCarta(datosRecibidos.getCarta());
						apostar(String.valueOf(datosRecibidos.getApuestas()[0]), "1");
						if(datosRecibidos.getJugadorEstado().equals("voló")) {
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
							//jugador2.dibujarCarta(datosRecibidos.getCarta());
							System.out.println("Apuesta[1] = "+datosRecibidos.getApuestas()[1]);
							apostar(String.valueOf(datosRecibidos.getApuestas()[1]), "2");
							apostar(String.valueOf(datosRecibidos.getApuestas()[0]),"1");
						}
					}else if(datosRecibidos.getJugador().equals(jugador3Id)) {
						if(datosRecibidos.getJugadorEstado().equals("apuesta")) {
							System.out.println("Apuesta[2] = "+datosRecibidos.getApuestas()[2]);
							apostar(String.valueOf(datosRecibidos.getApuestas()[2]), "3");
							apostar(String.valueOf(datosRecibidos.getApuestas()[1]), "2");
							apostar(String.valueOf(datosRecibidos.getApuestas()[0]),"1");
						}
					}
					else {
						//mensaje para PanelJuego dealer
						if(datosRecibidos.getJugadorEstado().equals("apuesta")) {
							//dealer.dibujarCarta(datosRecibidos.getCarta());
							apostar("4000", "dealer");
						}
					}
					
				}
		}
		
		private void apostar(String valor, String jugador) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(jugador.equals("1")) {
						apuesta1.setText(valor);
					}else if(jugador.equals("2")) {
						apuesta2.setText(valor);
					}else if(jugador.equals("3")) {
						apuesta3.setText(valor);
					}else {
						apuestaDealer.setText(valor);
					}
				}});
		}
	   
	   private void enviarDatos(String mensaje) {
			// TODO Auto-generated method stub
		  ClienteBlackJack cliente = (ClienteBlackJack)this.getTopLevelAncestor();
		  cliente.enviarMensajeServidor(mensaje);
		}
		   
	  
	   
	   private class Escucha implements ActionListener{
		   
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
					enviarDatos("plantar");
					activarBotones(false);	
				}else {
					JOptionPane.showMessageDialog(null,"Debes apostar primero!");
				}
				
			}else if(actionEvent.getSource() == apostar) {
				cantidadApuesta += 100;
				aposto = true;
				apuesta1.setText(String.valueOf(cantidadApuesta));
				yoClase.pack();
			}else {
				if(cantidadApuesta > 0) {
					enviarDatos(String.valueOf(cantidadApuesta));
					cantidadApuesta = 0;
					confirmarApuesta.setEnabled(false);
					apostar.setEnabled(false);
					yoClase.pack();
				}else {
					JOptionPane.showMessageDialog(null,"Debes apostar primero!");
				}
				
			}
		}
	   }
	 
}
