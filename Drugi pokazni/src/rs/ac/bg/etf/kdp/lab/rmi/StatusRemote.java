package rs.ac.bg.etf.kdp.lab.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatusRemote extends Remote {
	void setStatus(boolean status) throws RemoteException;

	boolean getStatus() throws RemoteException;

}
