package com.chalon.source;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class Decorator {

	public static void main(String[] args) throws Exception {
		DataInputStream dis = new DataInputStream(new FileInputStream("d:\\abc.txt"));
		System.out.println(dis.read());
		dis.close();
	}

}
