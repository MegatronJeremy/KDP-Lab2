package rs.ac.bg.etf.kdp.lab;

import java.io.Serializable;

public interface Status extends Serializable {
	void setStatus(boolean status);

	boolean getStatus();
}
