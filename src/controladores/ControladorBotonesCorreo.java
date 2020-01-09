package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.DefaultEditorKit;

import enviocorreo.EnvioCorreo;
import recibocorreo.MenuCorreo;
import recibocorreo.MuestraMensaje;
import ventanas.InterfazEmail;
import ventanas.InterfazEscribirEmail;
import ventanas.ModeloTextoInterfaz;

public class ControladorBotonesCorreo implements ActionListener {

	private ModeloTextoInterfaz textos;
	private ArrayList<String> paths = new ArrayList<>();

	public ControladorBotonesCorreo() {
		super();
		textos = new ModeloTextoInterfaz();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String boton = e.getActionCommand();
		if (boton.equals(textos.getTituloAccionesRefrescar())) {
			InterfazEmail.recargarTabla();

		} else if (boton.equals(textos.getTituloAccionesLeer())) {
			JDialog ventana = new JDialog();
			MenuCorreo folder = new MenuCorreo();
			JEditorPane panelMensaje = new JEditorPane();
			panelMensaje.setContentType("text/html");
			MuestraMensaje verMensaje = new MuestraMensaje(folder.getFolder(),
					InterfazEmail.getTable().getRowCount() - InterfazEmail.getTable().getSelectedRow());
			panelMensaje.setText(verMensaje.mostrarMensaje());
			panelMensaje.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(panelMensaje);
			ventana.add(scrollPane);
			ventana.pack();

			ventana.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			ventana.setModal(true);
			ventana.setVisible(true);
	
		} else if (boton.equals(textos.getTituloAccionesCrear())) {			
			InterfazEscribirEmail escribir = new InterfazEscribirEmail();
			escribir.setModal(true);
			escribir.setVisible(true);
		//	controladores.ControladorBotonesFtp.email.setVisible(false);			
			
			
		} else if (e.getSource().equals(InterfazEscribirEmail.getListaContactos())) {
			InterfazEscribirEmail.setTextFieldPara(InterfazEscribirEmail.getEmails()
					.get(InterfazEscribirEmail.getListaContactos().getSelectedIndex()));
			
		} else if (boton.equals(textos.getTituloBotonAdjuntar())) {
			JFileChooser adjuntar = new JFileChooser();
			adjuntar.showOpenDialog(null);
			adjuntar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			paths.add(adjuntar.getSelectedFile().getAbsolutePath());
			InterfazEscribirEmail.getListaAdjuntos().addItem(adjuntar.getSelectedFile().getName());
		
		} else if (boton.equals(textos.getTituloBotonEliminar())) {
			for (int i = 0; i < paths.size(); i++) {
				if (paths.get(i).endsWith(InterfazEscribirEmail.getListaAdjuntos().getSelectedItem().toString())) {
					InterfazEscribirEmail.getListaAdjuntos().removeItemAt(i);
					paths.remove(i);
				}
			}
			
		} else if (boton.equals(textos.getTituloBotonEnviar())) {
			MenuCorreo datosUsu = new MenuCorreo(InterfazEmail.getUser(), InterfazEmail.getPass());
			enviocorreo.EnvioCorreo conecEnviar = new EnvioCorreo(datosUsu.getUser(), datosUsu.getPass(),
					InterfazEscribirEmail.getTextFieldPara().getText(),
					InterfazEscribirEmail.getTextFieldAsunto().getText(),
					InterfazEscribirEmail.getAreaTexto().getText(), paths);
			conecEnviar.conectar();
			conecEnviar.enviarMensaje();
			JOptionPane.showConfirmDialog(null, textos.getEnviadoConExito(), textos.getMensajeEnviado(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE); ///////////////////////// MODIFICADO
		}else if (boton.equals(textos.getTituloAyudaSobre())) {
			JOptionPane.showMessageDialog(null, "En construccion...", null, JOptionPane.ERROR_MESSAGE);
		}
	}
}