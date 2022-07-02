package com.chalon.designpattern.facade;

public class Client {

	public static void main(String[] args) {
		HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade();
		homeTheaterFacade.ready();
		homeTheaterFacade.play();

		System.out.println();
		homeTheaterFacade.end();

	}

}
