package lab2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lab2.net.RequestHandler;
import lab2.rmi.RollercoasterRemoteImpl;

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
	}

	public void bindToRegistry() {
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(port + 1);
			registry.rebind("/rc", UnicastRemoteObject.exportObject(rcRemote, 0));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void doWork() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server running...");
			while (true) {
				Socket client = serverSocket.accept();

				new RequestHandler(this, client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Photo ride(int rcNumber) {
		RollercoasterMonitor rc;

		if ((rc = rcMap.putIfAbsent(rcNumber, new RollercoasterMonitor())) == null) {
			rc = rcMap.get(rcNumber);
		}

		return rc.ride();
	}

	private final Map<Integer, RollercoasterMonitor> rcMap = new ConcurrentHashMap<>();

	private final RollercoasterRemoteImpl rcRemote = new RollercoasterRemoteImpl(rcMap);

	private final int port;

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);

		Server server = new Server(port);

		server.bindToRegistry();

		server.doWork();
	}

}
