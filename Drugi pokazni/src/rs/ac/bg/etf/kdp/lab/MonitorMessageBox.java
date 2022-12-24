package rs.ac.bg.etf.kdp.lab;

import java.util.LinkedList;

public class MonitorMessageBox<T> implements MessageBox<T> {

	public MonitorMessageBox(int size) {
		super();
		this.capacity = size;
		buffer = new LinkedList<>();
	}

	@Override
	public synchronized void put(T msg, int priority, int timeToLiveMs) {
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
		// trebalo bi voditi racuna i na kraju ubacivanja u bafer...

		buffer.add(msg); // bez vodjenja racuna o prioritetu
		notifyAll();
	}

	@Override
	public synchronized T get(int timeToWait, Status status) {
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

	private LinkedList<T> buffer;
	private int capacity = 0;
	// size --> buffer.size()

}
