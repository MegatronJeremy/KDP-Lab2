package rs.ac.bg.etf.kdp.lab.rmi;

import rs.ac.bg.etf.kdp.lab.Consumer;
import rs.ac.bg.etf.kdp.lab.MessageBox;

public class TestC {

	public static void main(String[] args) {
		String host = "localhost";

		int port = Integer.parseInt(args[0]);

		MessageBox<Integer> buffer = new RmiMessageBox<>(host, port);

		Consumer c = new Consumer(buffer);
		c.start(); // c.run
	}

}
