package com.chalon.part1.single.test;

import com.chalon.part1.single.Singleton4;

import java.util.concurrent.*;

/**
 * @author wei.peng
 */
public class TestSingleton4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Singleton4 s1 = Singleton4.getInstance();
//        Singleton4 s2 = Singleton4.getInstance();
//
//        System.out.println(s1 == s2);
//        System.out.println(s1);
//        System.out.println(s2);

        Callable<Singleton4> c = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<Singleton4> f1 = es.submit(c);
        Future<Singleton4> f2 = es.submit(c);
        Future<Singleton4> f3 = es.submit(c);
        Future<Singleton4> f4 = es.submit(c);
        Future<Singleton4> f5 = es.submit(c);

        Singleton4 s1 = f1.get();
        Singleton4 s2 = f2.get();
        Singleton4 s3 = f3.get();
        Singleton4 s4 = f4.get();
        Singleton4 s5 = f5.get();

        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);

        es.shutdown();

    }

}
