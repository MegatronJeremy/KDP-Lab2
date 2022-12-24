package rs.ac.bg.etf.kdp.lab.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rs.ac.bg.etf.kdp.lab.Status;

public interface MessageBoxRemote<T> extends Remote {
	
	void put(T msg, int priority, int timeToLiveMs) throws RemoteException;

	T get(int timeToWait, StatusRemote status) throws RemoteException;
	
}
