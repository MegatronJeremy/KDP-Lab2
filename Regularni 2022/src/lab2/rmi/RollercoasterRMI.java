package lab2.rmi;

import lab2.Photo;

public interface RollercoasterRMI {

	public boolean init(String host, int port);

	/**
	 * Regularan zavrsetak programa.
	 * 
	 * @return true ako je uspesno, inace false
	 */
	public boolean close();
	
	public void startRide(int rcNumber, int cntPass);

	public void endRide(int rcNumber, Photo photo);
	
}
