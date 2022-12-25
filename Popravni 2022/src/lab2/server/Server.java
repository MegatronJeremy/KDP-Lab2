package lab2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lab2.common.Book;
import lab2.common.BookImpl;

/**
 * Example program arguments:
 * 
 * 4001
 * 
 * VM arguments:
 * 
 * -Djava.security.policy=java.policy
 */

public class Server {

	public Server(int port) {
		this.port = port;
		bindToRegister();
	}

	@SuppressWarnings({ "removal", "deprecation" })
	public void bindToRegister() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Registry registry = LocateRegistry.createRegistry(port + 1);
			registry.rebind("/rw", UnicastRemoteObject.exportObject(remoteRW, 0));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void doWork() {
		System.out.println("Server running...");

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket client = serverSocket.accept();
				System.out.println("Accepted " + client.getInetAddress());

				new RequestHandler(this, client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String name, Book book) {
		ReadersWriters rw;

		if ((rw = rwMap.putIfAbsent(name, new ReadersWritersMonitor())) == null) {
			rw = rwMap.get(name);
		}

		rw.startWrite();

		book.save(name);

		rw.endWrite();
	}

	public Book read(String name) {
		ReadersWriters rw;

		if ((rw = rwMap.putIfAbsent(name, new ReadersWritersMonitor())) == null) {
			rw = rwMap.get(name);
		}

		Book book = new BookImpl();
		book.setName(name);

		rw.startRead();

		book.load(name);

		rw.endRead();

		return book;
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);

		Server server = new Server(port);

		server.doWork();
	}

	private final int port;

	private final Map<String, ReadersWriters> rwMap = new ConcurrentHashMap<>();

	private final RemoteRWImpl remoteRW = new RemoteRWImpl(this);
}
