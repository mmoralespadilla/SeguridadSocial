package recibocorreo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.swing.JEditorPane;

/**
 * 
 * Esta clase recoge el mensaje que se va a mostrar
 * 
 * @author Diego Santos
 * @author Álvaro Fernández
 * @author Víctor López
 * @author Inma Jiménez
 * @author Miguel Morales
 */
public class MuestraMensaje {
 
	private int posMensaje;
	private Folder folder;

	/**
	 * Este constructor el mensaje que se quiere visualizar
	 * 
	 * @param folder     La lista de mensajes guardada
	 * @param posMensaje Posición del mensaje que se quiere visualizar
	 */
	public MuestraMensaje(Folder folder, int posMensaje) {
		this.folder = folder;
		this.posMensaje = posMensaje;
	}

	/**
	 * Esta función guarda todo el contenido del mensaje en formato html para
	 * enviarlo y descarga los ficheros adjuntos que contenga el correo
	 * 
	 * @return Contenido del mensaje en formato html
	 */
	public String mostrarMensaje() {
		StringBuilder txtMensaje = new StringBuilder();
		try {
			Message mensaje = folder.getMessage(posMensaje);
			if (mensaje.isMimeType("multipart/*")) {
				// Obtenemos el contenido, que es de tipo MultiPart
				Multipart multi = (Multipart) mensaje.getContent();
				// Extraemos cada una de las partes
				for (int j = 0; j < multi.getCount(); j++) {
					BodyPart bodyPart = multi.getBodyPart(j);
					if (bodyPart.isMimeType("text/html")) {
						txtMensaje.append(bodyPart.getContent().toString());
					} else if (bodyPart.isMimeType("multipart/*")) {
						Multipart tmp = (Multipart) bodyPart.getContent();
						for (int i = 0; i < tmp.getCount(); i++) {
							BodyPart bodyPart2 = (BodyPart) tmp.getBodyPart(i);
							if (bodyPart2.isMimeType("text/html")) {
								txtMensaje.append(bodyPart2.getContent().toString());
							}
						}
					} else if (bodyPart.getDisposition() != null
							&& bodyPart.getDisposition().equals(BodyPart.ATTACHMENT)) {
						MimeBodyPart filePart = (MimeBodyPart) multi.getBodyPart(j);
						filePart.saveFile(
								new File(System.getProperty("user.home") + "\\Downloads\\" + filePart.getFileName()));
						txtMensaje.append("<p><em>Se ha guardado fichero en: " + System.getProperty("user.home")
								+ "\\Downloads\\" + bodyPart.getFileName() + "</em></p>");
					}
				}
			} else {
				if (mensaje.isMimeType("text/plain") || mensaje.isMimeType("text/html")) {
					txtMensaje.append(mensaje.getContent().toString());
				}
			}
		} catch (MessagingException me) {
			System.err.println(me.getMessage());
			txtMensaje.append("<html>Mensaje erróneo</html>");
		} catch (IOException ie) {
			System.err.println(ie.getMessage());
			txtMensaje.append("<html>No se ha podido cargar el contenido : </html>");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			txtMensaje.append("<html>No se ha elegido ningún correo</html>");
		}
		return txtMensaje.toString();
	}
}
