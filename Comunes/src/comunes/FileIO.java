/*
 * Jennyfer Belalcazar 		- 1925639-3743
 * Samuel Riascos Prieto 	- 1922540-3743
 * Juan Camilo Randazzo		- 1923948-3743
 */
package comunes;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class FileIO.
 */
public class FileIO implements Serializable {
	
	/**
	 * Read image file.
	 *
	 * @param requestor the requestor
	 * @param fileName the file name
	 * @return the buffered image
	 */
	public static BufferedImage readImageFile(Object requestor, String fileName) {
		BufferedImage image = null;
		try {
			InputStream input = requestor.getClass().getResourceAsStream(fileName);
			image = ImageIO.read(input);					
		}catch(Exception e) {
			String message = "El archivo " + fileName + " No se pudo abrir.";
		    JOptionPane.showMessageDialog(null, message); 
		}
       return image;
	}
}