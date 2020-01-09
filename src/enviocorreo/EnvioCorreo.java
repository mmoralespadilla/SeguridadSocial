package enviocorreo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import ventanas.InterfazEscribirEmail;

public class EnvioCorreo {

	private String fromEmail;
	private String pass;
	private static Session session;
	private String toEmail;
	private String header;
	private String body;
	private ArrayList<String> paths = new ArrayList<>();

	public EnvioCorreo(String fromEmail, String pass, String toEmail, String header, String body,
			ArrayList<String> paths) {
		this.fromEmail = fromEmail;
		this.pass = pass;
		this.toEmail = toEmail;
		this.header = header;
		this.body = body;
		this.paths = paths;
	}

	public void conectar() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		props.put("mail.smtp.port", "465"); //SMTP Port
		
		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, pass);
			}
		});
		System.out.println("Se ha autentificado con éxito");
	}

	public void enviarMensaje() {
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");

			msg.setFrom(new InternetAddress(fromEmail, "Seguridad Social"));

			msg.setReplyTo(InternetAddress.parse(toEmail, false));

			msg.setSubject(header, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setContent("<h2>" + body.replace("\n", "<br>") + "</h2><br><img src=\"http://www.gelves.es/export/sites/gelves/.galleries/imagenes-noticias/inss.png\"/>", "text/html");
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
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
