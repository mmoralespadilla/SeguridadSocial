package ftpCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Clase controladora de solicitudes a la Base de datos MySql |
 * 
 * Class that control requests for the data base
 * 
 * @author AlvaroFernandez
 *
 */
public class ConexionMysql {
	private static Connection con;
	
	
	private final static String BASEDATOS = "segsoc";

	/**
	 * Metodo para iniciar la conexion con la base de datos |
	 * 
	 * Method that initialize the connections to the data base
	 * 
	 * @return boolean - True si la conexion es valida; False si no | True if Connection is done properly; False if there is an error
	 */
	public static boolean iniciarConexion() {
		
		String url = null;
		
		boolean cargada = false;
		try {
			// carga el controlador
			Class contr = Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("com.mysql.jdbc.Driver");
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/" + BASEDATOS, "root", "");
			
			
			System.out.println("Conexion sql realizada");
			cargada = true;
		} catch (SQLException sqle) {
			con = null;
			System.out.println(sqle.getMessage());
			JOptionPane.showMessageDialog(null, "Error al establecer la conexion");
		}
		return cargada;
	}

	/**
	 * Metodo para comprobar el login de un usuario con la base de datos |
	 * 
	 * This method checks if the user is in the data base
	 * 
	 * @param usuario String - Nombre del usuario | User's name
	 * @param contrase�a String - Contrasena del usuario | User's password
	 * @param ftp ControladorFtp - Controlador con los metodos y datos del ftp | Controller with methods and ftp's data
	 * @return int - 0 Si se logeo un funcionario; 1 Si se logeo un empresario; -1 El usuario no existe; -2 La contrasena no es correcta; -3 Fallo en la conexion | 
	 * 0 If user is a 'funcionario';1 if the user is a entrepreneur; -1 if the user doesn't exist; -2 if the password is wrong; -3 if the connection fails
	 */
	public static int comprobarLogin(String usuario, String contrasenia, ControladorFtp ftp) {
		int tipoLogin = -3;
		if (iniciarConexion()) {
			String query = "select * from usuarios where usuario = '" + usuario + "'";
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				if (rs.next()) {
					String usuarioBuscado = rs.getString(1);
					String contraseniaBuscada = rs.getString(2);
					String emailBuscado = rs.getString(4);
					if (contraseniaBuscada.equals(contrasenia)) {
						ftp.setEmail(emailBuscado);
						tipoLogin = rs.getInt(5);
					} else {
						tipoLogin = -2;
					}
				} else {
					tipoLogin = -1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cerrarConexion();
		}
		return tipoLogin;
	}

	/**
	 * Metodo para insertar en la tabla movimientos cada accion que se realice en el servidor ftp |
	 * 
	 * Method that insert the operations done in the FTP at the Data Base
	 * @param usuario String - Nombre del usuario | User's name
	 * @param operacion String - Nombre de la operacion | Operation's name 
	 * @param descripcion String - Texto descriptivo de la operacion |  Operation description
	 * @return booelan - True si se pudo insertar el registro; False si hubo algun fallo | True if the insert was done correctly; False if there is any problem
	 */
	public static boolean insertarMovimiento(String usuario, String operacion, String descripcion) {
		boolean correcto = false;
		Date dt = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		System.out.println(currentTime);
		if (iniciarConexion()) {
			String query = "insert into movimientos values (default, '" + usuario + "','" + operacion + "','"
					+ currentTime + "', '" + descripcion + "')";
			System.out.println(query);
			try {
				Statement st = con.createStatement();
				st.execute(query);
				correcto = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cerrarConexion();
		}
		return correcto;
	}

	/**
	 * Metodo para cerrar la conexion
	 * 
	 * This method close the connection with the Data base
	 */
	public static void cerrarConexion() {
		try {
			con.close();
			System.out.println("Conexion sql cerrada");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Este metodo se encarga de recuperar el usuario que ha realizado una operacion en el FTP
	 * 
	 * This method recover user's name that made the operation at the FTP
	 * 
	 * @param comboBoxUsuarios JComboBox - Objeto seleccionado | Object selected
	 */
	public static void recuperarUsuariosMovimiento(JComboBox comboBoxUsuarios) {
		if(iniciarConexion()) {
			try {
				Statement st = con.createStatement();
				String query = "select usuario from movimientos group by usuario";
				ResultSet rs = st.executeQuery(query);
				while(rs.next()) {
					comboBoxUsuarios.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				cerrarConexion();
			}
		}
	}

	
	/**
	 * Metodo para recuperar los movimientos de un usuario en el ftp
	 * @param nombreUsuario String - Nombre del usuario que sera buscado en la base de datos
	 * @param dtm DefaultTableModer - Modelo de la tabla donde sse insertaran los datos
	 */
	public static void recargarTablaHistorial(String nombreUsuario, DefaultTableModel dtm) {
		if(iniciarConexion()) {
			try {
				Statement st = con.createStatement();
				String query = "select * from movimientos where usuario = '"+nombreUsuario+"'";
				ResultSet rs = st.executeQuery(query);
				while(rs.next()) {
					String id = Integer.toString(rs.getInt(1));
					String operacion = rs.getString(3);
					String fecha = rs.getDate(4).toString();
					String descripcion = rs.getString(5);
					Object[] row = {id,operacion,fecha,descripcion};
					dtm.addRow(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cerrarConexion();
		} 
	}
}
