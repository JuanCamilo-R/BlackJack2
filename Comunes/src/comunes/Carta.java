package comunes;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Carta implements Serializable {
    private String valor;
    private String palo;
	private transient Image imagen;
    public Carta(String valor, String palo) {
		this.valor = valor;
		this.palo = palo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPalo() {
		return palo;
	}

	public void setPalo(String palo) {
		this.palo = palo;
	}
	
	public String toString() {
		return valor+palo;
	}
	
	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
	
	public Image getImagen() {
		return imagen;
	}
}
