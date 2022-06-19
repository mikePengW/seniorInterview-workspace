package com.chalon.designpattern.composite;

public class Department extends OrganizationComponent {

	public Department(String name, String des) {
		super(name, des);
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public String getDes() {
		return super.getDes();
	}

	@Override
	protected void print() {
		System.out.println(getName());
	}

}
