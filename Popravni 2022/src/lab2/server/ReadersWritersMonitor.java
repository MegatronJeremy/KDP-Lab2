package lab2.server;

public class ReadersWritersMonitor implements ReadersWriters {

	/**
	 * Writers preferred
	 */
	@Override
	public synchronized void startRead() {
		while (cntW > 0 && dW > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		cntR++;
	}

	@Override
	public synchronized void endRead() {
		cntR--;

		if (cntR == 0) {
			notifyAll();
		}
	}

	@Override
	public synchronized void startWrite() {
		dW++;
		while (cntW > 0 && cntR > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		dW--;
		cntW++;
	}

	@Override
	public synchronized void endWrite() {
		cntW--;
		notifyAll();
	}

	private int cntW = 0, cntR = 0, dW = 0;

}
