package lab2.net;

import java.net.Socket;

import lab2.Photo;
import lab2.Server;

public class RequestHandler extends Thread {

	public RequestHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (Service client = new Service(socket)) {
			while (true) {
				int rcNumber = (int) client.receiveMsg();

				Photo photo = server.ride(rcNumber);

				client.sendMsg(photo);
			}
		} catch (Exception e) {
			System.out.println("Connection terminated");
		}
	}

	private final Server server;
	private final Socket socket;
}
