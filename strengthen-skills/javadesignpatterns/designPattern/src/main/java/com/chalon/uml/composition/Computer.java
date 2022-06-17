package com.chalon.uml.composition;

public class Computer {
	private Mouse mouse = new Mouse(); // 不可以分离
	private Monitor monitor = new Monitor(); // 不可以分离

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

}
