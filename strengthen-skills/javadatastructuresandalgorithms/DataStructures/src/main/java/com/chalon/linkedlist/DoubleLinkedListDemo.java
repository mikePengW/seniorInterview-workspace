package com.chalon.linkedlist;

/**
 * @author wei.peng
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero5 = new HeroNode2(5, "武松", "打虎");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);
        doubleLinkedList.add(hero5);
        System.out.println("院士双向链表数据~~");
        doubleLinkedList.list();

        HeroNode2 newHeroNode = new HeroNode2(5, "公孙胜", "入云龙");
        doubleLinkedList.update(newHeroNode);
        System.out.println("修改后的链表情况~~");
        doubleLinkedList.list();

        doubleLinkedList.del(4);
        System.out.println("删除后的链表情况~~");
        doubleLinkedList.list();

    }

}

// 创建一个双向链表的类
class DoubleLinkedList {
    private HeroNode2 head = new HeroNode2(0, "", "");

    // 返回头节点
    public HeroNode2 getHead() {
        return head;
    }

    // 显示链表[遍历]
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 头节点不能动，因此需要一个辅助变量来遍历
        HeroNode2 temp = head.next;
        while (true) {
            // 判断是否到链表最后
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            // 将temp后移，一定小心
            temp = temp.next;
        }
    }

    // 添加一个节点到双向链表的最后。
    public void add(HeroNode2 heroNode) {
        // 因为head节点不能动，因此我们需要一个辅助遍历 temp
        HeroNode2 temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {
                break;
            }
            // 如果没有找到最后，将temp后移
            temp = temp.next;
        }
        // 当退出while循环时，temp就指向了链表的最后
        // 形成一个双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    public void update(HeroNode2 newHeroNode) {
        if (head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        // 找到需要修改的节点，根据no编号
        // 定义一个辅助变量
        HeroNode2 temp = head.next;
        boolean flag = false; // 表示是否找到该节点
        while (true) {
            if (temp == null) {
                break; // 已经遍历完链表
            }
            if (temp.no == newHeroNode.no) {
                // 找到
                flag = true;
                break;
            }
            temp = temp.next;
        }
        // 根据flag 判断是否找到要修改的节点
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else { // 没有找到
            System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newHeroNode.no);
        }
    }

    // 从双向链表中删除一个节点
    // 1 双向链表中，我们可以直接找到要删除的这个节点
    // 2 找到后，自我删除即可
    public void del(int no) {
        // 判断当前链表是否为空
        if (head.next == null) { // 空链表
            System.out.println("链表为空，无法删除");
            return;
        }

        HeroNode2 temp = head.next; // 辅助变量（指针）
        boolean flag = false; // 标志是否找到待删除节点
        while (true) {
            if (temp == null) { // 已经到链表的最后
                break;
            }
            if (temp.no == no) {
                // 找到的待删除节点的前一个节点temp
                flag = true;
                break;
            }
            temp = temp.next; // temp后移，遍历
        }
        if (flag) { // 找到
            // 可以删除
            // temp.next = temp.next.next; [单向链表]
            temp.pre.next = temp.next;
            // 如果是最后一个节点，就不需要执行下面这句话，否则出现空指针
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }

    public void addByOrder(HeroNode2 heroNode) {
        // 单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
        HeroNode2 temp = head;
        boolean flag = false; // flag标记添加的编号是否存在，默认false
        while (true) {
            if (temp.next == null) { // 说明temp已经在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) { // 位置找到，就在temp的后面插入
                break;
            } else if (temp.next.no == heroNode.no) { // 说明希望添加的heroNode的编号已然存在
                flag = true; // 编号存在
                break;
            }
            temp = temp.next; // 后移，遍历当前链表
        }
        if (flag) { // 不能添加，说明编号存在
            System.out.printf("准备插入的英雄编号 %d 已经存在了，不能加入\n", heroNode.no);
        } else {
            // 插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
            heroNode.pre = temp;
        }
    }

}

// 每个 HeroNode2 对象就是一个节点
class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next; // 指向下一个节点，默认为null
    public HeroNode2 pre; // 指向前一个节点，默认为null

    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode2 [no=" + no + ", name='" + name + '\'' + ", nickname='" + nickname + '\'' + ']';
    }

}
