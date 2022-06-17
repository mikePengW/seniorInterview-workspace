package com.chalon.principle.singleresponsibility;

/**
 * @author wei.peng
 */
public class SingleResponsibility3 {
    public static void main(String[] args) {
        Vehicle2 vehicle = new Vehicle2();
        vehicle.roadRun("摩托车");
        vehicle.roadRun("汽车");
        vehicle.airRun("飞机");
        vehicle.waterRun("潜艇");

    }

}

// 方式3分析
// 1. 这种修改方法没有对原来的类做大的修改，只是增加方法
// 2. 这里虽然没有在类这个级别上遵守单一职责原则，但是在方法级别上，仍然是遵守单一指责
class Vehicle2 {

    public void roadRun(String vehicle) {
        System.out.println(vehicle + " 在公路上运行......");
    }

    public void airRun(String vehicle) {
        System.out.println(vehicle + " 在天空中运行......");
    }

    public void waterRun(String vehicle) {
        System.out.println(vehicle + " 在水中运行......");
    }

}
