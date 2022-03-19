package com.chalon.part1.step;

import org.junit.jupiter.api.Test;

/**
 * @author wei.peng
 */
public class TestStep {
    @Test
    public void test() {
        long start = System.currentTimeMillis();
        // 165580141
        System.out.println(f(40));
        long end = System.currentTimeMillis();
        // 354ms
        System.out.println(end - start);
    }

    /**
     * 实现f(n)：求n步台阶，一共有几种走法
     *
     * @param n
     * @return
     */
    public int f(int n) {
        if (n < 1) {
            throw new IllegalArgumentException(n + "不能小于1");
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return f(n - 2) + f(n - 1);
    }
}
