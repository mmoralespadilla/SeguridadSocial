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

public class CreadorInterfaz {
	private static ControladorFtp ftp;
	private ControladorBotonesFtp controlBotonesFtp;
	private ControladorBotonesCorreo controlBotonesCorreo;
	private JLabel lblRuta;

	public CreadorInterfaz(ControladorFtp ftp, JLabel lblRuta) {
		this.ftp = ftp;
		this.lblRuta = lblRuta;
		controlBotonesFtp = new ControladorBotonesFtp(ftp, lblRuta);		
	}
	
	public CreadorInterfaz() {
		controlBotonesCorreo = new ControladorBotonesCorreo();		
	} 
		
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
	
	public JButton elaborarBoton(String titulo, int x , int y, int anchura) {
		JButton boton = new JButton(titulo);
		boton.addActionListener(controlBotonesFtp);
		boton.setBounds(x, y, anchura, 30);	
		ponerPropiedadesBoton(boton);
	
		return boton;
	}
	
	public void crearBotones(String titulo, int x , int y, JPanel panel) {
		JButton boton = new JButton(titulo);
		boton.addActionListener(controlBotonesCorreo);
		boton.setBounds(x, y, 159, 30);	
		ponerPropiedadesBoton(boton);
		panel.add(boton);
	}
	
	public JMenu crearMenu(String textoMenu, JMenuBar barraMenu) {
		JMenu menu = new JMenu(textoMenu);
		ponerPropiedadesMenu(menu);
		barraMenu.add(menu);
		return menu;
	}
	
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
	
	public void ponerPropiedadesMenu(JMenu menu) {
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);	
		menu.setForeground(Color.WHITE);
		menu.setBackground(new java.awt.Color(30, 105, 90));
		menu.setFont(fuenteTitulo);		
	}	
	public void ponerPropiedadesMenuItem(JMenuItem item) {
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 13);	
		item.setBackground(new java.awt.Color(218, 230, 228));
		item.setFont(fuenteTitulo);		
	}	
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
