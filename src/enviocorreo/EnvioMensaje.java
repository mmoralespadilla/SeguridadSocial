package enviocorreo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnvioMensaje {

	Session session;
	String fromEmail;
	String toEmail;
	String header;
	String body;
	ArrayList<String> paths = new ArrayList<>();

	public EnvioMensaje(Session session, String fromEmail, String toEmail, String header, String body,
			ArrayList<String> paths) {
		this.session = session;
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.header = header;
		this.body = body;
		this.paths = paths;
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
			messageBodyPart.setContent(body, "text/html");

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