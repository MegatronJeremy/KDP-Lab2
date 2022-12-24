package rs.ac.bg.etf.kdp.lab.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rs.ac.bg.etf.kdp.lab.MessageBox;
import rs.ac.bg.etf.kdp.lab.Status;

public class RmiMessageBox<T> implements MessageBox<T> {

	@SuppressWarnings({ "unchecked", "removal", "deprecation" })
	public RmiMessageBox(String host, int port) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Registry r = LocateRegistry.getRegistry(host, port);
			stub = (MessageBoxRemote<T>) r.lookup("/buffer");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void put(T msg, int priority, int timeToLiveMs) {
		try {
			stub.put(msg, priority, timeToLiveMs);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public T get(int timeToWait, Status status) {
		try {
			StatusRemote statusRemote = new StatusRemoteImpl();

			statusRemote.setStatus(status.getStatus());

			T item = stub.get(timeToWait, statusRemote);

			status.setStatus(statusRemote.getStatus());

			return item;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	private MessageBoxRemote<T> stub;

}
