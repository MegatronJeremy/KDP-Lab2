package lab2.common;

public interface RW {
	/**
	 * Inicijalizacija.
	 * 
	 * @param host
	 * @param port
	 * @return true ako je uspesno, inace false
	 */
	public boolean init(String host, int port);

	/**
	 * Regularan zavrsetak programa.
	 * 
	 * @return true ako je uspesno, inace false
	 */
	public boolean close();

	public void write(String name, Book book);

	public Book read(String name);

}
