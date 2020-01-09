package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Clase que crea una ventana con el historial
 * 
 * This class create view of historial
 *
 */
public class InterfazMostrarHistorial extends JDialog {
	private static JTable table;
	private static DefaultTableModel dtm;
	private static JComboBox comboBoxUsuarios;

	/**
	 * Create the dialog.
	 */
	public InterfazMostrarHistorial() {
		setTitle("Historial Movimientos");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 764, 351);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 53, 722, 250);
		getContentPane().add(scrollPane);

		dtm = new DefaultTableModel();
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		TableColumnModel columnModel = table.getColumnModel();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		dtm.addColumn("ID");
	    columnModel.getColumn(0).setPreferredWidth(100);
		dtm.addColumn("Operacion");
	    columnModel.getColumn(1).setPreferredWidth(300);
		dtm.addColumn("Fecha");
	    columnModel.getColumn(2).setPreferredWidth(100);
		dtm.addColumn("Descripcion");
	    columnModel.getColumn(3).setPreferredWidth(400);
		Object[] row = {"2", "Borrado", "2-5-1990", "Descripcion"};
		dtm.addRow(row);
		scrollPane.setViewportView(table);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(15, 20, 56, 16);
		getContentPane().add(lblUsuario);
		
		comboBoxUsuarios = new JComboBox();
		comboBoxUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recargarTabla();
			}
		});
		comboBoxUsuarios.setBounds(83, 17, 192, 19);
		getContentPane().add(comboBoxUsuarios);
		ftpCliente.ConexionMysql.recuperarUsuariosMovimiento(comboBoxUsuarios);
		
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
	 * Metodo que vacia la tabla, y la vuelve a rellenar con los datos de MySql
	 * 
	 * this method empty the table and fill it with the data from MySql
	 */
	public static void recargarTabla() {
		vaciarTabla();
		String nombreUsuario = (String) comboBoxUsuarios.getSelectedItem();
		ftpCliente.ConexionMysql.recargarTablaHistorial(nombreUsuario, dtm);
	}
}
