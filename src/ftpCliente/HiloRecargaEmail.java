package ftpCliente;

import ventanas.InterfazEmail;

public class HiloRecargaEmail extends Thread{
		
	public void run() {
		while(true) {
			try {
				sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InterfazEmail.recargarTabla();
		}
	}
}
