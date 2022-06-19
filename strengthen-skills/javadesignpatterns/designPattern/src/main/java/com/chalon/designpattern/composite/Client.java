package com.chalon.designpattern.composite;

public class Client {

	public static void main(String[] args) {
		OrganizationComponent university = new University("清华大学", " 中国顶级大学 ");

		OrganizationComponent cpuCollege = new College("计算机学院", " 计算机学院 ");
		OrganizationComponent bizCollege = new College("工商管理学院", " 工商管理学院 ");
		OrganizationComponent infoCollege = new College("信息工程学院", " 信息工程学院 ");

		cpuCollege.add(new Department("软件工程", " 软件工程不错 "));
		cpuCollege.add(new Department("网络工程", " 网络软件工程不错 "));
		cpuCollege.add(new Department("计算机科学与技术", " 计算机科学与技术是老牌的专业 "));

		bizCollege.add(new Department("工商管理", " 工商管理顶级 "));
		bizCollege.add(new Department("金融管理", " 金融管理好多金融吸血虫 "));

		infoCollege.add(new Department("通信工程", " 通信工程不好学 "));
		infoCollege.add(new Department("信息工程", " 信息工程好学 "));

		university.add(cpuCollege);
		university.add(bizCollege);
		university.add(infoCollege);

//		university.print();
//		cpuCollege.print();
		bizCollege.print();

	}

}
