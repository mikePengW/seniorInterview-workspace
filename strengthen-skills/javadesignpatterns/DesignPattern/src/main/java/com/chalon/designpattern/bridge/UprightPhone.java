package com.chalon.designpattern.bridge;

public class UprightPhone extends Phone {

	public UprightPhone(Brand brand) {
		super(brand);
	}

	public void open() {
		super.open();
		System.out.println(" 直立式手机 ");
	}

	public void close() {
		super.close();
		System.out.println(" 直立式手机 ");
	}

	public void call() {
		super.call();
		System.out.println(" 直立式手机 ");
	}

}
