package ftpCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Clase controladora de solicitudes a la Base de datos MySql
 * 
 * @author AlvaroFernandez
 *
 */
public class ConexionMysql {
	private static Connection con;
	
	/*private static String host = "fdb24.awardspace.net";
	private static String port = "3306";
	private static String database = "3261730_segsoc";
	private static String user = "3261730_segsoc";
	private static String password = "twocubes5";*/
	
	
	
	private final static String BASEDATOS = "segsoc";

	/**
	 * Metodo para iniciar la conexion con la base de datos
	 * @return boolean - True si la conexion es valida; False si no
	 */
	public static boolean iniciarConexion() {
		
		String url = null;
		
		//url ="jdbc:mysql://" + host + ":" + port + "/" + database;
		
		boolean cargada = false;
		try {
			// carga el controlador
			Class contr = Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("com.mysql.jdbc.Driver");
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/" + BASEDATOS, "root", "");
			
			
			//con = DriverManager.getConnection(url, user, password);
			
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
	 * Metodo para comprobar el login de un usuario con la base de datos
	 * 
	 * @param usuario String - Nombre del usuario
	 * @param contraseña String - Contraseña del usuario
	 * @param ftp ControladorFtp - Controlador con los metodos y datos del ftp
	 * @return int - 0 Si se logeo un funcionario; 1 Si se logeo un empresario; -1 El usuario no existe; -2 La contraseña no es correcta; -3 Fallo en la conexion
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
	 * Metodo para insertar en la tabla movimientos cada accion que se realice en el servidor ftp
	 * 
	 * @param usuario String - Nombre del usuario
	 * @param operacion String - Nombre de la operacion
	 * @param descripcion String - Texto descriptivo de la operacion
	 * @return booelan - True si se pudo insertar el registro; False si hubo algun fallo
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
	 */
	public static void cerrarConexion() {
		try {
			con.close();
			System.out.println("Conexiï¿½n sql cerrada");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
