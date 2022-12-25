package lab2.server;

import java.io.IOException;
import java.net.Socket;

import lab2.common.Book;
import lab2.common.Service;

public class RequestHandler extends Thread {

	public RequestHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (Service client = new Service(socket)) {
			service = client;
			while (true) {
				String operation = (String) service.receiveMsg();

				if ("read".equals(operation)) {
					read();
				} else if ("write".equals(operation)) {
					write();
				} else {
					String msg = String.format("*** Operacija %s nije podrzana.", operation);
					throw new UnsupportedOperationException(msg);
				}
			}
		} catch (Exception e) {
			System.out.println("Connection terminated.");
		}

	}

	private void read() throws IOException, ClassNotFoundException {
		String name = (String) service.receiveMsg();

		Book book = server.read(name);

		service.sendMsg(book);
	}

	private void write() throws IOException, ClassNotFoundException {
		String name = (String) service.receiveMsg();

		Book book = (Book) service.receiveMsg();

		server.write(name, book);

		service.sendMsg("ack");
	}

	private Service service = null;

	private final Server server;
	private final Socket socket;

}
