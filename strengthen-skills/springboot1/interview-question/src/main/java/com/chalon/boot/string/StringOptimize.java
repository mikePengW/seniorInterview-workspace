package com.chalon.boot.string;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * @author wei.peng wrote on 2024-04-19
 * @version 1.0
 */
public class StringOptimize {

    @Test
    public void stringTest1() {
        String a = "abc";
        String b = "abc";
        System.out.println(a == b);
    }

    @Test
    public void stringTest2() {
        String a = new String("abc").intern();
        String b = new String("abc").intern();
        System.out.println(a == b);
    }

    @Test
    public void stringTest3(){
        String str1 = "abc";
        String str2 = new String ("abc");
        String str3 = str2.intern();
        System.out.println(str1 == str2);
        System.out.println(str2 == str3);
        System.out.println(str1 == str3);
    }

}
