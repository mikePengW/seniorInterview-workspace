package com.chalon.designpattern.bridge;

public class Client {
	public static void main(String[] args) {
		Phone phone1 = new FoldedPhone(new XiaoMi());
		phone1.open();
		phone1.call();
		phone1.close();

		System.out.println("=========================");
		Phone phone2 = new FoldedPhone(new Vivo());
		phone2.open();
		phone2.call();
		phone2.close();
		System.out.println();
		Phone phone3 = new UprightPhone(new XiaoMi());
		phone3.open();
		phone3.call();
		phone3.close();

		System.out.println("=========================");
		Phone phone4 = new UprightPhone(new Vivo());
		phone4.open();
		phone4.call();
		phone4.close();

	}

}
