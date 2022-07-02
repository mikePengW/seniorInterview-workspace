package com.chalon.source.prototype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Prototype {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		Object bean = applicationContext.getBean("id01");
		System.out.println("bean " + bean);

		Object bean2 = applicationContext.getBean("id01");
		System.out.println("bean2 " + bean2);

		System.out.println(bean == bean2);

	}

}
