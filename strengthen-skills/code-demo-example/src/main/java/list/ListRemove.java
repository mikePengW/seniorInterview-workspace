package list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * list 遍历的动态删除
 *
 * <a href="https://www.jb51.net/article/248506.htm">
 *
 * @author wei.peng
 */
public class ListRemove {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        System.out.println("list.size()=" + list.size());

        // 错误方式
//        removeDataError(list);

        // 方式1
//        removeDataRight1(list);
        // 方式2
//        removeDataRight2(list);
        // 方式3
        removeDataRight3(list);

    }

    // 错误方式
    public static void removeDataError(List<Integer> list) {
        System.out.println("before remove error: list.size()=" + list.size());
        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
        }

        System.out.println("after remove error: list.size()=" + list.size());
    }

    // 解决方法1：每移过一次后，再把 i 移回来
    public static void removeDataRight1(List<Integer> list) {
        System.out.println("before remove right1: list.size()=" + list.size());
        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
            i--;
        }
        System.out.println("after remove right1: list.size()=" + list.size());
    }

    // 解决方法2：先删除后面的元素
    public static void removeDataRight2(List<Integer> list) {
        System.out.println("before remove right2: list.size()=" + list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            list.remove(i);
        }
        System.out.println("after remove right2: list.size()=" + list.size());
    }

    // 解决方法3：使用iterator删除
    public static void removeDataRight3(List<Integer> list) {
        System.out.println("before remove right3: list.size()=" + list.size());
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            // 我们在用迭代删除(Iterator.remove())时，可能会因为没有“it.next();”这一行， 抛出java.lang.IllegalStateException异常，
            // 原因是通过Iterator来删除集合中某一个不满足条件的元素时，首先需要使用next方法迭代出集合中的元素 ，然后才能调用remove方法，
            // 否则集合可能会因为对同一个Iterator remove了多次而抛出java .lang.IllegalStateException异常。
            // 记得在循环体中别忘了iterator.next()
            it.next();
            it.remove();
        }
        System.out.println("after remove right3: list.size()=" + list.size());
    }

}
