package com.chalon.part1.single.test;

import com.chalon.part1.single.Singleton2;

/**
 * @author wei.peng
 */
public class TestSingleton2 {
    public static void main(String[] args) {
        Singleton2 s = Singleton2.INSTANCE;
        System.out.println(s);
    }
}
