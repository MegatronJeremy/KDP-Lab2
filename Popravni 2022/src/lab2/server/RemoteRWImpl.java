package lab2.server;

import java.rmi.RemoteException;

import lab2.common.Book;
import lab2.common.RemoteRW;

public class RemoteRWImpl implements RemoteRW {

	public RemoteRWImpl(Server server) {
		this.server = server;
	}

	@Override
	public void write(String name, Book book) throws RemoteException {
		server.write(name, book);
	}

	@Override
	public Book read(String name) throws RemoteException {
		Book book = server.read(name);

		return book;
	}

	private final Server server;

}
