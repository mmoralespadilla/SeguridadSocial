package controladores;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import ftpCliente.ControladorFtp;
import ventanas.InterfazEmail;
import ventanas.InterfazFtp;
import ventanas.ModeloTextoInterfaz;

/**
 * Clase que implementa ActionListener para hacer el controlador de los botones
 * de la interfaz FTP |
 * 
 * This class implements actionListener in order to make the controller for the buttons interface of the FTP
 * 
 * @author AlvaroFernandez
 *
 */
public class ControladorBotonesFtp implements ActionListener {
	private ControladorFtp ftp;
	private ModeloTextoInterfaz textos;
	private JLabel lblRuta;

	/**
	 * Constructor que recibe nuestro controlador FTP |
	 * 
	 * This constructor gets FTP's controller
	 * 
	 * @param ftp ControladorFtp - Controlador que contendra los datos del usuario
	 *            logeado, y multiples metodos para controlar las acciones
	 *            realizadas | Controller that contains the user's data and a few methods so they can control the actions done
	 */
	public ControladorBotonesFtp(ControladorFtp ftp) {
		super();
		this.ftp = ftp;
		textos = new ModeloTextoInterfaz();
	}

	/**
	 * Constructor que recibe nuestro controlador FTP |
	 * 
	 * This constructor gets FTP's controller
	 * 
	 * @param ftp     ControladorFtp - Controlador que contendra los datos del
	 *                usuario logeado, y multiples metodos para controlar las
	 *                acciones realizadas | Controller that contains the user's data and 
	 *                a few methods so they can control the actions done
	 *                
	 * @param lblRuta JLabel - JLabel donde se ira actualizando la ruta cuando se
	 *                vaya moviendo el usuario | JLabel that will update the user's path
	 */
	public ControladorBotonesFtp(ControladorFtp ftp, JLabel lblRuta) {
		super();
		this.ftp = ftp;
		this.lblRuta = lblRuta;
		textos = new ModeloTextoInterfaz();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nomFichero = "";
		String ruta = "";
		String archivo;
		String boton = e.getActionCommand();
		// El boton pulsado es subir fichero
		if (boton.equals(textos.getTituloSubirFichero())) {
			try {
				int numeroFicheros = 0;
				JFileChooser cargar = new JFileChooser();
				cargar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				cargar.setMultiSelectionEnabled(true);
				if (cargar.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File[] ficheros = cargar.getSelectedFiles();
					for (int i = 0; i < ficheros.length; i++) {
						if (ftp.subir(ficheros[i].getAbsolutePath(), ficheros[i].getName())) {
							numeroFicheros++;
						}
					}
					if (numeroFicheros == 1) {
						JOptionPane.showMessageDialog(null, "Archivo subido con exito");
					} else if (numeroFicheros > 1) {
						JOptionPane.showMessageDialog(null, "Archivos subidos con exito");
					} else {
						JOptionPane.showMessageDialog(null, "Error al subir archivo");
					}
				}
			} catch (NullPointerException e1) {
				System.out.println("No ha seleccionado ningun fichero");
			}
		}
		// El boton pulsado es descargar fichero 
		else if (boton.equals(textos.getTituloDescargarFichero())) {
			try {
				JFileChooser elegir = new JFileChooser();
				archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 1);
				ftp.descargar(archivo, elegir);
			} catch (NullPointerException e1) {
				System.out.println("No ha seleccionado ningun directorio");
			} catch (ArrayIndexOutOfBoundsException a1) {
				System.out.println("No ha seleccionado ningun fichero de la tabla");
			}
		}
		// El boton pulsado es crear fichero
		else if (boton.equals(textos.getTituloCrearFichero())) {
			try {
				nomFichero = JOptionPane.showInputDialog("Nombre del fichero");
				if (nomFichero.length() != 0) {
					String extension = FilenameUtils.getExtension(nomFichero);
					if (extension.length() != 0) {
						ftp.crearFichero(nomFichero);
					} else {
						ftp.crearFichero(nomFichero + ".txt");
					}
				}
			} catch (NullPointerException e1) {
				System.out.println("Accion cancelada");
			}
			// El boton pulsado es eliminar
		} else if (boton.equals(textos.getTituloEliminar())) {
			try {
				int[] archivos = InterfazFtp.table.getSelectedRows();
				if (JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar?") == 0) {
					for (int i = 0; i < archivos.length; i++) {
						archivo = (String) InterfazFtp.dtm.getValueAt(archivos[i], 1);
						ftp.borrarCarpeta(archivo);
					}
				}  

			} catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Seleccione un elemento de la tabla");
			}
			// El boton pulsado es crear carpeta
		} else if (boton.equals(textos.getTituloCrearCarpeta())) {
			try {
				nomFichero = JOptionPane.showInputDialog("Nombre de la carpeta");
				if (nomFichero.length() != 0) {
					ftp.crearCarpeta(nomFichero);
				} else {

				}
			} catch (NullPointerException e1) {
				System.out.println("Accion cancelada");
			}
			// El boton pulsado es cambiar nombre
		} else if (boton.equals(textos.getTituloCambiarNombre())) {
			try {
				archivo = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 1);
				nomFichero = JOptionPane.showInputDialog("Nombre nuevo");
				ftp.renombrar(archivo, nomFichero);
			} catch (ArrayIndexOutOfBoundsException e1) {
				
			}
			// El boton pulsado es abrir correo
		} else if (boton.equals(textos.getTituloCorreoAbrir())) {
			InterfazEmail email = new InterfazEmail(ftp.getEmail(), ftp.getPass());
			email.setModal(true);
			email.setVisible(true);
			email.setEnabled(true);

			// El boton pulsado es atras
		} else if (boton.equals(textos.getTituloBotonAtras())) {
			ruta = "";
			if (ftp.getPosicion() >= 1) {
				ftp.decrementarPosicion();
				try {
					ftp.getCliente().changeWorkingDirectory(ftp.getRutas().get(ftp.getPosicion()));
					ruta = ftp.getCliente().printWorkingDirectory();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblRuta.setText("Ruta: " + ruta);
				InterfazFtp.recargarTabla();
			}
		} else if (boton.equals(textos.getTituloAyudaSobre())) {
			JOptionPane.showMessageDialog(null, "En construccion...", null, JOptionPane.ERROR_MESSAGE);
		} else if (boton.equals(textos.getTituloServidorInfor())) {
			JOptionPane.showMessageDialog(null, "En construccion...", null, JOptionPane.ERROR_MESSAGE);
		} else if (boton.equals(textos.getTituloServidorHistorial())) {
			JOptionPane.showMessageDialog(null, "En construccion...", null, JOptionPane.ERROR_MESSAGE);
		}
		InterfazFtp.recargarTabla();
	}
}
