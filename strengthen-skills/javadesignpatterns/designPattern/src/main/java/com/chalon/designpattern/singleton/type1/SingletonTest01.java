package com.chalon.designpattern.singleton.type1;

public class SingletonTest01 {
	public static void main(String[] args) {
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2);
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());

	}

}

class Singleton {
	private Singleton() {

	}

	private final static Singleton instance = new Singleton();

	public static Singleton getInstance() {
		return instance;
	}

}
