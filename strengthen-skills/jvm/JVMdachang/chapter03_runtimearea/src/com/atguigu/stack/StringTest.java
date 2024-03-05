package com.atguigu.stack;

/**
 * @author wei.peng wrote on 2024-01-19
 * @version 1.0
 */
public class StringTest {

    public static void main(String[] args) {

        String s1 = "111\n222";
        String s2 = "111\\n222";
        System.out.println(s1.equals(s2));

        String src = "\\n";
        String target = "\n";
        System.out.println(s2.replace(src, target));

    }

}
