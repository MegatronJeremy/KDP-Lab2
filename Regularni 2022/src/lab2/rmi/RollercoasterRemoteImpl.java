package lab2.rmi;

import java.rmi.RemoteException;
import java.util.Map;

import lab2.Photo;
import lab2.RollercoasterMonitor;

public class RollercoasterRemoteImpl implements RollercoasterRemote {

	public RollercoasterRemoteImpl(Map<Integer, RollercoasterMonitor> rcMap) {
		this.rcMap = rcMap;
	}

	@Override
	public void startRide(int rcNumber, int k) throws RemoteException {
		RollercoasterMonitor rc;

		if ((rc = rcMap.putIfAbsent(rcNumber, new RollercoasterMonitor())) == null) {
			rc = rcMap.get(rcNumber);
		}

		rc.startRide(k);
	}

	@Override
	public void endRide(int rcNumber, Photo photo) throws RemoteException {
		RollercoasterMonitor rc = new RollercoasterMonitor();

		if ((rc = rcMap.putIfAbsent(rcNumber, rc)) == null) {
			rc = rcMap.get(rcNumber);
		}

		rc.endRide(photo);
	}

	private final Map<Integer, RollercoasterMonitor> rcMap;

}
