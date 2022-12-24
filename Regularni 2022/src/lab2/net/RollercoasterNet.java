package lab2.net;

import java.io.IOException;
import java.net.Socket;

import lab2.Photo;
import lab2.Rollercoaster;

public class RollercoasterNet implements Rollercoaster {

	@Override
	public boolean init(String host, int port) {
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Photo ride(int rcNumber) {
		try (Service server = new Service(socket)) {
			server.sendMsg(rcNumber);
			
			return (Photo) server.receiveMsg(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Socket socket = null;


}
