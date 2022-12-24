package lab2.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lab2.Photo;

public class RollercoasterRMI_Impl implements RollercoasterRMI {

	@Override
	public boolean init(String host, int port) {
		try {
			Registry registry = LocateRegistry.getRegistry(host, port);

			rc = (RollercoasterRemote) registry.lookup("/rc");
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
	public void startRide(int rcNumber, int cntPass) {
		try {
			rc.startRide(rcNumber, cntPass);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void endRide(int rcNumber, Photo photo) {
		try {
			rc.endRide(rcNumber, photo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private RollercoasterRemote rc = null;

}
