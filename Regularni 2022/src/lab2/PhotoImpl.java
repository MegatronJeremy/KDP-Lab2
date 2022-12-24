package lab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PhotoImpl implements Photo, Serializable {

	@Override
	public String getName() {
		return fileName;
	}

	@Override
	public void setName(String name) {
		this.fileName = name;
	}

	@Override
	public String[] getBody() {
		if (body == null)
			return null;

		return (String[]) body.toArray(new String[body.size()]);
	}

	@Override
	public void setBody(String[] body) {
		this.body = Arrays.asList(body);
	}

	@Override
	public String readLine() {
		if (body == null || body.isEmpty())
			return null;

		String ret = body.get(0);
		body.remove(0);

		return ret;
	}

	@Override
	public void printLine(String s) {
		if (body == null)
			return;

		body.add(s);
	}

	@Override
	public int getNumLines() {
		if (body == null)
			return -1;

		return body.size();
	}

	@Override
	public void save(String name) {
		File file = new File(name + ".txt");

		try (PrintWriter out = new PrintWriter(new FileOutputStream(file), true)) {
			for (String s : body) {
				out.println(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(String name) {
		File file = new File(name + ".txt");
		body = new LinkedList<>();

		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = in.readLine()) != null) {
				body.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;

	private String fileName = null;
	private List<String> body = new LinkedList<>();

}
