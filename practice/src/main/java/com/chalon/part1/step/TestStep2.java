package com.chalon.part1.step;

import org.junit.jupiter.api.Test;

/**
 * @author wei.peng
 */
public class TestStep2 {
    @Test
    public void test() {
        long start = System.currentTimeMillis();
        // 165580141
        System.out.println(loop(40));
        long end = System.currentTimeMillis();
        // <1ms
        System.out.println(end - start);
    }

    /**
     * 实现f(n)：求n步台阶，一共有几种走法
     *
     * @param n
     * @return
     */
    public int loop(int n) {
        if (n < 1) {
            throw new IllegalArgumentException(n + "不能小于1");
        }
        if (n == 1 || n == 2) {
            return n;
        }

        // 初始化为走到第二级台阶的走法
        int one = 2;
        // 初始化为走到第一级台阶的走法
        int two = 1;
        int sum = 0;

        for (int i = 3; i <= n; i++) {
            // 最后跨2步 + 最后跨1步的走法
            sum = two + one;
            two = one;
            one = sum;
        }
        return sum;
    }
}
