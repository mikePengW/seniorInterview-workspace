package com.chalon.part1.single.test;

import com.chalon.part1.single.Singleton1;

/**
 * @author wei.peng
 */
public class TestSingleton1 {
    public static void main(String[] args) {
        Singleton1 s = Singleton1.INSTANCE;
        System.out.println(s);
    }
}
