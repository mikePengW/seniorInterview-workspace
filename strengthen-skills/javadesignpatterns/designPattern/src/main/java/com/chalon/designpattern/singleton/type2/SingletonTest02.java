package com.chalon.designpattern.singleton.type2;

public class SingletonTest02 {
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

	private final static Singleton instance;

	static {
		instance = new Singleton();
	}

	public static Singleton getInstance() {
		return instance;
	}

}
