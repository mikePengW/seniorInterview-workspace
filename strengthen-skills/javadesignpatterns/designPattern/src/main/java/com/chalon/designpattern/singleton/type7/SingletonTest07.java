package com.chalon.designpattern.singleton.type7;

public class SingletonTest07 {
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

	private static class SingletonInstance {
		private static final Singleton INSTANCE = new Singleton();
	}

	public static Singleton getInstance() {
		return SingletonInstance.INSTANCE;
	}

}
