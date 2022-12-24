package lab2.net;

import java.io.IOException;
import java.net.Socket;

import lab2.AB;
import lab2.Goods;

public class NetAB implements AB {

	@SuppressWarnings({ "removal", "deprecation" })
	@Override
	public boolean init(String host, int port) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Socket socket = new Socket(host, port);
			service = new Service(socket);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean close() {
		try {
			service.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			service.send(name);
			service.send(goods);

		} catch (IOException e) {
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
