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

public class MenuCorreo {


	private static Folder folder;
	private static Store store;
	private ArrayList<String> remitentes = new ArrayList<>();
	private ArrayList<String> asuntos = new ArrayList<>();
	private static String user;
	private static String pass;
	
	public MenuCorreo(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	public MenuCorreo() {
		
	}
	
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
		/*for(String remitente : remitentes)
			System.out.println(remitente);
		int contador = 1;
		for(String asunto : asuntos) {
			System.out.println(contador++ + ": " + asunto);
		}*/
		return mensajes;
	}
		
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
