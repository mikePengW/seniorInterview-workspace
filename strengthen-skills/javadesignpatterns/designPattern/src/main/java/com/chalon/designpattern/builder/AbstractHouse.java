package com.chalon.designpattern.builder;

public abstract class AbstractHouse {

	public abstract void buildBasics();

	public abstract void buildWalls();

	public abstract void roofed();

	public void build() {
		buildBasics();
		buildWalls();
		roofed();
	}

}
