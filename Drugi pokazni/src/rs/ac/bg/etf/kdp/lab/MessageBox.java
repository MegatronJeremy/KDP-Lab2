package rs.ac.bg.etf.kdp.lab;

/**
 * Potencijalno moze kao varijanta - stavimo extends Remote, ali ako zelimo 
 * da ostane isto, napravimo nas novi interfejs.
 */
public interface MessageBox<T> {

	/**
	 * Insert message to buffer, blocking if the buffer is full.
	 * 
	 * @param msg          message to be put in buffer
	 * @param priority     priority of the message; lower number is higher priority
	 * @param timeToLiveMs number of milliseconds for which the message can be read;
	 *                     if more time passed, message is no longer valid; 0 means
	 *                     lives forever
	 */
	void put(T msg, int priority, int timeToLiveMs);
	
	T get(int timeToWait, Status status);
}
