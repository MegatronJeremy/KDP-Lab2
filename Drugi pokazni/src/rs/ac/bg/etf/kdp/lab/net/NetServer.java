package rs.ac.bg.etf.kdp.lab.net;

import java.net.ServerSocket;
import java.net.Socket;

import rs.ac.bg.etf.kdp.lab.MessageBox;
import rs.ac.bg.etf.kdp.lab.MonitorMessageBox;

public class NetServer {

	/**
	 * Za jednostavan server sve stavimo u main Pravimo varijantu da pravimo nit po
	 * nit Rizikujemo da nam neko posalje milion zahteva - unbounded buffer
	 */

	public static void main(String[] args) {
		int port = 4001;
		
		/**
		 * Integer - jeste serializable, i moze da se salje kroz tokove podataka
		 */
		MessageBox<Integer> buffer = new MonitorMessageBox<>(10);

		/**
		 * Potreban serverSocket - on jedini slusa na ovom portu i uspostavlja TCP komunikaciju
		 */
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket client = serverSocket.accept();
				
				/**
				 * Bafer za working thread?
				 * SVI treba da rade sa istim baferom
				 * Ovde treba da napravimo neki nas message box
				 */
				new WorkingThread(client, buffer).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
