package com.atguigu.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author wei.peng
 */
public class MyForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTaskDemo myTask = new MyTaskDemo(0, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> task = forkJoinPool.submit(myTask);
        Integer result = task.get();
        System.out.println(result);
        forkJoinPool.shutdown();
    }

}

class MyTaskDemo extends RecursiveTask<Integer> {

    private static final Integer VALUE = 10;
    private int begin;
    private int end;
    private int result;

    MyTaskDemo(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) <= VALUE) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            int middle = (begin + end) / 2;
            MyTaskDemo myTask1 = new MyTaskDemo(begin, middle);
            MyTaskDemo myTask2 = new MyTaskDemo(middle + 1, end);
            myTask1.fork();
            myTask2.fork();
            result = myTask1.join() + myTask2.join();
        }
        return result;
    }

}
