package lab2;

public interface Rollercoaster {

	public boolean init(String host, int port);
	
	public boolean close();

	public Photo ride(int rcNumber);
}
