package lab2.reader;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lab2.common.Book;
import lab2.common.RW;
import lab2.common.RemoteRW;

public class RmiRW implements RW {

	@SuppressWarnings({ "deprecation", "removal" })
	@Override
	public boolean init(String host, int port) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Registry registry = LocateRegistry.getRegistry(host, port);
			stub = (RemoteRW) registry.lookup("/rw");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public void write(String name, Book book) {
		try {
			stub.write(name, book);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Book read(String name) {
		try {
			return stub.read(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	private RemoteRW stub;

}
