package enviocorreo;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MainEnviar {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for SSL: 465
	 */
	public static void main(String[] args) {
		final String toEmail = "iamsegsoctrustme@gmail.com"; // can be any email id 
		final String fromEmail = "iamsegsoctrustme@gmail.com"; //requires valid gmail id
		final String pass = "segsoc123"; // correct password for gmail id
		String header = "Ojalá ste sea el weno";
		String body = "<H1>Vamooo</H1><br><img src=\"https://www.vectorlogo.es/wp-content/uploads/2018/01/logo-vector-instituto-nacional-de-la-seguridad-social.jpg\"/>";
		ArrayList<String> paths = new ArrayList<>();
		paths.add(System.getProperty("user.home") + "/Desktop/AndroidManifest.xml");
		paths.add(System.getProperty("user.home") + "/Desktop/gato felis.jpg");
		
		//EnvioCorreo connectMail = new EnvioCorreo(fromEmail, pass);
		//Session sesion = connectMail.conectar();
		//EnvioMensaje sentMsg = new EnvioMensaje(sesion, toEmail, fromEmail, header, body, paths);
		//sentMsg.enviarMensaje();
		
	        //EmailUtil.sendEmail(session, toEmail,"SSLEmail Testing Subject", "SSLEmail Testing Body");

	        //EmailUtil.sendAttachmentEmail(session, toEmail,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");

	        //EmailUtil.sendImageEmail(session, toEmail,"SSLEmail Testing Subject with Image", "SSLEmail Testing Body with Image");

	}

}
//Buscar Base64 imágenes embebidas
//Inline image java mail