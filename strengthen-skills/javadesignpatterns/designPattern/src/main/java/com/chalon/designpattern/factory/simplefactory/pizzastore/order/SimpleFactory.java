package com.chalon.designpattern.factory.simplefactory.pizzastore.order;

import com.chalon.designpattern.factory.simplefactory.pizzastore.pizza.CheesePizza;
import com.chalon.designpattern.factory.simplefactory.pizzastore.pizza.GreekPizza;
import com.chalon.designpattern.factory.simplefactory.pizzastore.pizza.PepperPizza;
import com.chalon.designpattern.factory.simplefactory.pizzastore.pizza.Pizza;

public class SimpleFactory {

	public Pizza createPizza(String orderType) {

		Pizza pizza = null;

		System.out.println(" 使用简单工厂模式 ");
		if (orderType.equals("greek")) {
			pizza = new GreekPizza();
			pizza.setName(" 希腊披萨 ");
		} else if (orderType.equals("cheese")) {
			pizza = new CheesePizza();
			pizza.setName(" 奶酪披萨 ");
		} else if (orderType.equals("pepper")) {
			pizza = new PepperPizza();
			pizza.setName(" 胡椒披萨 ");
		} else if (orderType.equals("china")) {
			pizza = new PepperPizza();
			pizza.setName(" 中国披萨 ");
		}

		return pizza;
	}

	public static Pizza createPizza2(String orderType) {

		Pizza pizza = null;

		System.out.println(" 使用简单工厂模式2 ");
		if (orderType.equals("greek")) {
			pizza = new GreekPizza();
			pizza.setName(" 希腊披萨 ");
		} else if (orderType.equals("cheese")) {
			pizza = new CheesePizza();
			pizza.setName(" 奶酪披萨 ");
		} else if (orderType.equals("pepper")) {
			pizza = new PepperPizza();
			pizza.setName(" 胡椒披萨 ");
		} else if (orderType.equals("china")) {
			pizza = new PepperPizza();
			pizza.setName(" 中国披萨 ");
		}

		return pizza;
	}

}
