package rs.ac.bg.etf.kdp.lab.rmi;

import java.rmi.RemoteException;
import java.util.LinkedList;

public class MessageBoxRemoteImpl<T> implements MessageBoxRemote<T> {

	public MessageBoxRemoteImpl(int cap) {
		buffer = new LinkedList<>();
		this.capacity = cap;
	}

	@Override
	public synchronized void put(T msg, int priority, int timeToLiveMs) throws RemoteException {
		long start = System.currentTimeMillis();
		while (capacity == buffer.size()) {
			try {
				wait(timeToLiveMs);
			} catch (InterruptedException e) {
				long end = System.currentTimeMillis();
				if (timeToLiveMs > 0 && end - start >= timeToLiveMs)
					return;
			}
		}
		long end = System.currentTimeMillis();
		if (timeToLiveMs > 0 && end - start >= timeToLiveMs)
			return;

		buffer.add(msg); // bez vodjenja racuna o prioritetu
		notifyAll();
	}

	@Override
	public synchronized T get(int timeToWait, StatusRemote status) throws RemoteException {
		long start = System.currentTimeMillis();
		while (buffer.size() == 0) {
			try {
				wait(timeToWait);
			} catch (InterruptedException e) {
				long end = System.currentTimeMillis();
				if (timeToWait > 0 && end - start >= timeToWait) {
					status.setStatus(false);
					return null;
				}
			}
		}
		long end = System.currentTimeMillis();
		if (timeToWait > 0 && end - start >= timeToWait) {
			status.setStatus(false);
			return null;
		}

		notifyAll();

		T item = buffer.poll();
		status.setStatus(true);

		return item;
	}

	/**
	 * Preko semafora ili lockova? I ne trebamo...
	 * 
	 * Ova implementacija samo omotac - ali mogli smo na bilo koji drugaciji nacin
	 * mi implementirati.
	 */

	private LinkedList<T> buffer;
	private int capacity = 0;

}
