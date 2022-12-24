package rs.ac.bg.etf.kdp.lab.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

	@SuppressWarnings({ "unchecked", "removal", "deprecation" })
	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		int port = 4002;
		try {
			Registry registry = LocateRegistry.createRegistry(port);

			MessageBoxRemote<Integer> mbx = new MessageBoxRemoteImpl<>(7);
			MessageBoxRemote<Integer> stub = (MessageBoxRemote<Integer>) UnicastRemoteObject.exportObject(mbx, 0);

			registry.rebind("/buffer", stub);
			System.out.println("RMI server working and contains the following objects...");

			for (String s : registry.list()) {
				System.out.println(s);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
