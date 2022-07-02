package com.chalon.linkedlist;

/**
 * 合并两个有序的单链表，合并之后的链表依然有序
 *
 * @author wei.peng
 */
public class ClassExercise {

    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero5 = new HeroNode(5, "武松", "打虎");
        SingleLinkedList singleLinkedList1 = new SingleLinkedList();
        singleLinkedList1.addByOrder(hero1);
        singleLinkedList1.addByOrder(hero5);
        singleLinkedList1.addByOrder(hero3);
        System.out.println("原来链表1的情况~~");
        singleLinkedList1.list();

        HeroNode hero12 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero14 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero16 = new HeroNode(6, "李逵", "勇猛");
        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
        singleLinkedList2.addByOrder(hero12);
        singleLinkedList2.addByOrder(hero14);
        singleLinkedList2.addByOrder(hero16);
        System.out.println("原来链表2的情况~~");
        singleLinkedList2.list();

        System.out.println("合并后~~~");

    }

    // TODO
    // 合并两个有序单链表
    // 按照顺序创建新的列表，发现两个链表那个更小就加到新的链表里面去，再发现下一个更小就加进去
    public static SingleLinkedList mergeList(HeroNode head1, HeroNode head2) {
        return null;
    }

}
