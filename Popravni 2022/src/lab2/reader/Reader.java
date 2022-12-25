package lab2.reader;

import lab2.common.Book;
import lab2.common.RW;

/**
 * Example program arguments:
 * 
 * localhost 4002 AAAAAAAAAAA BBBBBBBBBBBB "Operativni Sistemi" KDP ...
 * 
 * **Note: port needs to be server port + 1
 * 
 * VM arguments:
 * 
 * -Djava.security.policy=java.policy
 */

public class Reader {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		RW rw = new RmiRW();

		if (!rw.init(host, port))
			return;

		for (int i = 2; i < args.length; i++) {
			String name = args[i];

			Book book = rw.read(name);

			int size = book.getNumLines();
			for (int j = 0; j < size; j++) {
				System.out.println(book.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}

			System.out.println("Finished reading " + name + "\n");

		}
		rw.close();
	}
}
