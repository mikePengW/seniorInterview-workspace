package com.chalon.designpattern.builder.improve;

public class Client {
	public static void main(String[] args) {
		CommonHouse commonHouse = new CommonHouse();
		HouseDirector houseDirector = new HouseDirector(commonHouse);
		houseDirector.constructHouse();

		System.out.println();
		HighBuilding highBuilding = new HighBuilding();
		houseDirector.setHouseBuilder(highBuilding);
		houseDirector.constructHouse();

	}

}
