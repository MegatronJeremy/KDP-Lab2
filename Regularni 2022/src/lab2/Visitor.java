package lab2;

import lab2.net.RollercoasterNet;

/**
 * Example program arguments:
 * 
 * localhost 4001 1
 * 
 * VM arguments: 
 * 
 * -Djava.security.policy=java.policy
 */

public class Visitor {
	public static void main(String[] args) throws InterruptedException {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		int rcNumber = Integer.parseInt(args[2]);

		Rollercoaster rc = new RollercoasterNet();

		if (!rc.init(host, port))
			return;

		int rides = (int) (Math.random() * 4) + 1;

		for (int i = 0; i < rides; i++) {
			Photo photo = rc.ride(rcNumber);

			String name = photo.getName();
			int size = photo.getNumLines();

			for (int j = 0; j < size; j++) {
				System.out.println(photo.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}

			System.out.println("Finished reading " + name);
			System.out.println();
		}

	}
}
