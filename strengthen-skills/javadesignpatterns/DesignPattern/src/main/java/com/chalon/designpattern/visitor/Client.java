package com.chalon.designpattern.visitor;

public class Client {

	public static void main(String[] args) {
		ObjectStructure objectStructure = new ObjectStructure();
		objectStructure.attach(new Man());
		objectStructure.attach(new Woman());

		// 成功
		Success success = new Success();
		objectStructure.display(success);

		// 失败
		System.out.println("==================");
		Fail fail = new Fail();
		objectStructure.display(fail);

		// 待定
		System.out.println("=========给的是待定的测评=========");
		Wait wait = new Wait();
		objectStructure.display(wait);

	}

}
