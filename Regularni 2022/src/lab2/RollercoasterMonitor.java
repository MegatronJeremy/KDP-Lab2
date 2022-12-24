package lab2;

public class RollercoasterMonitor {

	public synchronized void startRide(int k) {
		this.k = k;

		enter = true;

		notifyAll();

		while (cnt < k) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public synchronized void endRide(Photo photo) {
		photoFile = photo.getName();
		photo.save(photoFile);

		exit = true;

		notifyAll();

		while (cnt > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public synchronized Photo ride() {
		while (enter == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		cnt++;

		if (cnt == k) {
			enter = false;
			notifyAll();
		}

		// ride()

		while (exit == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		cnt--;

		Photo photo = new PhotoImpl();
		photo.setName(photoFile);
		photo.load(photoFile);

		if (cnt == 0) {
			exit = false;
			notifyAll();
		}

		return photo;
	}

	private String photoFile = null;
	private boolean enter = false;
	private boolean exit = false;
	private int cnt = 0;
	private int k = 0;

}
