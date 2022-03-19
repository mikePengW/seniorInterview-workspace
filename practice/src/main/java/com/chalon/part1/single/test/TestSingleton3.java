package com.chalon.part1.single.test;

import com.chalon.part1.single.Singleton3;

/**
 * @author wei.peng
 */
public class TestSingleton3 {
    public static void main(String[] args) {
        Singleton3 s = Singleton3.INSTANCE;
        System.out.println(s);
    }
}
