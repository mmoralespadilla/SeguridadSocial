package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controladores.ControladorBotonesCorreo;
import controladores.ControladorBotonesFtp;
import ftpCliente.ControladorFtp;
/**
 * 
 * Clase que crea el interfaz para las diferentes ventanas
 * 
 * This class create the interface for the differents views
 */
public class CreadorInterfaz {
	private static ControladorFtp ftp;
	private ControladorBotonesFtp controlBotonesFtp;
	private ControladorBotonesCorreo controlBotonesCorreo;
	private JLabel lblRuta;

	/**
	 * Metodo para crear la interfaz del FTP
	 * 
	 * This method creates a interface for the FTP
	 * @param ftp ControladorFTP - Objeto de ControladorFTP | Object of ControladorFTP
	 * @param lblRuta
	 */
	public CreadorInterfaz(ControladorFtp ftp, JLabel lblRuta) {
		this.ftp = ftp;
		this.lblRuta = lblRuta;
		controlBotonesFtp = new ControladorBotonesFtp(ftp, lblRuta);		
	}
	/**
	 * Constructor que inicializa el controlador de botones
	 * 
	 * This constructor start the controller of buttons
	 */
	public CreadorInterfaz() {
		controlBotonesCorreo = new ControladorBotonesCorreo();		
	} 
		
	/**
	 * Metodo que crea botones para la interfaz
	 * 
	 * This method create buttons for the interface
	 * 
	 * @param titulos
	 * @param y
	 * @param panel
	 * @param tipoControl
	 */
	public void crearBotones(ArrayList <String> titulos, int y, JPanel panel, int tipoControl) {	
		for (int i = 0 ; i<titulos.size(); i++) {			
			JButton boton = new JButton(titulos.get(i));
						
			if(tipoControl ==2) {
				boton.addActionListener(controlBotonesCorreo);
			}else {
				boton.addActionListener(controlBotonesFtp);
			}			
			boton.setBounds(715, y, 160, 40);
			y += 65;
			ponerPropiedadesBoton(boton);
			panel.add(boton);	
		}	
			
	}
	
	/**
	 * Metodo que inserta el action listener en los boton es
	 * 
	 * This method enable the actionlistener in the buttons
	 * @param titulo
	 * @param x
	 * @param y
	 * @param anchura
	 * @return
	 */
	public JButton elaborarBoton(String titulo, int x , int y, int anchura) {
		JButton boton = new JButton(titulo);
		boton.addActionListener(controlBotonesFtp);
		boton.setBounds(x, y, anchura, 30);	
		ponerPropiedadesBoton(boton);
	
		return boton;
	}
	
	/**
	 * Metodo que crea los botones |
	 * 
	 * This method create the buttons
	 * 
	 * @param titulo
	 * @param x
	 * @param y
	 * @param panel
	 */
	public void crearBotones(String titulo, int x , int y, JPanel panel) {
		JButton boton = new JButton(titulo);
		boton.addActionListener(controlBotonesCorreo);
		boton.setBounds(x, y, 159, 30);	
		ponerPropiedadesBoton(boton);
		panel.add(boton);
	}
	
	/**
	 * Metodo que crea el menu
	 * 
	 * This method create the menu
	 * @param textoMenu
	 * @param barraMenu
	 * @return
	 */
	public JMenu crearMenu(String textoMenu, JMenuBar barraMenu) {
		JMenu menu = new JMenu(textoMenu);
		ponerPropiedadesMenu(menu);
		barraMenu.add(menu);
		return menu;
	}
	
	/**
	 * Metodo que crea objetos en el menu | 
	 * 
	 * This method create objects in the menu
	 * @param titulos ArrayList<String> 
	 * @param menu JMenu
	 * @param tipoControl int
	 */
	public void crearItems(ArrayList <String> titulos, JMenu menu,  int tipoControl) {
		for (int i = 0 ; i<titulos.size(); i++) {
			JMenuItem mntmTransfer = new JMenuItem(titulos.get(i));
			
			if(tipoControl ==2) {
				mntmTransfer.addActionListener(controlBotonesCorreo);
			}else {
				mntmTransfer.addActionListener(controlBotonesFtp);
			}				
			ponerPropiedadesMenuItem(mntmTransfer);
			menu.add(mntmTransfer);
		}		
	}
	
	/**
	 * Metodo que configura las propiedades del menu
	 * 
	 * This method sets the properties of the menu
	 * @param menu
	 */
	public void ponerPropiedadesMenu(JMenu menu) {
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);	
		menu.setForeground(Color.WHITE);
		menu.setBackground(new java.awt.Color(30, 105, 90));
		menu.setFont(fuenteTitulo);		
	}	
	
	/**
	 * Metodo que configura los menus de un objeto
	 * 
	 * Method that configure the properties of an object
	 * @param item
	 */
	public void ponerPropiedadesMenuItem(JMenuItem item) {
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 13);	
		item.setBackground(new java.awt.Color(218, 230, 228));
		item.setFont(fuenteTitulo);		
	}	
	/**
	 * Metodo que configura el boton con unas propiedades determinadas
	 * 
	 * This method configure the button with a especific properties
	 * 
	 * @param boton JButton - boton a configurar | Button that will be configure
	 */
	public void ponerPropiedadesBoton(JButton boton){
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);	
		boton.setForeground(Color.WHITE);
		boton.setBackground(new java.awt.Color(30, 105, 90));
		boton.setFont(fuenteTitulo);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);	
		boton.setBorder(compound);		
	}
}
