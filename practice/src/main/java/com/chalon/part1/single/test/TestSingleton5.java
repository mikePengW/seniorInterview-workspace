package com.chalon.part1.single.test;

import com.chalon.part1.single.Singleton5;

import java.util.concurrent.*;

/**
 * @author wei.peng
 */
public class TestSingleton5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Singleton5> c = new Callable<Singleton5>() {
            @Override
            public Singleton5 call() throws Exception {
                return Singleton5.getInstance();
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<Singleton5> f1 = es.submit(c);
        Future<Singleton5> f2 = es.submit(c);
        Future<Singleton5> f3 = es.submit(c);
        Future<Singleton5> f4 = es.submit(c);
        Future<Singleton5> f5 = es.submit(c);

        Singleton5 s1 = f1.get();
        Singleton5 s2 = f2.get();
        Singleton5 s3 = f3.get();
        Singleton5 s4 = f4.get();
        Singleton5 s5 = f5.get();

        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5);

        es.shutdown();

    }

}
