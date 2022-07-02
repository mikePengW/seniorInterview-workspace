package com.chalon.designpattern.adapter.interfaceadapter;

public class Client {

	public static void main(String[] args) {
		System.out.println(" === 接口适配器模式 === ");
		AbsAdapter absAdapter = new AbsAdapter() {
			@Override
			public void m1() {
				System.out.println("使用了m1的方法");
			}
		};
		absAdapter.m1();

	}

}
