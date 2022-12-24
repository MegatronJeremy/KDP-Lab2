package lab2.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import lab2.Photo;

public interface RollercoasterRemote extends Remote {
	public void startRide(int rcNumber, int cntPass) throws RemoteException;

	public void endRide(int rcNumber, Photo photo) throws RemoteException;
}
