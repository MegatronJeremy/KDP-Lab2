package lab2.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface AtomicBroadcastRemote<T, E> extends Remote {

	void put(T key, E val) throws RemoteException;

	E get(T key) throws RemoteException;

}
