package recibocorreo;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Esta clase conecta con el correo y descarga los mensajes
 * 
 * @author Diego Santos
 * @author Álvaro Fernández
 * @author Víctor López
 * @author Inma Jiménez
 * @author Miguel Morales
 */
public class MenuCorreo {

	private static Folder folder;
	private static Store store;
	private ArrayList<String> remitentes = new ArrayList<>();
	private ArrayList<String> asuntos = new ArrayList<>();
	private static String user;
	private static String pass;

	/**
	 * Este constructor recibe el correo y la contraseña a usar
	 * 
	 * @param user Correo del usuario
	 * @param pass Contraseña del correo
	 */
	public MenuCorreo(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	/**
	 * Constructor vacío para poder crear la clase sin usar parámetros
	 */
	public MenuCorreo() {

	}

	/**
	 * Este método asigna las propiedades de la clase, conecta con el correo y descarga los mensajes
	 */
	public void conectar() {
		try {
			Properties prop = new Properties();
			// Hay que usar SSL
			prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			prop.setProperty("mail.pop3.socketFactory.fallback", "false");

			// Puerto 995 para conectarse.
			prop.setProperty("mail.pop3.port", "995");
			prop.setProperty("mail.pop3.socketFactory.port", "995");

			Session sesion = Session.getInstance(prop);

			store = sesion.getStore("pop3s");

			store.connect("pop.gmail.com", user, pass);
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			System.out.println(folder.getMessageCount());
		} catch (NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Esta función guarda la lista de mensajes, con sus remitentes y asuntos aparte
	 * @return Todos los mensajes guardados
	 */
	public Message[] listarMensajes() {
		Message[] mensajes = null;
		try {
			mensajes = folder.getMessages();
			for (Message mensaje : mensajes)
				mensaje.setFlag(Flags.Flag.DELETED, false);
			mensajes = folder.getMessages();
			for (int i = 0; i < mensajes.length; i++) {
				remitentes.add(mensajes[i].getFrom()[0].toString());
				asuntos.add(mensajes[i].getSubject());
			}
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
		}
		/*
		 * for(String remitente : remitentes) System.out.println(remitente); int
		 * contador = 1; for(String asunto : asuntos) { System.out.println(contador++ +
		 * ": " + asunto); }
		 */
		return mensajes;
	}

	/**
	 * Este método cierra la conexión con el correo
	 */
	public void cerrarConexion() {
		try {
			store.close();
			folder.close(true);
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
		}
	}

	public static Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public static String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public static String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
