package lab2.writer;

import lab2.common.Book;
import lab2.common.BookImpl;
import lab2.common.RW;

/**
 * Example program arguments:
 * 
 * localhost 4001 AAAAAAAAAAA BBBBBBBBBBBB "Operativni Sistemi" KDP ...
 * 
 * VM arguments:
 * 
 * -Djava.security.policy=java.policy
 */

public class Writer {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		RW rw = new NetRW();

		if (!rw.init(host, port))
			return;

		for (int i = 2; i < args.length; i++) {
			String name = args[i];

			Book book = new BookImpl();

			int size = (int) (Math.random() * 32);
			for (int j = 0; j < size; j++) {
				String data = "" + (Math.random() * 1234567) + "\n";
				book.printLine(data);
				System.out.println(data);
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}

			rw.write(name, book);

			System.out.println("Finished writing " + name + "\n");
		}
		rw.close();
	}
}
