package lab2;

import java.util.ArrayList;
import java.util.List;

public class AtomicBroadcastMonitor<T> implements AtomicBroadcast<T> {
	@Override
	public synchronized void put(T data) {
		buffer.add(data);

		wi++;

		notifyAll();
	}

	@Override
	public synchronized T get() {
		int ri = riL.get();

		while (ri == wi) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		T data = buffer.get(ri);

		riL.set(ri + 1);

		return data;
	}

	private List<T> buffer = new ArrayList<>();

	private int wi = 0;
	private ThreadLocal<Integer> riL = ThreadLocal.withInitial(() -> 0);
}
