package com.chalon.designpattern.facade;

public class Screen {

	private static Screen instance = new Screen();

	public static Screen getInstance() {
		return instance;
	}

	public void up() {
		System.out.println(" screen up ");
	}

	public void down() {
		System.out.println(" screen down ");
	}

}
