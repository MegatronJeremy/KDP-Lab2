package lab2.writer;

import java.io.IOException;
import java.net.Socket;

import lab2.common.Book;
import lab2.common.RW;
import lab2.common.Service;

public class NetRW implements RW {

	public boolean init(String host, int port) {
		try {
			Socket socket = new Socket(host, port);
			service = new Service(socket);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean close() {
		try {
			service.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public void write(String name, Book book) {
		try {
			service.sendMsg("write");

			service.sendMsg(name);

			service.sendMsg(book);

			service.receiveMsg(); // "ACK"
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Book read(String name) {
		try {
			service.sendMsg("read");

			service.sendMsg(name);

			return (Book) service.receiveMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Service service = null;
}
