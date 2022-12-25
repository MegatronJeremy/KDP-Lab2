package lab2.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookImpl implements Book, Serializable {

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String[] getBody() {
		if (body.isEmpty())
			return null;

		return (String[]) body.toArray(new String[body.size()]);
	}

	@Override
	public void setBody(String[] body) {
		this.body = Arrays.asList(body);
		cursor = 0;
	}

	@Override
	public String readLine() {
		if (cursor >= body.size())
			return null;

		return body.get(cursor++);
	}

	@Override
	public void printLine(String s) {
		body.add(s);
	}

	@Override
	public int getNumLines() {
		if (body.isEmpty())
			return -1;

		return body.size();
	}

	@Override
	public void save(String name) {
		File file = new File(name + ".txt");
		try (PrintWriter out = new PrintWriter(new FileOutputStream(file))) {
			for (String s : body) {
				out.println(s);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(String name) {
		File file = new File(name + ".txt");
		body = new ArrayList<>();
		cursor = 0;

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

	private String name = null;

	private List<String> body = new ArrayList<>();
	private int cursor = 0;

}
