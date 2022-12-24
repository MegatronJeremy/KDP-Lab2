package lab2;

import lab2.rmi.RmiAB;

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

public class Consumer {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		AB ab = new RmiAB(); // TODO zameniti null stvaranjem konkretnog objekta, npr. new PCClass(...)

		if (!ab.init(host, port))
			return;

		for (int i = 2; i < args.length; i++) {
			String name = args[i];

			Goods goods = ab.getGoods(name);

			int size = goods.getNumLines();
			for (int j = 0; j < size; j++) {
				System.out.println(goods.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}

			System.out.println("Finished reading " + name);
			System.out.println();

		}
		ab.close();
	}
}
