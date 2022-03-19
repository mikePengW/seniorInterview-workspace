package com.chalon.part1.init;

/**
 * 子类的初始化<clinit>:
 * （1）j = method();
 * （2）子类的静态代码块
 * <p>
 * 先初始化父类：(5)(1)
 * 初始化子类：(10)(6)
 * <p>
 * 子类的实例化方法：
 * （1）super()（最前）    (9) (3) (2)
 * （2）i = test();      (9)
 * （3）子类的非静态代码块   (8)
 * （4）子类的无参构造（最后）  (7)
 * <p>
 * 因为创建了两个Son对象，因此实例化方法<init>执行了两次
 * （9）（3）（2）（9）（8）（7）
 *
 * @author wei.peng
 */
public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }

    Son() {
//        super(); // 写或不写都在，在子类构造器中一定会调用父类的构造器
        System.out.print("(7)");
    }

    {
        System.out.print("(8)");
    }

    @Override
    public int test() {
        System.out.print("(9)");
        return 1;
    }

    public static int method() {
        System.out.print("(10)");
        return 1;
    }

    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println();
        Son s2 = new Son();
    }

}
