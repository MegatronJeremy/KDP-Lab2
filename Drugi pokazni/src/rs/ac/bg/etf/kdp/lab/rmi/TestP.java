package rs.ac.bg.etf.kdp.lab.rmi;

import rs.ac.bg.etf.kdp.lab.MessageBox;
import rs.ac.bg.etf.kdp.lab.Producer;

public class TestP {

	public static void main(String[] args) {
		// moze i da se fiksira - ili kroz args

		// moze i localhost
		String host = "localhost";

		int port = Integer.parseInt(args[0]);

		MessageBox<Integer> buffer = new RmiMessageBox<>(host, port);

		Producer p = new Producer(buffer);
		p.start();
	}

}
