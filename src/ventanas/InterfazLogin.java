package ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ftpCliente.ConexionMysql;
import ftpCliente.ControladorFtp;

public class InterfazLogin extends JFrame {

	private BufferedImage image;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JPasswordField textFieldContrasena;
	private JPanel panelLogin;
	private JPanel panelImagen;
	private String rutaImagen;
	private static ModeloTextoInterfaz modeloTexto;
	private JTextField textFierldIp;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazLogin frame = new InterfazLogin();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	
	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public InterfazLogin(){
		modeloTexto = new ModeloTextoInterfaz();
		setTitle(modeloTexto.getTituloLogin());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 524, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new java.awt.Color(218, 230, 228));

		panelLogin = new JPanel();
		panelLogin.setBorder(new LineBorder(new Color(30, 105, 90), 3));

		panelLogin.setBounds(40, 86, 427, 218);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		panelLogin.setBackground(new java.awt.Color(218, 230, 228));

		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);

		JLabel lblUsuario = new JLabel(modeloTexto.getTituloUsuario());
		lblUsuario.setBounds(31, 28, 75, 16);
		lblUsuario.setFont(fuenteTitulo);
		panelLogin.add(lblUsuario);

		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(134, 25, 231, 25);

		panelLogin.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);

		JLabel lblContrasena = new JLabel(modeloTexto.getTituloContrasena());
		lblContrasena.setBounds(31, 80, 90, 16);
		lblContrasena.setFont(fuenteTitulo);
		panelLogin.add(lblContrasena);

		textFieldContrasena = new JPasswordField();
		textFieldContrasena.setColumns(10);
		textFieldContrasena.setBounds(134, 75, 231, 25);
		panelLogin.add(textFieldContrasena);

		JButton btnLogin = new JButton(modeloTexto.getTituloLogin());
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = textFieldContrasena.getText().toString();
				String usuario = textFieldUsuario.getText().toString();
				String direccionFtp = textFierldIp.getText();
				ControladorFtp ftp = new ControladorFtp(direccionFtp,usuario, password);
				int comprobarLogin = ConexionMysql.comprobarLogin(usuario, password, ftp);
				System.out.println(ftp.getEmail() + " //");
				if (comprobarLogin >= 0) {
					try {
						if(ftp.init()) {
							try {
								System.out.println(ftp.getEmail() + " //");
								InterfazFtp frame = new InterfazFtp(ftp);
								dispose();
								frame.setVisible(true);
							} catch (Exception c) {
								c.printStackTrace();
							}
						}
					} catch (SocketException a) {
						// TODO Auto-generated catch block
						a.printStackTrace();
					} catch (IOException b) {
						// TODO Auto-generated catch block
						b.printStackTrace();
					}
				} else if (comprobarLogin == -2){
					JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
				} else if (comprobarLogin == -1 ) {
					JOptionPane.showMessageDialog(null, "Usuario no existe");
				}
			}
		});
		btnLogin.setBounds(165, 172, 97, 30);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new java.awt.Color(30, 105, 90));
		btnLogin.setFont(fuenteTitulo);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		btnLogin.setBorder(compound);
		panelLogin.add(btnLogin);
		
		JLabel labelFtp = new JLabel(modeloTexto.getDireccionFtp());
		labelFtp.setFont(new Font("Dialog", Font.BOLD, 14));
		labelFtp.setBounds(31, 127, 90, 16);
		panelLogin.add(labelFtp);
		
		textFierldIp = new JTextField();
		textFierldIp.setColumns(10);
		textFierldIp.setBounds(134, 124, 231, 25);
		panelLogin.add(textFierldIp);
		
		rutaImagen = "imagen\\logo.png";
  
		try {
			image = ImageIO.read(new File(rutaImagen));
			JLabel Imagen = new JLabel(new ImageIcon(image));
			Imagen.setBounds(new Rectangle(-26, -137, 495, 322));
			contentPane.add(Imagen);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
