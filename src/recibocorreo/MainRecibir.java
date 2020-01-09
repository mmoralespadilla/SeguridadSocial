package recibocorreo;

import java.awt.Dimension;
import java.awt.Panel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class MainRecibir {
	private static JEditorPane editor = new JEditorPane();
	private static JDialog ventana = new JDialog();

	public static void main(String[] args) throws MessagingException, IOException {

		Panel panel = new Panel();
		String user = "iamsegsoctrustme@gmail.com";
		String pass = "segsoc123";
		MenuCorreo conmail = new MenuCorreo(user, pass);
		JEditorPane panelMensaje = new JEditorPane();
		panelMensaje.setContentType("text/html");

		panelMensaje.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(panelMensaje);
		ventana.add(scrollPane);
		ventana.pack();

		ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
}
