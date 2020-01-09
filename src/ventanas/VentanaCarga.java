package ventanas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/*
 * Clase que carga la ventana
 * 
 * Class that loads the frame
 */
public class VentanaCarga extends Thread{
	JWindow ventana;
	JLabel label;
	JPanel panel;
	
	public void crearVentana() {
		ventana = new JWindow();
		label = new JLabel();
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\imagen\\cargando.gif"));
		panel = new JPanel();
		panel.add(label);
		ventana.add(panel);
		ventana.pack();
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	  	
	}
	
	public void run() {
		crearVentana();
	}

	public JWindow getVentana() {
		return ventana;
	}

	public void setVentana(JWindow ventana) {
		this.ventana = ventana;
	}
	
}
