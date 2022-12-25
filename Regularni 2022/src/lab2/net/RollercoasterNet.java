package lab2.net;

import java.io.IOException;
import java.net.Socket;

import lab2.Photo;
import lab2.Rollercoaster;

public class RollercoasterNet implements Rollercoaster {

	@Override
	public boolean init(String host, int port) {
		try {
			service = new Service(new Socket(host, port));
		} catch (IOException e) {
			close();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean close() {
		try (Service s = service) {

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Photo ride(int rcNumber) {
		try {
			service.sendMsg(rcNumber);

			return (Photo) service.receiveMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Service service = null;
}
