package com.chalon.designpattern.factory.simplefactory.pizzastore.pizza;

public class ChinaPizza extends Pizza {

	@Override
	public void prepare() {
		System.out.println(" 给中国披萨 准备原材料");

	}

}
