package lab2;

public interface AtomicBroadcast<T> {

	void put(T data);

	T get();

}
