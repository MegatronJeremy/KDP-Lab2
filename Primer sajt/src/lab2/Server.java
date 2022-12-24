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
import lab2.net.Service;
import lab2.rmi.AtomicBroadcastRemote;
import lab2.rmi.AtomicBroadcastRemoteImpl;

public class Server {

	public Server(int port) {
		this.port = port;
	}

	@SuppressWarnings({ "removal", "deprecation", "unchecked" })
	private void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Registry r = LocateRegistry.createRegistry(port+1);

			AtomicBroadcastRemote<String, Goods> stub = (AtomicBroadcastRemote<String, Goods>) UnicastRemoteObject
					.exportObject(remoteMap, 0);

			r.rebind("/buffer", stub);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server running");
			while (true) {
				Socket socket = serverSocket.accept();

				new RequestHandler(this, socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void put(String name, Goods goods) {
		AtomicBroadcast<Goods> ab;

		if ((ab = goodsMap.putIfAbsent(name, new AtomicBroadcastMonitor<>())) == null) {
			ab = goodsMap.get(name);
		}

		ab.put(goods);
	}

	private final int port;

	private final Map<String, AtomicBroadcast<Goods>> goodsMap = new ConcurrentHashMap<String, AtomicBroadcast<Goods>>();

	private final AtomicBroadcastRemote<String, Goods> remoteMap = new AtomicBroadcastRemoteImpl<>(goodsMap);

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);

		Server server = new Server(port);
		server.run();
	}
}
