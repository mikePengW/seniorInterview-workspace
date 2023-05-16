package list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * list 遍历时动态添加，使用报错
 * <p>
 * 一、问题
 * 使用Iterator在对List集合进行遍历集合时，如果只是遍历而不进行增加、删除操作时，可以正常运行吗，但是如果我们在使用迭代器对List集合进行插入或者删除时，
 * 就会出现Exception in thread "main" java.util.ConcurrentModificationException这个异常。（调用了next方法）
 * 二、原因分析
 * 前提：先了解一个AbstractList中，有一个全局变量madCount
 * 在AbstractList中，有一个全局变量madCount，记录了结构性改变的次数。结构性改变指的是那些修改了列表大小的操作，在迭代过程中可能会造成错误的结果。
 * madCount交由迭代器（Iterator）和列表迭代器（ListIterator）使用，当进行next()、remove()、previous()、set()、add()等操作时，
 * 如果madCount的值意外改变，那么迭代器或者列表迭代器就会抛出ConcurrentModificationException异常。
 * 所以我们每次 add 或者 remove 操作时，这个modCount都会自增一次。
 * 获取迭代器是如何操作的？
 * 三、总结
 * 当我们使用ArrayList做 add 或者 remove 操作时，都会改变 madCount (记录了结构性改变的次数) 的值，而在我们获取迭代器时，
 * 其实是获取了ArrayList内部类的迭代器，然后我们使用next()这个方法时都会调用checkForComodification（）方法，
 * 可以看出在这个方法中抛出了异常，那么抛出异常的条件是因为modCount != expectedModCount这个条件，
 * modCount是指记录了结构性改变的次数，expectedModCount代表期望遍历次数，当我们在使用迭代器进行遍历并插入或者删除时，
 * modCount就会改变，从而导致modCount != expectedModCount，从而抛出异常。
 *
 * <a href="https://blog.csdn.net/qq_41995919/article/details/125026350">
 *
 * @author wei.peng
 */
public class ListAdd {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println("before list.size()=" + list.size());
        list.forEach(System.out::println);

        for (Iterator it = list.listIterator(); it.hasNext(); ) {
            Integer next = (Integer) it.next();
            if (5 == next) {
                list.add(6);
            }
        }

        System.out.println("after list.size()=" + list.size());
        list.forEach(System.out::println);

    }

}
