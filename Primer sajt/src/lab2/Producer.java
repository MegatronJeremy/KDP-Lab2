package lab2;

import lab2.net.NetAB;

/**
 * Example program arguments:
 * 
 * localhost 4001 AAAAAAAAAAA BBBBBBBBBBBB "Operativni Sistemi" KDP ...
 * 
 * VM arguments: 
 * 
 * -Djava.security.policy=java.policy
 */



public class Producer {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		AB ab = new NetAB(); 

		if (!ab.init(host, port))
			return;

		for (int i = 2; i < args.length; i++) {
			String name = args[i];

			Goods goods = new GoodsImpl(); 

			int size = (int) (Math.random() * 32);
			for (int j = 0; j < size; j++) {
				String data = "" + (Math.random() * 1234567) + "\n";
				goods.printLine(data);
				System.out.println(data);
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}

			goods.save(name);
			
			ab.putGoods(name, goods);

			System.out.println("Finished sending " + name);
			System.out.println();
		}
		ab.close();
	}
}
