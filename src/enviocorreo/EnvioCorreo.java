package enviocorreo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * Esta clase se encarga de enviar el correo |
 * 
 * This class take care of send the email
 * 
 * @author Diego Santos
 * @author Alvaro Fernandez
 * @author VIctor Lopez
 * @author Inma Jimenez
 * @author Miguel Morales
 *
 */
public class EnvioCorreo {

	private String fromEmail;
	private String pass;
	private static Session session;
	private String toEmail;
	private Address[] direcciones;
	private String header;
	private String body;
	private ArrayList<String> paths = new ArrayList<>();
	private ArrayList<String> toEmails;
	private String nombreUsuario;

	/**
	 * Este constructor coge todos los datos necesarios para enviar el correo
	 * 
	 * This constructor gets neccessary data to send emails
	 * 
	 * @param fromEmail String - El correo de la persona que envia el mensaje |
	 *                  Sender's email
	 * @param pass      String - La contrasena del correo con el que se envia el
	 *                  mensaje | User's password
	 * @param toEmail   String - El correo de la persona que recibira el mensaje |
	 *                  recipent's email
	 * @param header    String - El asunto del correo que se envia | email's
	 *                  header
	 * @param body      String - El contenido del mensaje a enviar | email's body
	 * @param paths     String - Las rutas de fichero que posiblemente se envien
	 *                  junto al correo | File's path which are attached to the
	 *                  email
	 */
	public EnvioCorreo(String fromEmail, String pass, String toEmail, String header, String body,
			ArrayList<String> paths, String nombreUsuario) {
		this.fromEmail = fromEmail;
		this.pass = pass;
		this.toEmail = toEmail;
		this.header = header;
		this.body = body;
		this.paths = paths;
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Este m�todo asigna las propiedades de la clase y se conecta al correo |
	 * 
	 * This method assign properties for the class and connect to the email
	 * 
	 */
	public void conectar() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
		props.put("mail.smtp.port", "465"); // SMTP Port

		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, pass);
			}
		});
		System.out.println("Se ha autentificado con exito");
	}

	/**
	 * Este metodo re�ne en un objeto todos los datos requeridos para introducir
	 * en el correo y lo envia
	 * 
	 * This method gets all data necessary to send an email
	 */
	public boolean enviarMensaje() {
		try {

			guardarRemitentes();

			direcciones = new Address[toEmails.size()];

			for (int i = 0; i < direcciones.length; i++) {
				direcciones[i] = new InternetAddress(toEmails.get(i));
			}

			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");

			msg.setFrom(new InternetAddress(fromEmail, nombreUsuario));

			msg.setReplyTo(direcciones);

			msg.setSubject(header, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, direcciones);
			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setContent("<h2>" + body.replace("\n", "<br>")
					+ "</h2><br><img src=\"http://www.gelves.es/export/sites/gelves/.galleries/imagenes-noticias/inss.png\"/>",
					"text/html");
			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart("related");
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			for (String path : paths) {
				File file = new File(path);
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(path);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(file.getName());
				multipart.addBodyPart(messageBodyPart);
			}

			/// Send the complete message parts
			msg.setContent(multipart);

			// Send message
			Transport.send(msg);
			System.out.println("El correo se ha enviado satisfactoriamente");
			return true;
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void guardarRemitentes() {
		toEmails = new ArrayList<>();
		String email = "";
		for (int i = 0; i < toEmail.length(); i++) {
			email += toEmail.substring(i, i + 1);
			if (toEmail.substring(i, i + 1).equals(",")) {
				email = email.substring(0, email.length() - 1);
				toEmails.add(email.trim());
				email = "";
				// comas++;
			}
		}
		toEmails.add(email.trim());
	}
}
