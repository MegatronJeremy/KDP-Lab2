package lab2.net;

import java.io.IOException;
import java.net.Socket;

import lab2.AB;
import lab2.Goods;

public class NetAB implements AB {

	public boolean init(String host, int port) {
		try {
			Socket socket = new Socket(host, port);
			service = new Service(socket);
		} catch (IOException e) {
			close();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean close() {
		try {
			service.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			service.send(name);
			service.send(goods);

			service.receive(); // "ACK"
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Goods getGoods(String name) {
		try {
			service.send(name);

			Goods goods = (Goods) service.receive();
			return goods;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Service service;

}
