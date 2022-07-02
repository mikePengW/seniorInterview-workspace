package com.chalon.uml.aggregation;

public class Computer {
	private Mouse mouse; // 可以分离
	private Monitor monitor; // 可以分离

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

}
