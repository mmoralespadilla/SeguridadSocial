package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.text.Normalizer.Form;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.commons.io.FilenameUtils;

import ftpCliente.ControladorFtp;
import ftpCliente.MouseAdapterFtp;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class InterfazFtp extends JFrame {

	private JPanel contentPane;
	public static DefaultTableModel dtm;
	public static JTable table;
	private static ControladorFtp ftp;
	private static ModeloTextoInterfaz modeloTexto;
	private static CreadorInterfaz creador;
	private JLabel lblRuta;

	/**
	 * Create the frame.
	 */
	public InterfazFtp(ControladorFtp ftp) {
		// Actualizar
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);
		modeloTexto = new ModeloTextoInterfaz();
		this.ftp = ftp;
		lblRuta = new JLabel(modeloTexto.getTituloRuta());
		lblRuta.setBounds(50, 30, 515, 16);
		lblRuta.setFont(fuenteTitulo);
		creador = new CreadorInterfaz(ftp, lblRuta);
		ArrayList<String> titulosMenuItemArchivo;
		ArrayList<String> titulosMenuItemTransferencia;
		ArrayList<String> titulosMenuItemServidor;
		ArrayList<String> titulosMenuItemAyuda;
		ArrayList<String> titulosMenuItemCorreo;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 547);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new java.awt.Color(30, 105, 90));
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		menuBar.setBorder(compound);
		setJMenuBar(menuBar);

		// File menu
		JMenu mnArchivo = creador.crearMenu(modeloTexto.getTituloArchivo(), menuBar);
		JMenuItem mnCambioUsuario = new JMenuItem(modeloTexto.getTituloCambiarUsuario());
		Font fuenteTituloItem = new Font("Dialog", Font.BOLD, 13);
		mnCambioUsuario.setBackground(new java.awt.Color(218, 230, 228));
		mnCambioUsuario.setFont(fuenteTitulo);
		mnArchivo.add(mnCambioUsuario);
		mnCambioUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				InterfazLogin login = new InterfazLogin();
				login.setVisible(true);
			}
		});

		// Transfer menu
		JMenu mnTransferencia = creador.crearMenu(modeloTexto.getTituloTransferencia(), menuBar);
		titulosMenuItemTransferencia = llenarListaTituloTransferencia();
		creador.crearItems(titulosMenuItemTransferencia, mnTransferencia, 1);

		// Server menu
		JMenu mnServidor = creador.crearMenu(modeloTexto.getTituloServidor(), menuBar);
		titulosMenuItemServidor = llenarListaTituloServidor();
		creador.crearItems(titulosMenuItemServidor, mnServidor, 1);

		// Help menu
		JMenu mnAyuda = creador.crearMenu(modeloTexto.getTituloAyuda(), menuBar);
		creador.ponerPropiedadesMenu(mnAyuda);
		menuBar.add(mnAyuda);
		titulosMenuItemAyuda = llenarListaTituloAyuda();
		creador.crearItems(titulosMenuItemAyuda, mnAyuda, 1);

		// Email menu
		JMenu mnCorreo = creador.crearMenu(modeloTexto.getTituloCorreo(), menuBar);
		creador.ponerPropiedadesMenu(mnCorreo);
		menuBar.add(mnCorreo);
		titulosMenuItemCorreo = llenarListaTituloCorreo();
		creador.crearItems(titulosMenuItemCorreo, mnCorreo, 1);

		// Global Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new java.awt.Color(218, 230, 228));

		// Buttons
		creador.crearBotones(titulosMenuItemTransferencia, 60, contentPane, 1);

		// Label
		JLabel lblUsuario = new JLabel(modeloTexto.getTituloUsuario());
		lblUsuario.setBounds(715, 30, 205, 16);
		lblUsuario.setFont(fuenteTitulo);
		contentPane.add(lblUsuario);

		// Table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 60, 615, 365);
		contentPane.add(scrollPane);
		dtm = new DefaultTableModel();
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.setDefaultRenderer(Object.class,new CellRender());
	
		MouseAdapterFtp adapterTable = new MouseAdapterFtp(ftp, lblRuta);
		table.addMouseListener(adapterTable);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// Cabeceras de la tabla
		dtm.addColumn(modeloTexto.getCabeceraTipoArchivo());
		dtm.addColumn(modeloTexto.getTituloCabeceraTabla());
		TableColumnModel columnModel = table.getColumnModel();
		table.setRowHeight(40);
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(0).setResizable(false);
		columnModel.getColumn(1).setPreferredWidth(800);
		columnModel.getColumn(1).setResizable(false);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
  
		JButton btnAtras = creador.elaborarBoton(modeloTexto.getTituloBotonAtras(), 560, 20, 105);
		lblUsuario.setText("Usuario: " + ftp.getUser());
		contentPane.add(btnAtras);
		contentPane.add(lblRuta);
		recargarTabla();
	}

	private ArrayList<String> llenarListaTituloCorreo() {
		ArrayList<String> titulosMenuItemCorreo = new ArrayList();
		titulosMenuItemCorreo.add(modeloTexto.getTituloCorreoAbrir());
		return titulosMenuItemCorreo;
	}

	private ArrayList<String> llenarListaTituloAyuda() {
		ArrayList<String> titulosMenuItemAyuda = new ArrayList();
		titulosMenuItemAyuda.add(modeloTexto.getTituloAyudaSobre());
		return titulosMenuItemAyuda;
	}

	private ArrayList<String> llenarListaTituloTransferencia() {
		ArrayList<String> titulosMenuItemTransferencia = new ArrayList();
		titulosMenuItemTransferencia.add(modeloTexto.getTituloSubirFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloDescargarFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCrearFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCrearCarpeta());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloEliminar());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCambiarNombre());
		return titulosMenuItemTransferencia;

	}

	private ArrayList<String> llenarListaTituloServidor() {
		ArrayList<String> titulosMenuItemServidor = new ArrayList();
		titulosMenuItemServidor.add(modeloTexto.getTituloServidorInfor());
		titulosMenuItemServidor.add(modeloTexto.getTituloServidorHistorial());
		return titulosMenuItemServidor;
	}

	/**
	 * Metodo para vaciar la tabla
	 * 
	 * This method empty the table
	 */
	public static void vaciarTabla() {
		int a = table.getRowCount() - 1;
		for (int i = a; i >= 0; i--) {
			dtm.removeRow(dtm.getRowCount() - 1);
		}
	}

	/**
	 * Metodo que vacia la tabla, y la vuelve a rellenar con los datos de neodatis
	 * 
	 * This method empty the table and fill it with the data from neodatis
	 */
	public static void recargarTabla() {
		vaciarTabla();
		try {
			ftp.setFicheros(ftp.getCliente().listFiles());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < ftp.getFicheros().length; i++) {
			String nomFichero = ftp.getFicheros()[i].getName();
			String extension = FilenameUtils.getExtension(nomFichero);
			JLabel foto= controladores.ControladorIconosFicheros.recuperarIcono(extension);
			Object[] row = { foto, nomFichero };
			dtm.addRow(row);
		}
	}

}
