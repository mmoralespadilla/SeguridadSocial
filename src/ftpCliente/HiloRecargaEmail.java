package ftpCliente;

import ventanas.InterfazEmail;

/**
 * Hilo para recargar la tabla de los mensajes recibidos cada cierto tiempo
 * 
 * @author AlvaroFernandez
 *
 */
public class HiloRecargaEmail extends Thread{
		
	public void run() {
		while(true) {
			try {
				sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			InterfazEmail.recargarTabla();
		}
	}
}
