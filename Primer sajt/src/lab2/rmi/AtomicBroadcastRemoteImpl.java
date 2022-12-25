package lab2.rmi;

import java.rmi.RemoteException;
import java.util.Map;

import lab2.AtomicBroadcast;
import lab2.AtomicBroadcastMonitor;

public class AtomicBroadcastRemoteImpl<T, E> implements AtomicBroadcastRemote<T, E> {

	public AtomicBroadcastRemoteImpl(Map<T, AtomicBroadcast<E>> goodsMap) {
		this.goodsMap = goodsMap;
	}

	@Override
	public void put(T key, E val) throws RemoteException {
		AtomicBroadcast<E> ab;

		if ((ab = goodsMap.putIfAbsent(key, new AtomicBroadcastMonitor<>())) == null) {
			ab = goodsMap.get(key);
		}
		ab.put(val);
	}

	@Override
	public E get(T key) throws RemoteException {
		AtomicBroadcast<E> ab = new AtomicBroadcastMonitor<>();

		if ((goodsMap.putIfAbsent(key, ab)) != null) {
			ab = goodsMap.get(key);
		}

		return ab.get();
	}

	private final Map<T, AtomicBroadcast<E>> goodsMap;

}
