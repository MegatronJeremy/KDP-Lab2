package rs.ac.bg.etf.kdp.lab.net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import rs.ac.bg.etf.kdp.lab.BooleanStatus;
import rs.ac.bg.etf.kdp.lab.MessageBox;
import rs.ac.bg.etf.kdp.lab.Status;

/**
 * Request handler
 */
public class WorkingThread extends Thread {

	public WorkingThread(Socket client, MessageBox<Integer> buffer) {
		this.client = client;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		try (Socket s = client;
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {

			String operation = in.readUTF();

			if (operation.equalsIgnoreCase("put")) {
				Integer item = (Integer) in.readObject();

				int priority = (int) in.readObject();
				int ttl = (int) in.readObject();
				
				buffer.put(item, priority, ttl);

				out.writeObject("ACK");

			} else if ("get".equalsIgnoreCase(operation)) {
				int ttw = (int) in.readObject();
				Status status = (Status) in.readObject();

				Integer item = buffer.get(ttw, status);

				out.writeObject(item);
				out.writeObject(status);
			} else {
				System.err.println(String.format("*** Operacija %s nije podrzana.", operation));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Socket client;
	MessageBox<Integer> buffer;

}
