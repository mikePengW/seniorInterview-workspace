package list;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * 使用 ListIterator 对 List 遍历时添加、修改、删除
 * <p>
 * ListIterator就是在Iterator的基础上添加的prev系列方法，可以实现反向操作，最重要的是添加了add和set方法，
 * 可以实现遍历List的时候同时进行添加，修改的操作。
 * <p>
 * public interface ListIterator<E> extends Iterator<E>
 * 系列表迭代器，允许程序员按任一方向遍历列表、迭代期间修改列表，并获得迭代器在列表中的当前位置。
 * <p>
 * Iterator 和 ListIterator 主要区别
 * ListIterator有add()方法，可以向List中添加对象，而Iterator不能
 * ListIterator和Iterator都有hasNext()和next()方法，可以实现顺序向后遍历，但是ListIterator有hasPrevious()和previous()方法，
 * 可以实现逆向（顺序向前）遍历。Iterator就不可以。
 * ListIterator可以定位当前的索引位置，nextIndex()和previousIndex()可以实现。Iterator没有此功能。
 * ListIterator 可以再迭代时对集合进行add、set、remove操作，而Iterator迭代器只能在迭代时对集合进行 remove 操作
 *
 * <a href="http://www.ibloger.net/article/3195.html">
 *
 * @author wei.peng
 */
public class ListIteratorModify {

    public static void main(String[] args) {

        // 简单测试
//        listIterator1();

        // 复杂使用
        listIterator2();

    }

    // 复杂使用
    private static void listIterator2() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            list.add(i);
        }

        ListIterator<Integer> li = list.listIterator();
        boolean flag = true;
        // 如果是正序迭代 或者 有前一个可以迭代的元素
        while (flag || li.hasPrevious()) {
            int index = 0;
            int ele = 0;
            if (flag) {
                index = li.nextIndex();  // nextIndex() 返回下一个元素的索引
                ele = li.next();         // next() 返回下一个元素
            } else {
                index = li.previousIndex(); // previousIndex 返回上一个元素的索引
                ele = li.previous();        // previous() 返回上一个元素
            }
            if (ele == 1) {
                // 如果迭代到的元素是 1 ，则将该元素替换成 0
                li.set(0); // set() 用指定元素替换最后返回的元素
            } else if (ele == 3) {
                li.remove(); // remove() 移除最后返回的元素
            }
            System.out.println("(" + index + ") = " + ele);

            // 判断是否还有下一个可以迭代的元素
            if (!li.hasNext()) {
                flag = false;
                li.add(10); // add() 添加一个元素
            }
        }
    }

    // 简单测试
    private static void listIterator1() {
        ArrayList list = new ArrayList();
        list.add("java01");
        list.add("java02");
        list.add("java03");
        System.out.println("before list: " + list);

        ListIterator li = list.listIterator();
        while (li.hasNext()) {
            Object obj = li.next();
            if (obj.equals("java01")) {
                li.set("java009");
            }
        }

        System.out.println("after list: " + list);
    }

}
