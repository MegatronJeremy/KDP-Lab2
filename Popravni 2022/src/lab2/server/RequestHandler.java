package lab2.server;

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
		try (Service service = new Service(socket)) {
			while (true) {
				String operation = (String) service.receiveMsg();
				if ("read".equals(operation)) {
					String name = (String) service.receiveMsg();

					Book book = server.read(name);

					service.sendMsg(book);

				} else if ("write".equals(operation)) {
					String name = (String) service.receiveMsg();

					Book book = (Book) service.receiveMsg();

					server.write(name, book);

					service.sendMsg("ack");

				} else {
					String msg = String.format("*** Operacija %s nije podrzana.", operation);
					throw new UnsupportedOperationException(msg);
				}
			}
		} catch (Exception e) {
			System.out.println("Connection terminated.");
		}

	}

	private final Server server;
	private final Socket socket;

}
