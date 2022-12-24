package rs.ac.bg.etf.kdp.lab.net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import rs.ac.bg.etf.kdp.lab.MessageBox;
import rs.ac.bg.etf.kdp.lab.Status;

/**
 * Otvara konekciju ka serveru - koristi onaj MonitorMessageBox Taj server ima
 * konkretnu implementaciju (i objekat) Ovo je samo interfejs za consumera -
 * poziva metode preko socketa Klijent zatvori komunikaciju? Tek tada kazemo
 * kraj je Mi radimo kratkozivecu - kako bismo iskoristili try sa resursima
 * 
 * RMI - Server ima monitor message box koji je udaljena referenca
 * 
 * @author xparh
 *
 * @param <T>
 */

public class NetMessageBox<T> implements MessageBox<T> {
	/**
	 * interfejs ne baca izuzetke - pa ovo ne baca izuzetke;
	 * 
	 * Vise ima smisla da svako ima svoj NetMessageBox - ne da je deljeni resurs
	 * medju klijentima. Mozemo i staviti da je ThreadLocal promenljiva socket... pa
	 * ThreadLocal.set.
	 * 
	 * Veci problem - dugoziveca komunikacija - i pise se u ISTI tok podataka ako je
	 * isto klijentsko sanduce - onda ili metode synchronized ili svako svoje
	 * sanduce za sebe (i svoj tok komunikacije)
	 */

	public NetMessageBox(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	@Override
	public void put(T msg, int priority, int timeToLiveMs) {
		try (Socket client = new Socket(host, port);
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {

			/**
			 * Mogu i samo dva odvojena servera - mozda ne treba aktivni monitor (koji ne
			 * zna od koga je primio zahtev) koji dalje komuniciraju u pozadini.
			 * 
			 * Accept je blokirajuci, dve serverske niti i dva socketa - inace se blokiraju.
			 * Imamo WorkingThreadP i WorkingThreadC - dva odvojena postanska sanducica
			 * (ulaza). Prosledjujemo zajednicki bafer - imamo samo vise niti i vise ulaza.
			 * 
			 * Mi pravimo kao aktivni monitor - jedan working thread obradjuje sve zahteve.
			 * Da ne radimo tako - vodimo racuna da je accept blokirajuci (ne mozemo nikako
			 * izaci iz while true).
			 */

			out.writeUTF("put");

			out.writeObject(msg);
			
			out.writeObject(priority);
			out.writeObject(timeToLiveMs);
			
//			out.writeInt(priority);
//			out.writeInt(timeToLiveMs);
			/**
			 * Problem - slanje prostih tipova se baferuje!
			 */
//			out.flush();

			in.readObject(); // "ACK" ili sta god

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int timeToWait, Status status) {
		try (Socket client = new Socket(host, port);
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {

			out.writeUTF("get");

			out.writeObject(timeToWait);
			out.writeObject(status);

//			out.writeInt(timeToWait);
//			out.flush();

			T item = (T) in.readObject();
			Status returnedStatus = (Status) in.readObject();
			status.setStatus(returnedStatus.getStatus());

			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// vracamo null ako se desi bilo koja greska
		return null;
	}

//	private Socket socket; // bolje u try sa resursima da otvaramo
	private String host;
	private int port;

}
