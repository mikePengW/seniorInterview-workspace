package com.chalon.designpattern.visitor;

public abstract class Action {

	// 得到男性的 评价
	public abstract void getManResult(Man man);

	// 得到女性的 评价
	public abstract void getWomanResult(Woman woman);

}
