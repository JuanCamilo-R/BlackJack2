package clientebj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
		private JButton pedir, plantar;
		private JPanel panelYo, panelBotones, yoFull, panelDealer,panelJugador2, panelJugador3;
		private Baraja barajaNueva; 
		private String yoId, jugador2Id, jugador3Id;
		private GridBagConstraints constraints;
		//private DatosBlackJack datosRecibidos;
		private Escucha escucha;
		
		public VentanaSalaJuego(String yoId, String jugador2Id, String jugador3Id) {
			this.yoId = yoId;
			this.jugador2Id = jugador2Id;
			this.jugador3Id = jugador3Id;
			//this.datosRecibidos=datosRecibidos;
			barajaNueva = new Baraja();			
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
			constraints = new GridBagConstraints();
			panelDealer = new JPanel();
			dealer = new PanelJugador("Dealer");
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelDealer.add(dealer);
			add(panelDealer,constraints);		
			
			panelJugador2 = new JPanel();
			jugador2= new PanelJugador(jugador2Id);
			constraints.gridx = 2;
			constraints.gridy = 2;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelJugador2.add(jugador2);
			add(panelJugador2,constraints);	
			
			panelJugador3 = new JPanel();
			jugador3 = new PanelJugador(jugador3Id);
			constraints.gridx = 2;
			constraints.gridy = 0;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			panelJugador3.add(jugador3);
			add(panelJugador3, constraints);
			
			areaMensajes = new JTextArea(8,18);
			JScrollPane scroll = new JScrollPane(areaMensajes);	
			Border blackline;
			blackline = BorderFactory.createLineBorder(Color.black);
			TitledBorder bordes;
			bordes = BorderFactory.createTitledBorder(blackline, "Area de Mensajes");
	        bordes.setTitleJustification(TitledBorder.CENTER);
			scroll.setBorder(bordes);
			areaMensajes.setOpaque(false);
			areaMensajes.setBackground(new Color(0, 0, 0, 0));
			areaMensajes.setEditable(false);

			scroll.getViewport().setOpaque(false);
			scroll.setOpaque(false);
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			constraints.gridheight = 1;
			add(scroll,constraints);
			
			panelYo = new JPanel();
			panelYo.setLayout(new BorderLayout());
			yo = new PanelJugador(yoId);
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
			
			yoFull = new JPanel();
			yoFull.setLayout(new GridLayout(2,1));
			yoFull.setPreferredSize(new Dimension(190,190));//206 100
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth =1;
			constraints.gridheight = 1;
			yoFull.add(panelYo, constraints);
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.gridwidth =1;
			constraints.gridheight = 1;
			yoFull.add(panelBotones, constraints);
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth =1;
			constraints.gridheight = 2;
			constraints.anchor = constraints.SOUTHWEST;
			//constraints.fill = constraints.SOUTHWEST;
			add(yoFull,constraints);	
		}
		
		public void activarBotones(boolean turno) {
			pedir.setEnabled(turno);
			plantar.setEnabled(turno);
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
			}else if(datosRecibidos.getIdJugadores()[1].equals(yoId)) {
				yo.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador1()));
				jugador2.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador2()));
				jugador3.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador3()));
			} {
				yo.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador2()));
				jugador2.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador1()));
				jugador3.pintarCartasInicio(asignarCartas(datosRecibidos.getManoJugador3()));
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
						//mensaje para PanelJuego jugador2
						if(datosRecibidos.getJugadorEstado().equals("sigue")||
						   datosRecibidos.getJugadorEstado().equals("voló")) {
							//jugador2.dibujarCarta(datosRecibidos.getCarta());
							jugador3.dibujarCarta(colocarImagenCarta(datosRecibidos));
							
						}
					}else {
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
				enviarDatos("pedir");				
			}else {
				//enviar plantar al servidor
				enviarDatos("plantar");
				activarBotones(false);
			}
		}
	   }
}
