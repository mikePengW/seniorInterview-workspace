package com.chalon.principle.inversion.improve;

public class DependecyInversion {
	public static void main(String[] args) {
		Person person = new Person();
		person.receive(new Email());
		
		person.receive(new Weixin());
	}

}

interface IReceiver {
	String getInfo();
}

class Email implements IReceiver {
	public String getInfo() {
		return "电子邮件信息：hello,world";
	}
}

class Weixin implements IReceiver {
	public String getInfo() {
		return "微信信息：hello,ok";
	}
}

// 完成Person接收消息的功能
// 方式1完成
class Person {
	public void receive(IReceiver receiver) {
		System.out.println(receiver.getInfo());
	}
}
