package com.chalon.designpattern.builder;

public class CommonHouse extends AbstractHouse {

	@Override
	public void buildBasics() {
		System.out.println(" 普通房子打地基 ");
	}

	@Override
	public void buildWalls() {
		System.out.println(" 普通房子砌墙 ");
	}

	@Override
	public void roofed() {
		System.out.println(" 普通房子封顶打地基 ");
	}

}
