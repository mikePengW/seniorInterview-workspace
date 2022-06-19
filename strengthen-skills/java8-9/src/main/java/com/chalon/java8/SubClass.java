package com.chalon.java8;

public class SubClass /*extends MyClass*/ implements MyFun1, MyInterface{

	@Override
	public String getName() {
		return MyInterface.super.getName();
	}

}
