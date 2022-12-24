package lab2.net;

import java.io.IOException;
import java.net.Socket;

import lab2.Goods;
import lab2.Server;

/**
 * For communication with producer
 * 
 * @author xparh
 */

public class RequestHandler extends Thread {

	public RequestHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (Service client = new Service(socket)) {
			while (true) {
				String name = (String) client.receive();
				Goods goods = (Goods) client.receive();

				System.out.println("Received " + name);

				server.put(name, goods);
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Connection ended");
		}
	}

	private final Server server;
	private final Socket socket;

}
