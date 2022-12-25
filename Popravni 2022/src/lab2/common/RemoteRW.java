package lab2.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRW extends Remote {
	public void write(String name, Book book) throws RemoteException;

	public Book read(String name) throws RemoteException;
}
