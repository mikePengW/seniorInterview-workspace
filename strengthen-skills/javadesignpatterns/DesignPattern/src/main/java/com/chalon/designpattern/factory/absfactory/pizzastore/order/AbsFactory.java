package com.chalon.designpattern.factory.absfactory.pizzastore.order;

import com.chalon.designpattern.factory.absfactory.pizzastore.pizza.Pizza;

public interface AbsFactory {

	Pizza createPizza(String orderType);

}
