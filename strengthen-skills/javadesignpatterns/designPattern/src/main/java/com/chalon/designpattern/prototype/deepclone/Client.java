package com.chalon.designpattern.prototype.deepclone;

public class Client {
	public static void main(String[] args) throws Exception {
		DeepPrototype p = new DeepPrototype();
		p.name = "宋江";
		p.deepCloneableTarget = new DeepCloneableTarget("大牛", "小牛");

		// 方式一
//		DeepPrototype p2 = (DeepPrototype) p.clone();
//		System.out.println("p.name = " + p.name + "p.deepCloneableTarget = " + p.deepCloneableTarget.hashCode());
//		System.out.println("p2.name = " + p2.name + "p2.deepCloneableTarget = " + p2.deepCloneableTarget.hashCode());

		// 方式二
		DeepPrototype p2 = (DeepPrototype) p.deepClone();
		System.out.println("p.name = " + p.name + "p.deepCloneableTarget = " + p.deepCloneableTarget.hashCode());
		System.out.println("p2.name = " + p2.name + "p2.deepCloneableTarget = " + p2.deepCloneableTarget.hashCode());

	}

}
