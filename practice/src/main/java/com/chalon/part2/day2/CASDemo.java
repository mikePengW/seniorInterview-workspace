package com.javalabs.handicraft.day2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wei.peng
 * <p>
 * 1    CAS什么？  ===> compareAndSet 比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        // main do thing......

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
