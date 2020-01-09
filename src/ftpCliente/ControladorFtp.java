package ftpCliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ControladorFtp {
	private FTPClient cliente = new FTPClient();
	private String user = "user", pass = "";
	private String host;
	private String email;
	private FTPFile[] ficheros;
	private ArrayList<String> rutas;
	private int posicion;
	
	public ControladorFtp(String host, String usuario, String pass) {
		this.host = host;
		this.user = usuario;
		this.pass = pass;
		posicion = 0;
		rutas = new ArrayList<String>();
		rutas.add("/");
	}

	public boolean init() throws SocketException, IOException {
		boolean conectado = false;
		cliente.connect(host, 21);
		conectado = cliente.login(user, pass);
		ficheros = cliente.listFiles();
		cliente.enterLocalPassiveMode();
		return conectado;
	}

	public boolean subir(String archivo, String nombre) {
		BufferedInputStream in;
		boolean subido = false;
		try {
			in = new BufferedInputStream(new FileInputStream(archivo));
			if (cliente.storeFile(nombre, in)) {
				subido = true;
				System.out.println(archivo + nombre);
				ConexionMysql.insertarMovimiento(user, "Subir", "Archivo " + nombre + "subido");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return subido;
	}
	
	public void crearCarpeta(String nombreCarpeta) {
		try {
			if (cliente.makeDirectory(nombreCarpeta)) {
				ConexionMysql.insertarMovimiento(user, "Crear carpeta", "Carpeta "+nombreCarpeta+" creada");
				System.out.println("Carpeta creada");
			} else {
				System.out.println("ERROR AL CREAR CARPETA.");
			}
		} catch (IOException e) {
			System.out.println("ERROR E/S");
		}
	}

	public void borrarCarpeta(String nombreCarpeta) {
		try {
			FTPFile f = cliente.mlistFile(nombreCarpeta);
			
				
				if (f.isDirectory()) {
					if (cliente.removeDirectory(nombreCarpeta)) {
						ConexionMysql.insertarMovimiento(user, "Borrar carpeta", "Carpeta " + nombreCarpeta + " borrada");
						System.out.println("Carpeta borrada.");
					} else {
						String rutaNueva = rutas.get(posicion)+"/"+nombreCarpeta;
						cliente.changeWorkingDirectory(rutaNueva);
						posicion++;
						rutas.add(rutaNueva);
						
						ficheros = cliente.listFiles();
						
						for(FTPFile fichero : ficheros) {
							borrarCarpeta(fichero.getName());
						}
						
						posicion--;
						cliente.changeWorkingDirectory(rutas.get(posicion));
						cliente.removeDirectory(nombreCarpeta);
						//System.out.println("No se pudo borrar directorio.");
					}
				} else if (f.isFile()) {
					if (cliente.deleteFile(nombreCarpeta)) {
						ConexionMysql.insertarMovimiento(user, "Borrar fichero", "Fichero " + nombreCarpeta + " borrado");
						System.out.println("Fichero borrado.");
					} else {
						System.out.println("Fichero no existe.");
					}
				}
			
		} catch (IOException e) {
			System.out.println("ERROR E/S");
			e.printStackTrace();
		}
	}

	public void renombrar(String nombreAntiguo, String nombreNuevo) {
		try {
			cliente.rename(nombreAntiguo, nombreNuevo);
			ConexionMysql.insertarMovimiento(user, "Renombrar", "Archivo " + nombreAntiguo + " renombrado a "+nombreNuevo);
		} catch (IOException e) {
			System.out.println("ERROR E/S");
			e.printStackTrace();
		}
	}
	
	public void crearFichero(String fichero) {
		File fi = new File(fichero);
		try {
			fi.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (FileInputStream FicheroNuevo = new FileInputStream(fi);){
			cliente.storeFile(fichero, FicheroNuevo);
			ConexionMysql.insertarMovimiento(user, "Crear fichero", "Fichero "+fichero+ " creado");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.delete();
	}
	
	public void descargar(String nombre, JFileChooser elegir) {
		File fileDescargar;
		String archivoDirDestino = "";
		String dirDest = "";
		try {
			cliente.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		elegir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnF = elegir.showDialog(null, "Descargar..");
		if (returnF == JFileChooser.APPROVE_OPTION) {
			fileDescargar = elegir.getSelectedFile();
			dirDest = fileDescargar.getAbsolutePath();
			archivoDirDestino = dirDest + File.separator + nombre;
			System.out.println(archivoDirDestino);

			try {
				BufferedOutputStream salida = new BufferedOutputStream(new FileOutputStream(archivoDirDestino));
				salida.close();
				if (cliente.retrieveFile(nombre, salida)) {
					System.out.println(ConexionMysql.insertarMovimiento(user, "Descargar", "Fichero "+nombre+" descargado"));
					JOptionPane.showMessageDialog(null, nombre + "=> Se ha descargado correctamente...");
				}
				else {
					JOptionPane.showMessageDialog(null, nombre + "=> No se ha podido descargar...");
				}
			} catch (Exception e) {
				System.out.println("ERROR");
			}
		}
	}

	public FTPClient getCliente() {
		return cliente;
	}

	public void setCliente(FTPClient cliente) {
		this.cliente = cliente;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FTPFile[] getFicheros() {
		return ficheros;
	}

	public void setFicheros(FTPFile[] ficheros) {
		this.ficheros = ficheros;
	}

	public ArrayList<String> getRutas() {
		return rutas;
	}

	public void setRutas(ArrayList<String> rutas) {
		this.rutas = rutas;
	}

	
	public int getPosicion() {
		return posicion;
	}
	
	public void incrementarPosicion() {
		this.posicion++;
	}
	public void decrementarPosicion() {
		this.posicion--;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
}
