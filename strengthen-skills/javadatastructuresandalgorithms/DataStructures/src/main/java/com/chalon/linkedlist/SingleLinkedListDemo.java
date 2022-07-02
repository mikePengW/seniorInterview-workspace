package com.chalon.linkedlist;

import java.util.Stack;

/**
 * @author wei.peng
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero5 = new HeroNode(5, "武松", "打虎");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero5);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);

        // 加入按照编号顺序
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero5);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero3);

        System.out.println("原来链表的情况~~");
        singleLinkedList.list();

        // 单链表的反转功能
//        System.out.println("反转单链表~~");
//        reversalList(singleLinkedList.getHead());
//        singleLinkedList.list();

        // 逆序打印
        System.out.println("逆序打印单链表,没有改变链表的结构~~");
        reversePrint(singleLinkedList.getHead());

//        // 修改
//        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~~");
//        singleLinkedList.update(newHeroNode);
//
//        System.out.println("修改后的链表情况~~~");
//        singleLinkedList.list();
//
//        // 删除
//        singleLinkedList.del(1);
//        singleLinkedList.del(2);
//        System.out.println("删除后的链表情况~~~");
//        singleLinkedList.list();
//
//        // 求单链表中有效节点的个数
//        System.out.println("有效的节点个数= " + getLength(singleLinkedList.getHead()));
//
//        // 得到倒数第K个节点
//        HeroNode res = findLastIndexNode(singleLinkedList.getHead(), 5);
//        System.out.println("res=" + res);

    }

    // 逆序打印
    // 方式1：反序后打印；
    // 方式2：利用栈这个数据结构，将各个节点压入栈中，然后利用栈的先进后出特点，实现逆序打印的效果
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return; // 空链表，不能打印
        }
        // 创建一个栈，将各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        // 将链表的所有节点压入栈
        while (cur != null) {
            stack.push(cur);
            cur = cur.next; // cur后移，这样就可以压入下一个节点
        }
        // 将栈中的节点进行打印，pop出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop()); // stack的特点就是先进后出
        }
    }

    // 将单链表反转
    public static void reversalList(HeroNode head) {
        // 如果当前链表为空，或者只有一个节点，无需反转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
        // 定义一个辅助的指针（变量），帮助我们遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null; // 指向当前节点[cur]的下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        // 遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端
        // 动脑筋
        while (cur != null) {
            next = cur.next; // 先暂时保存当前节点的下一个节点，因为后面需要使用
            cur.next = reverseHead.next; // 将cur的下一个节点指向新的链表的最前端
            reverseHead.next = cur; // 将cur 连接到新的链表上
            cur = next; // 让cur后移
        }
        // 将head.next 指向 reverseHead.next 实现单链表的反转
        head.next = reverseHead.next;
    }

    // 查找单链表中的倒数第K个节点
    // 1. 编写一个方法，接收head节点，同时接收一个index
    // 2. index 表示是倒数第index个节点
    // 3. 先把链表从头到尾遍历，得到链表的总长度getLength
    // 4. 得到size 后，我们从链表的第一个开始遍历（size-index）个，就可以得到
    // 5. 如果找到了，则返回该节点，否则返回null
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        // 判断链表为空，返回null
        if (head.next == null) {
            return null; // 没有找到
        }
        // 第一个遍历得到链表的长度（节点个数）
        int size = getLength(head);
        // 第二次遍历 size-index 位置，就是我们倒数的第K个节点
        // 先做一个index的校验
        if (index <= 0 || index > size) {
            return null;
        }
        // 定义辅助变量，for循环定位到倒数的index
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 获取单链表的节点个数（如果带头节点的链表，需要不统计头节点）
     *
     * @param head 链表的头节点
     * @return 返回的是有效节点的个数
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) { // 空链表
            return 0;
        }
        int length = 0;
        // 定义一个辅助的变量，这里没有统计头节点
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next; // 遍历
        }
        return length;
    }

}

class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    // 返回头节点
    public HeroNode getHead() {
        return head;
    }

    // 思路，当不考虑编号顺序时
    // 1. 找到当前链表的最后节点
    // 2. 将最后这个节点的next 指向 新的节点
    public void add(HeroNode heroNode) {
        // 因为head节点不能动，因此我们需要一个辅助遍历 temp
        HeroNode temp = head;
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
        // 将最后这个节点的next 指向 新的节点
        temp.next = heroNode;
    }

    public void addByOrder(HeroNode heroNode) {
        // 单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
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
        }
    }

    // 修改节点的信息，根据no编号来修改，即no编号不能改
    // 1. 根据 nowHeroNode 的 no 来修改即可
    public void update(HeroNode newHeroNode) {
        if (head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        // 找到需要修改的节点，根据no编号
        // 定义一个辅助变量
        HeroNode temp = head.next;
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

    // 删除节点
    // 1. head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
    // 2. 我们在比较时，是temp.next.no 和 需要删除的节点的no比较
    public void del(int no) {
        HeroNode temp = head;
        boolean flag = false; // 标志是否找到待删除节点
        while (true) {
            if (temp.next == null) { // 已经到链表的最后
                break;
            }
            if (temp.next.no == no) {
                // 找到的待删除节点的前一个节点temp
                flag = true;
                break;
            }
            temp = temp.next; // temp后移，遍历
        }
        if (flag) { // 找到
            // 可以删除
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }

    // 显示链表[遍历]
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 头节点不能动，因此需要一个辅助变量来遍历
        HeroNode temp = head.next;
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

}

// 每个 HeroNode 对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name='" + name + '\'' + ", nickname='" + nickname + '\'' + ']';
    }

}
