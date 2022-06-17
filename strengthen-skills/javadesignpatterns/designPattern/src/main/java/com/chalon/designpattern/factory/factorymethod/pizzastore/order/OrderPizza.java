package com.chalon.designpattern.factory.factorymethod.pizzastore.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.chalon.designpattern.factory.factorymethod.pizzastore.pizza.Pizza;

public abstract class OrderPizza {

	abstract Pizza createPizza(String orderType);

	public OrderPizza() {
		Pizza pizza = null;
		String orderType;
		do {
			orderType = getType();
			pizza = createPizza(orderType);
			if (pizza != null) {
				pizza.prepare();
				pizza.bake();
				pizza.cut();
				pizza.box();
			} else {
				System.out.println("订购失败");
				break;
			}
		} while (true);
	}

	private String getType() {
		try {
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("input pizza 种类：");
			String str = strin.readLine();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}
