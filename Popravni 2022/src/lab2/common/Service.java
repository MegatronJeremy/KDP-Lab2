package lab2.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Service implements AutoCloseable {

	public Service(Socket socket) throws IOException {
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public Object receiveMsg() throws ClassNotFoundException, IOException {
		return in.readObject();
	}

	public void sendMsg(Object obj) throws IOException {
		out.writeObject(obj);
	}

	@Override
	public void close() throws IOException {
		try (Socket s = socket; ObjectOutputStream o = out; ObjectInputStream i = in) {
		}
	}

	private final Socket socket;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;

}
