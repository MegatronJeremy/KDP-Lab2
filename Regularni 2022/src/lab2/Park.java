package lab2;

import lab2.rmi.RollercoasterRMI;
import lab2.rmi.RollercoasterRMI_Impl;


/**
 * Example program arguments:
 * 
 * localhost 4002 1 3
 * 
 * **Note: port needs to be server port + 1
 * 
 * VM arguments: 
 * 
 * -Djava.security.policy=java.policy
 */

public class Park {

	public static void main(String[] args) {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		int rcNumber = Integer.parseInt(args[2]);
		int cntPass = Integer.parseInt(args[3]);

		RollercoasterRMI rc = new RollercoasterRMI_Impl();

		String name = "Rollercoaster " + rcNumber + " ride ";

		if (!rc.init(host, port))
			return;

		int rides = (int) (Math.random() * 32);

		for (int i = 0; i < rides; i++) {
			rc.startRide(rcNumber, cntPass);

			Photo photo = new PhotoImpl();

			int size = (int) (Math.random() * 32) + 1;
			for (int j = 0; j < size; j++) {
				String data = "" + (Math.random() * 1234567) + "\n";
				photo.printLine(data);
				System.out.println(data);
				try {
					Thread.sleep(1000 + (int) (Math.random() * 734));
				} catch (InterruptedException e) {
				}
			}

			photo.setName(name + i);

			rc.endRide(rcNumber, photo);

			System.out.println(name + i + " finished.");
			System.out.println();
		}
		rc.close();
	}

}
