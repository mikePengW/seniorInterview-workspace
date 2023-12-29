package jdk21.pms;

/**
 * @author wei.peng wrote on 2023-12-29
 * @version 1.0
 */
public class PatternMatchingSwitchExample {

    public static void main(String[] args) {
        System.out.println(formatter(10L));

        System.out.println(formatterPatternSwitch(100L));
    }

    // Prior to Java 21
    static String formatter(Object obj) {
        String formatted = "unknown";
        if (obj instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (obj instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (obj instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (obj instanceof String s) {
            formatted = String.format("String %s", s);
        }
        return formatted;
    }

    // As of Java 21
    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String %s", s);
            default -> obj.toString();
        };
    }

    // Prior to Java 21
    static void testFooBarOld(String s) {
        if (s == null) {
            System.out.println("Oops!");
            return;
        }
        switch (s) {
            case "Foo", "Bar" -> System.out.println("Great");
            default -> System.out.println("Ok");
        }
    }

    // As of Java 21
    static void testFooBarNew(String s) {
        switch (s) {
            case null -> System.out.println("Oops!");
            case "Foo", "Bar" -> System.out.println("Great");
            default -> System.out.println("Ok");
        }
    }

}
