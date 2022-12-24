package lab2.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lab2.AB;
import lab2.Goods;

public class RmiAB implements AB {

	@SuppressWarnings({ "deprecation", "removal", "unchecked" })
	@Override
	public boolean init(String host, int port) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Registry r = LocateRegistry.getRegistry(host, port);
			stub = (AtomicBroadcastRemote<String, Goods>) r.lookup("/buffer");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			stub.put(name, goods);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Goods getGoods(String name) {
		Goods g = null;

		try {
			g = stub.get(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return g;
	}

	private AtomicBroadcastRemote<String, Goods> stub;

}
