package jdk21.rp;

/**
 * @author wei.peng wrote on 2023-12-29
 * @version 1.0
 */
record Person(String name, int age) {
}

public class RecordPatternsExample {

    public static void main(String[] args) {
        p1();
        printSum16(new Point(9, 10));
        printSum21(new Point(10, 11));
    }

    static void p1() {
        Person person = new Person("Alice", 25);
        if (person instanceof Person p) {
            System.out.println(p.name()); // 输出 "Alice"
            System.out.println(p.age()); // 输出 25
        }
    }

    // As of Java 16
    static void printSum16(Object obj) {
        if (obj instanceof Point p) {
            int x = p.x();
            int y = p.y();
            System.out.println(x + y);
        }
    }

    // As of Java 21
    static void printSum21(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            System.out.println(x + y);
        }
    }

}

record Point(int x, int y) {
}
