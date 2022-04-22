package com.chalon;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wei.peng
 */
//@SpringBootTest
@DisplayName("JUnit5功能测试类")
public class Junit5Test {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 测试前置条件
     */
    @DisplayName("测试前置条件")
    @Test
    void testAssumptions() {
        Assumptions.assumeTrue(false, "结果不是true");
        System.out.println("111111");
    }

    /**
     * 断言：前面的断言失败，后面的代码都不会执行
     */
    @DisplayName("测试简单断言")
    @Test
    void testSimpleAssertions() {
        int cal = cal(2, 3);
        // 相等
        assertEquals(5, cal, "业务逻辑计算失败");
        //
        Object obj1 = new Object();
        Object obj2 = new Object();
        assertSame(obj1, obj2, "两个对象不一样");
    }

    int cal(int i, int j) {
        return i + j;
    }

    @Test
    @DisplayName("array assertion")
    void array() {
        assertArrayEquals(new int[]{1, 2}, new int[]{1, 2}, "数组内容不相等");
    }

    @Test
    @DisplayName("组合断言")
    void all() {
        /**
         * 所有断言全部需要成功
         */
        assertAll("test",
                () -> assertTrue(true && false, "结果不是true"),
                () -> assertEquals(1, 2, "结果不是1")
        );
        System.out.println(1);
    }

    @DisplayName("异常断言")
    @Test
    void testException() {
        // 断定业务逻辑一定出现异常
        assertThrows(ArithmeticException.class, () -> {
            int i = 10 / 2;
        }, "业务逻辑居然正常运行？");
    }

    @Test
    @DisplayName("超时断言")
    void timeoutTest() {
        assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(1100), "超时咯，快跑啊！");
    }

    @DisplayName("快速失败")
    @Test
    void testFail() {
        if (1 == 2) {
            fail("测试失败");
        }
    }


    @DisplayName("测试 displayName 注解")
    @Test
    void testDisplayName() {
        System.out.println(1);
        System.out.println(jdbcTemplate);
    }

    @Disabled
    @DisplayName("测试方法2")
    @Test
    void test2() {
        System.out.println("2");
    }

    /**
     * 规定方法超时时间。超出时间测试抛出异常
     *
     * @throws InterruptedException
     */
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @Test
    void testTimeout() throws InterruptedException {
        Thread.sleep(600);
    }

    @RepeatedTest(5)
    @Test
    void test3() {
        System.out.println(5);
    }

    @BeforeEach
    void testBefore() {
        System.out.println("测试开始了");
    }

    @AfterEach
    void testAfter() {
        System.out.println("测试结束了");
    }

    @BeforeAll
    static void testAllBefore() {
        System.out.println("所有测试开始了");
    }

    @AfterAll
    static void testAllAfter() {
        System.out.println("所有测试结束了");
    }

}
