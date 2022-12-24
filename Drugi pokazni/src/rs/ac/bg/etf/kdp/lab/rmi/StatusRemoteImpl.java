package rs.ac.bg.etf.kdp.lab.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Uradili smo suppress warning jer je UnicastRemoteObject vec serializable
 */
@SuppressWarnings("serial")
public class StatusRemoteImpl extends UnicastRemoteObject implements StatusRemote {

	protected StatusRemoteImpl() throws RemoteException {
		super();
		status = false;
	}

	@Override
	public void setStatus(boolean status) throws RemoteException {
		this.status = status;
	}

	@Override
	public boolean getStatus() throws RemoteException {
		return status;
	}

	private boolean status;

}
