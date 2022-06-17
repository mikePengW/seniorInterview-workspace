package com.chalon.designpattern.factory.absfactory.pizzastore.order;

public class PizzaStore {
	public static void main(String[] args) {
		String loc = "bj";
		if (loc.equals("bj")) {
			new OrderPizza(new BJFactory());
		} else {
			new OrderPizza(new LDFactory());
		}
	}

}
