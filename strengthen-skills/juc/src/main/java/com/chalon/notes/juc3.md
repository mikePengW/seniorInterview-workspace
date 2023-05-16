# JUC并发编程与源码分析

## 1、本课程前置知识及要求说明

![JVM体系结构](./img/Snipaste_2023-05-09_07-44-35.png)

![参考资料](./img/Snipaste_2023-05-09_07-45-51.png)



## 2、线程基础知识复习

![概览](./img/Snipaste_2023-05-09_07-51-55.png)

![大神](./img/Snipaste_2023-05-09_07-49-40.png)

![摩尔定律失败](./img/Snipaste_2023-05-09_07-50-27.png)

![核心源码](./img/Snipaste_2023-05-09_07-56-44.png)



## 3、CompletableFuture

### 概述

![概述](./img/Snipaste_2023-05-10_02-04-53.png)

### 并发函数参数

![并发函数](./img/Snipaste_2023-04-02_09-55-08.png)







## 4、说说Java“锁”事

### 多线程死锁

![死锁是什么](./img/Snipaste_2023-04-02_14-07-51.png)



### 如何排查死锁

![如何排查死锁](./img/Snipaste_2023-04-02_14-32-38.png)







### 小总结

![小总结1](./img/Snipaste_2023-04-02_15-02-29.png)

![小总结2](./img/Snipaste_2023-04-02_15-09-57.png)











## 5、LockSupport与线程中断













## 6、Java内存模型之JMM

可见性

原子性

有序性









## 7、volatile与JMM

volatile：可见性、有序性——排序要求，禁止重排















## 8、CAS





## 9、原子操作类之18罗汉增强





## 10、聊聊ThreadLocal





## 11、Java对象内存布局和对象头

### 对象构成布局

#### 对象实例

- 对象头
  - 对象标记Mark Word
  - 类元信息（又叫类型指针）
- 实例数据
- 对齐填充

![对象在堆内存中的存储布局](./img/Snipaste_2023-04-16_08-48-40.png)

![说明](./img/Snipaste_2023-04-16_08-50-51.png)

#### 对象标记Mark Word

![保存什么1](./img/Snipaste_2023-04-16_08-57-27.png)

![保存什么2](./img/Snipaste_2023-04-16_08-58-31.png)

![保存什么3](./img/Snipaste_2023-04-16_09-00-18.png)

#### 类元信息（又叫类型指针）

![对象原图](./img/Snipaste_2023-04-16_09-04-58.png)

#### 对象头实例说明

![说明](./img/Snipaste_2023-04-16_09-33-21.png)

Hotspot术语表官网说明地址：[官网说明](https://openjdk.org/groups/hotspot/docs/HotSpotGlossary.html)

![官网说明](./img/Snipaste_2023-04-16_09-39-21.png)

源码实现地址：[源码实现](https://github.com/openjdk/jdk8u/blob/master/hotspot/src/share/vm/oops/oop.hpp)

![实现源码](./img/Snipaste_2023-04-16_09-41-40.png)

![源码实现2](./img/Snipaste_2023-04-16_09-46-04.png)

#### 64位虚拟机（非常重要）

![64位重要](./img/Snipaste_2023-04-16_09-50-17.png)

![64位重要2](./img/Snipaste_2023-04-16_09-49-22.png)

#### 源码解析

![关键字说明1](./img/Snipaste_2023-04-16_09-55-25.png)

![关键字说明2](./img/Snipaste_2023-04-16_09-56-27.png)

#### Hotspot的实现（非常重要，64位标记图）

![Hotspot的实现](./img/Snipaste_2023-04-16_10-00-33.png)

#### 证明以上结构

JOL（Java Object Layout）证明，官网地址：[JOL官网](https://openjdk.org/projects/code-tools/jol/)

工具源码地址：[工具源码](https://github.com/openjdk/jol)

```
# 打印代码方法
ClassLayout.parseInstance(new Object()).toPrintable();
```

结果呈现说明

![结果呈现说明1](./img/Snipaste_2023-04-16_10-13-02.png)

![结果呈现说明2](./img/Snipaste_2023-04-16_10-14-38.png)

GC年龄

![10](./img/Snipaste_2023-04-16_10-50-24.png)

![GC年龄说明2](./img/Snipaste_2023-04-16_10-52-26.png)

JVM虚拟机打印参数：java -XX:+PrintCommandLineFlags -version

压缩（-XX:+UseCompressedClassPointers）、对齐到8字节的倍数



## 12、Synchronized与锁升级

锁的升级过程

![锁的升级过程](./img/Snipaste_2023-04-16_11-21-43.png)

### synchronized性能变化

Java5之前，用户态和内核态之间的切换

![切换说明](./img/Snipaste_2023-04-16_11-29-58.png)

markOop.hpp

![markOop.hpp](./img/Snipaste_2023-04-16_11-36-45.png)

Monitor

![Monitor](./img/Snipaste_2023-04-16_11-38-24.png)

![Monitor2](./img/Snipaste_2023-04-16_11-39-28.png)

synchronized和对象头说明

![说明1](./img/Snipaste_2023-04-16_11-42-44.png)

![说明2](./img/Snipaste_2023-04-16_11-48-05.png)

### synchronized锁种类及升级步骤

#### 64位标记图（非常重要）

![64位标记图](./img/Snipaste_2023-04-16_12-00-19.png)

锁指向

![所指向](./img/Snipaste_2023-04-16_11-58-56.png)

无锁

![无锁](./img/Snipaste_2023-04-16_12-07-32.png)

#### 偏向锁

![结论](./img/Snipaste_2023-04-16_14-32-51.png)

64位标记图再看

![64位标记图再看](./img/Snipaste_2023-04-16_14-34-49.png)

偏向锁的持有说明

![说明1](./img/Snipaste_2023-04-16_14-52-29.png)

![说明2](./img/Snipaste_2023-04-16_14-53-30.png)

![说明3](./img/Snipaste_2023-04-16_14-50-48.png)

细化举例

![举例说明1](./img/Snipaste_2023-04-16_15-37-14.png)

![举例说明2](./img/Snipaste_2023-04-16_15-39-16.png)

偏向锁命令

```shell
java -XX:+PrintFlagsInitial | grep biasedLock*
```

![打印参数](./img/Snipaste_2023-04-16_16-06-31.png)

![重要参数说明](./img/Snipaste_2023-04-16_16-03-24.png)

参数系统默认开启

![调整说明](./img/Snipaste_2023-04-16_16-10-38.png)

第一种情况

![第一种情况](./img/Snipaste_2023-04-16_16-16-01.png)

第二种情况

![第二种情况](./img/Snipaste_2023-04-16_16-18-41.png)

#### 撤销

![撤销说明](./img/Snipaste_2023-04-16_17-24-31.png)

![撤销说明2](./img/Snipaste_2023-04-16_17-26-16.png)

下图中红线流程部分为偏向锁获取和撤销流程：

![详细说明](./img/20200602120540100.jpg)

#### 轻量级锁

轻量级锁的获取

![轻量级锁的获取](./img/Snipaste_2023-04-16_18-11-18.png)

![获取说明2](./img/Snipaste_2023-04-16_18-13-51.png)

补充

![补充说明](./img/Snipaste_2023-04-16_18-21-31.png)

关闭偏向锁，就可以直接进入轻量级锁：

```shell
-XX:-UseBiasedLocking
```

自旋次数

Java6之前

![Java6之前](./img/Snipaste_2023-04-16_18-32-50.png)

Java6之后

![自适应自旋锁大致原理](./img/Snipaste_2023-04-16_18-33-46.png)

自适应自旋

![自适应自旋](./img/Snipaste_2023-04-16_18-35-57.png)

轻量锁与偏向锁的区别和不同

![不同](./img/Snipaste_2023-04-16_18-45-12.png)

#### 重量级锁

锁标志位

![锁标志位](./img/Snipaste_2023-04-16_18-48-01.png)

#### 小总结

锁升级发生后，hashcode去哪啦

![说明1](./img/Snipaste_2023-04-16_22-36-02.png)

![说明2](./img/Snipaste_2023-04-16_23-10-18.png)

各种锁的优缺点、synchronized锁升级和实现原理

![说明1](./img/Snipaste_2023-04-16_23-16-12.png)

![说明2](./img/Snipaste_2023-04-16_23-18-40.png)

#### JIT编译器对锁的优化

![JIT](./img/Snipaste_2023-04-16_23-31-22.png)

##### 锁消除

锁消除问题，JIT编译器会无视它，synchronized(o), 每次new出来的，不存在了，非正常。

从JIT角度看相当于无视它，synchronized(o)不存在了，这个锁对象并没有被共用扩散到其它线程使用，

极端的说就是根本没有加这个锁对象的底层机器码，消除了锁的使用

##### 锁粗化

假如方法中首尾相接，前后相邻的都是同一个锁对象，那JIT编译器就会把这几个synchronized块合并成一个大块，

加粗加大范围，一次申请锁使用即可，避免次次的申请和释放锁，提升了性能

##### 小总结

![小总结](./img/Snipaste_2023-04-16_23-30-19.png)



## 13、AbstractQueuedSynchronizer之AQS

### 前置知识

![前置知识](./img/Snipaste_2023-04-16_23-54-51.png)

### AQS入门级别理论知识

#### 官网解释

![官网解释](./img/Snipaste_2023-04-17_00-14-57.png)

#### 队列结构示意图

![示意图](./img/Snipaste_2023-04-17_00-19-22.png)

#### 和AQS有关的

![AQS有关的](./img/Snipaste_2023-04-17_00-22-28.png)

#### 锁和同步器的关系

##### 锁，面向锁的使用着

![说明](./img/Snipaste_2023-04-17_00-28-06.png)

##### 同步器，面向锁的实现者

![说明1](./img/Snipaste_2023-04-17_00-42-42.png)

![说明2](./img/Snipaste_2023-04-17_00-41-53.png)

#### 加锁会导致阻塞

![说明1](./img/Snipaste_2023-04-17_00-46-07.png)

解释说明

![解释说明1](./img/Snipaste_2023-04-17_00-49-06.png)

![解释说明2](./img/Snipaste_2023-04-17_00-50-06.png)

#### 源码说明

![源码说明](./img/Snipaste_2023-04-17_00-56-48.png)

#### AQS同步队列的基本结构

![AQS同步队列的基本结构](./img/Snipaste_2023-04-17_00-59-07.png)

### AQS源码分析前置知识储备

#### AQS内部体系架构

![架构说明1](./img/Snipaste_2023-04-17_01-02-07.png)

![架构说明2](./img/Snipaste_2023-04-17_01-04-29.png)

#### 变量state

![state说明](./img/Snipaste_2023-04-17_01-06-40.png)

#### CLH队列

![CLH队列](./img/Snipaste_2023-04-17_01-08-30.png)

![CLH队列2](./img/Snipaste_2023-04-17_01-09-23.png)

#### 小总结

![小总结](./img/Snipaste_2023-04-17_01-25-38.png)

#### Node

![AQS和Node中状态init的关系](./img/Snipaste_2023-04-17_01-28-39.png)

#### 属性说明

![属性说明](./img/Snipaste_2023-04-17_01-30-56.png)

### AQS源码深度讲解和分析

#### 总体结构

![内容目录](./img/Snipaste_2023-04-17_01-34-20.png)

#### ReentrantLock的原理

![原理说明1](./img/Snipaste_2023-04-17_01-36-24.png)

#### 公平和非公平

![代码说明1](./img/Snipaste_2023-04-17_01-42-35.png)

![代码说明2](./img/Snipaste_2023-04-17_01-44-12.png)

![代码说明3](./img/Snipaste_2023-04-17_01-45-01.png)

![代码说明4](./img/Snipaste_2023-04-17_01-49-09.png)

#### 非公平锁开始讲解

![讲解1](./img/Snipaste_2023-04-17_01-49-58.png)

![讲解2](./img/Snipaste_2023-04-17_01-52-34.png)

![讲解3](./img/Snipaste_2023-04-17_01-53-47.png)

#### lock()

![lock1](./img/Snipaste_2023-04-17_01-56-32.png)

#### 三大走向

![说明1](./img/Snipaste_2023-04-17_01-58-30.png)

![说明2](./img/Snipaste_2023-04-17_02-00-36.png)

#### 源码总体结构

![源码总体](./img/Snipaste_2023-04-17_02-01-56.png)



## 14、ReentrantLock、ReentrantReadWriteLock、StampedLock讲解

### 课程总纲

![总纲](./img/Snipaste_2023-05-08_23-54-11.png)

### 读写锁的意义

![读写锁的意义](./img/Snipaste_2023-05-09_00-06-56.png)

### 读写锁的演化过程

![演化](./img/Snipaste_2023-05-09_00-15-40.png)

#### 锁降级说明

![锁降级说明](./img/Snipaste_2023-05-09_00-17-05.png)

#### 锁降级过程

![锁降级](./img/Snipaste_2023-05-09_00-23-15.png)

#### 锁降级2

![锁降级2](./img/Snipaste_2023-05-09_00-25-59.png)

![说明](./img/Snipaste_2023-05-09_00-28-12.png)

### 不可锁升级

![不可锁升级](./img/Snipaste_2023-05-09_00-29-17.png)

![不可锁升级2](./img/Snipaste_2023-05-09_00-30-48.png)

### 写锁和读锁是互斥的

![写锁读锁互斥](./img/Snipaste_2023-05-09_00-32-23.png)

### StampedLock粗讲

![邮戳锁](./img/Snipaste_2023-05-09_00-35-33.png)

#### Oracle源码总结

![总结1](./img/Snipaste_2023-05-09_00-37-27.png)

![总结2](./img/Snipaste_2023-05-09_00-42-04.png)

![总结3](./img/Snipaste_2023-05-09_00-43-25.png)

### 邮戳锁

![比读写锁更快](./img/Snipaste_2023-05-09_00-47-07.png)

![stamp](./img/Snipaste_2023-05-09_00-48-49.png)

#### 锁饥饿问题

![锁饥饿](./img/Snipaste_2023-05-09_00-50-18.png)

#### 解决？

![解决](./img/Snipaste_2023-05-09_00-52-59.png)

#### 邮戳锁登场

![Why闪亮](./img/Snipaste_2023-05-09_00-54-54.png)

#### 一句话

![一句话](./img/Snipaste_2023-05-09_00-57-00.png)

#### 邮戳锁特点

![特点](./img/Snipaste_2023-05-09_00-58-43.png)

#### 邮戳锁的三种访问模式

![访问模式](./img/Snipaste_2023-05-09_01-00-30.png)

#### StampedLock

![StampedLock](./img/Snipaste_2023-05-09_01-02-22.png)

![StampedLock](./img/Snipaste_2023-05-09_01-03-35.png)

![一句话](./img/Snipaste_2023-05-09_01-05-05.png)

#### StampedLock的缺点

![缺点](./img/Snipaste_2023-05-09_01-19-12.png)

### 全部演化过程

![过程](./img/Snipaste_2023-05-09_01-18-16.png)



## 15、课程总结与回顾

### 多下苦功夫

![多下苦功夫打磨自己](./img/Snipaste_2023-05-09_01-22-08.png)



高级精英只能是这样辛苦的不断前进，打磨研究！！！



### 终章回顾

![终章回顾](./img/Snipaste_2023-05-09_01-24-14.png)

### “琐”事儿

![“锁”事儿](./img/Snipaste_2023-05-09_01-25-07.png)

### synchronized及升级优化

![升级](./img/Snipaste_2023-05-09_01-27-06.png)

#### 锁的到底是什么

![锁的到底是什么](./img/Snipaste_2023-05-09_01-28-15.png)

#### 64位图

Hotspot的实现

![Hotspot的实现](./img/Snipaste_2023-05-09_01-29-25.png)

### CAS

![CAS](./img/Snipaste_2023-05-09_01-31-05.png)

#### CAS的底层原理

![CAS的底层原理](./img/Snipaste_2023-05-09_01-31-29.png)

#### ABA问题

![ABA问题](./img/Snipaste_2023-05-09_01-33-27.png)

### volatile

![volatile](./img/Snipaste_2023-05-09_01-34-51.png)

### LockSupport和线程中断

![LockSupport](./img/Snipaste_2023-05-09_01-35-44.png)

#### LockSupport.park和Object.wait区别

![区别](./img/Snipaste_2023-05-09_01-36-51.png)

### AbstractQueuedSynchronizer

![AbstractQueuedSynchronizer](./img/Snipaste_2023-05-09_01-37-52.png)

#### 是什么

![是什么](./img/Snipaste_2023-05-09_01-39-41.png)

![是什么2](./img/Snipaste_2023-05-09_01-40-35.png)

#### 出队入队Node

![出队入队Node](./img/Snipaste_2023-05-09_01-41-24.png)

### ThreadLocal

![ThreadLocal](./img/Snipaste_2023-05-09_01-42-54.png)

### 原子增强类Atomic

![9-10](./img/Snipaste_2023-05-09_01-44-07.png)



### 青山不改绿水长流，一定练习，大大加油！！！





