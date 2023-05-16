

## Cloud升级

### 课程文档文件

[文档](./file/SpringCloud2020.mmap)



### Cloud升级

![Cloud升级](./img/Snipaste_2023-05-02_16-16-49.png)

官网链接：[SpringCloud](https://github.com/spring-cloud)



### Ribbon负载均衡服务调用

#### Ribbon的轮询方式：

![robin调用规则](./img/Snipaste_2023-05-01_12-25-20.png)

#### 负载均衡算法原理：

![算法原理](./img/Snipaste_2023-05-02_11-52-25.png)



### OpenFeign服务接口调用

#### 概览

![概览](./img/Snipaste_2023-05-02_21-55-23.png)

#### 能干什么

![能干什么](./img/Snipaste_2023-05-02_16-28-39.png)

Feign和OpenFeign两者区别

![区别](./img/Snipaste_2023-05-02_16-30-37.png)

#### 官网文档

[Feign源码](https://github.com/OpenFeign/feign)

[OpenFeign源码](https://github.com/spring-cloud/spring-cloud-openfeign)

[OpenFeign文档](https://docs.spring.io/spring-cloud-openfeign/docs/4.0.2/reference/html/)



### Hystrix断路器

#### 服务雪崩

![雪崩](./img/Snipaste_2023-05-03_06-52-41.png)

#### 是什么

![是啥](./img/Snipaste_2023-05-03_06-54-11.png)

#### 文档官网

[官网](https://github.com/Netflix/Hystrix/wiki/How-To-Use)

#### 服务降级

![服务降级](./img/Snipaste_2023-05-03_08-34-09.png)

#### 服务熔断

![服务熔断](./img/Snipaste_2023-05-03_08-36-02.png)

![服务熔断2](./img/Snipaste_2023-05-03_08-38-27.png)

#### 服务限流

![服务限流](./img/Snipaste_2023-05-03_08-36-47.png)



#### 详解降级

##### 服务问题与解决

![问题与解决](./img/Snipaste_2023-05-03_08-58-59.png)

##### 官方文档

[文档demo](https://github.com/Netflix/Hystrix)

##### 降级解决膨胀和混乱问题

![解决问题](./img/Snipaste_2023-05-03_09-25-39.png)



#### 详解熔断

##### 熔断是什么

![熔断是什么](./img/Snipaste_2023-05-03_09-34-15.png)

[大神论文](https://martinfowler.com/bliki/CircuitBreaker.html)

##### 代码

![代码](./img/Snipaste_2023-05-03_09-46-52.png)

##### 重点测试

![重点测试](./img/Snipaste_2023-05-03_09-48-28.png)

##### 小总结

熔断类型

![熔断类型](./img/Snipaste_2023-05-03_09-50-38.png)

断路流程图

![流程图](./img/circuit-breaker-1280.png)

##### 断路器什么情况下起作用

![起作用说明](./img/Snipaste_2023-05-03_09-54-43.png)

##### 开启和关闭的条件

![条件](./img/Snipaste_2023-05-11_07-42-46.png)

##### 断路器打开之后

![打开之后](./img/Snipaste_2023-05-11_07-44-57.png)

##### All配置

[all配置源码地址](https://github.com/Netflix/Hystrix/blob/master/hystrix-core/src/main/java/com/netflix/hystrix/HystrixCommandProperties.java)

![all配置](./img/Snipaste_2023-05-11_07-45-58.png)



#### 熔断流程图

![熔断流程图](./img/hystrix-command-flow-chart.png)

##### 步骤说明

![说明](./img/Snipaste_2023-05-11_07-53-01.png)

#### dashboard配置

![dashboard](./img/Snipaste_2023-05-11_08-00-11.png)



### zuul路由网关

#### 暂无笔记





### Gateway新一代网关

#### 概述

![概述](./img/Snipaste_2023-05-12_07-48-36.png)

![概述2](./img/Snipaste_2023-05-12_07-49-50.png)

![netty](./img/Snipaste_2023-05-12_07-50-30.png)

#### 架构

![架构](./img/cloud-3.svg)

![架构2](./img/Snipaste_2023-05-12_07-53-21.png)

#### zuul和spring cloud Gateway 的区别

![区别](./img/Snipaste_2023-05-12_07-58-28.png)

#### zuul模型

![zuul1.x模型](./img/Snipaste_2023-05-12_08-00-08.png)

![zuul1.x模型2](./img/Snipaste_2023-05-12_08-03-20.png)

#### Gateway模型

![Gateway模型](./img/Snipaste_2023-05-12_08-04-39.png)



### SpringCloud Config 分布式配置中心

![访问结构](./img/Snipaste_2023-05-13_14-16-45.png)



### SpringCloud Bus 消息总线

#### 是什么

![是什么](./img/Snipaste_2023-05-13_14-29-15.png)

#### 能干嘛

![能干嘛](./img/Snipaste_2023-05-13_14-42-35.png)

#### 总线

![总线](./img/Snipaste_2023-05-13_14-44-11.png)

#### 架构图

![图1](./img/Snipaste_2023-05-13_15-09-38.png)

![图2](./img/Snipaste_2023-05-13_15-10-31.png)

##### 选择二原因如下

![原因](./img/Snipaste_2023-05-13_15-13-07.png)

#### 总结

![通知总结](./img/Snipaste_2023-05-13_15-50-05.png)



### SpringCloud Stream 消息驱动

#### 一句话

![说明](./img/Snipaste_2023-05-13_16-34-42.png)

![说明2](./img/Snipaste_2023-05-13_16-39-23.png)

#### 为什么用Cloud Stream

![why1](./img/Snipaste_2023-05-13_16-43-27.png)

![why2](./img/Snipaste_2023-05-13_16-44-35.png)

#### 凭什么解决差异

![说明1](./img/Snipaste_2023-05-13_16-45-40.png)

##### Binder

![binder1](./img/Snipaste_2023-05-13_16-47-20.png)

![binder2](./img/Snipaste_2023-05-13_16-48-25.png)

#### 标准套路

![套路1](./img/Snipaste_2023-05-13_16-50-44.png)

![套路2](./img/Snipaste_2023-05-13_16-52-24.png)

![套路3](./img/Snipaste_2023-05-13_16-54-36.png)

##### 常用API

![常用](./img/Snipaste_2023-05-13_16-55-19.png)

##### YML

[使用官方文档](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream-binder-rabbit.html#rabbit-dlq-processing)

![YML](./img/Snipaste_2023-05-13_17-01-20.png)

#### 消费

##### 生产实际案例

![生产案例](./img/Snipaste_2023-05-13_20-02-53.png)

![生产案例2](./img/Snipaste_2023-05-13_20-04-57.png)

#### 分组

![说明1](./img/Snipaste_2023-05-13_19-54-34.png)

![说明2](./img/Snipaste_2023-05-13_19-58-04.png)

![原理](./img/Snipaste_2023-05-13_19-56-32.png)

#### 持久化

没接收到会捡起来继续消费



### SpringCloud Sleuth 分布式请求链路跟踪

#### 问题

![问题1](./img/Snipaste_2023-05-13_20-29-43.png)

![问题2](./img/Snipaste_2023-05-13_20-30-48.png)

#### 解决

![解决](./img/Snipaste_2023-05-13_20-39-57.png)

#### zipkin

![zipkin](./img/Snipaste_2023-05-13_20-49-05.png)

![zipkin2](./img/Snipaste_2023-05-13_20-50-32.png)

##### 名词解释

![名词解释](./img/Snipaste_2023-05-13_20-51-43.png)



### SpringCloud Alibaba 入门简介

[部分停止维护](https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now)

#### 维护模式

![维护模式](./img/Snipaste_2023-05-13_21-16-16.png)

![维护模式2](./img/Snipaste_2023-05-13_21-17-24.png)

#### 是什么

![是什么](./img/Snipaste_2023-05-13_21-25-26.png)

#### 能干嘛

![能干嘛](./img/Snipaste_2023-05-13_21-26-45.png)

#### 怎么玩

![怎么玩](./img/Snipaste_2023-05-13_21-37-03.png)

[官方文档](https://github.com/alibaba/spring-cloud-alibaba/blob/2022.x/README-zh.md)



### SpringCloud Alibaba Nacos服务注册和配置中心

#### 是什么

![是什么](./img/Snipaste_2023-05-13_21-43-08.png)

#### 注册中心比较

![比较](./img/Snipaste_2023-05-13_21-57-51.png)

#### 详细说明

##### 全景图

![全景图](./img/1533045871534-e64b8031-008c-4dfc-b6e8-12a597a003fb.png)

##### Nacos和CAP

![说明1](./img/Snipaste_2023-05-13_23-45-57.png)

![说明2](./img/Snipaste_2023-05-13_23-45-04.png)

##### Nacos支持AP和CP模式的切换

![切换](./img/Snipaste_2023-05-13_23-47-35.png)

##### bootstrap和application

![两个](./img/Snipaste_2023-05-13_23-50-34.png)

##### 自动更新配置

![自动更新](./img/Snipaste_2023-05-13_23-53-33.png)

##### 官方集成文档

[官方文档](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

##### 小总结

![小总结](./img/Snipaste_2023-05-13_23-57-49.png)

##### 多环境

![多环境](./img/Snipaste_2023-05-14_00-02-13.png)

###### 设计

![设计](./img/Snipaste_2023-05-14_07-16-57.png)

##### 持久化

[集群持久化官网文档](https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html)

###### 架构图

![架构图](./img/deployDnsVipMode.jpg)

![实际](./img/Snipaste_2023-05-14_07-51-30.png)

###### 说明

[多模式官网说明](https://nacos.io/zh-cn/docs/deployment.html)

![说明1](./img/Snipaste_2023-05-14_07-52-46.png)

![说明2](./img/Snipaste_2023-05-14_07-54-46.png)

##### 修改脚本

![需要修改](./img/Snipaste_2023-05-14_08-25-12.png)

###### 思考

![思考](./img/Snipaste_2023-05-14_08-26-52.png)

###### 修改内容

![修改内容1](./img/Snipaste_2023-05-14_08-28-20.png)

![修改内容2](./img/Snipaste_2023-05-14_08-29-28.png)

###### 执行方式

![执行方式](./img/Snipaste_2023-05-14_08-35-38.png)

###### nginx

![nginx1](./img/Snipaste_2023-05-14_08-48-30.png)

![nginx2](./img/Snipaste_2023-05-14_08-46-09.png)

#### 高可用小总结

![高可用小总结](./img/Snipaste_2023-05-14_08-52-49.png)



### SpringCloud Alibaba Sentinel实现熔断与限流

#### Hystrix和Sentinel比较

![比较](./img/Snipaste_2023-05-14_09-01-10.png)

#### 特征

![特征](./img/Snipaste_2023-05-14_09-04-18.png)

![是什么](./img/Snipaste_2023-05-14_09-05-39.png)

#### 使用

[官方文档](https://spring-cloud-alibaba-group.github.io/github-pages/2021/en-us/index.html#_spring_cloud_alibaba_sentinel)

![说明1](./img/Snipaste_2023-05-14_10-01-05.png)

#### 限流

##### 说明

![说明1](./img/Snipaste_2023-05-14_10-08-41.png)

![说明2](./img/Snipaste_2023-05-14_10-09-36.png)

##### 限流

![限流1](./img/Snipaste_2023-05-14_10-41-20.png)

##### 关联

![是什么](./img/Snipaste_2023-05-14_10-43-03.png)

##### 预热

![说明1](./img/Snipaste_2023-05-14_10-51-30.png)

![说明2](./img/Snipaste_2023-05-14_10-53-32.png)

[官方文档说明](https://github.com/alibaba/Sentinel/wiki/%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6)

##### 配置

![配置](./img/Snipaste_2023-05-14_10-57-15.png)

##### 应用场景

![场景](./img/Snipaste_2023-05-14_10-58-52.png)

![说明](./img/Snipaste_2023-05-14_11-15-43.png)

#### 降级

##### 基本介绍

![介绍](./img/Snipaste_2023-05-14_11-19-33.png)

![介绍2](./img/Snipaste_2023-05-14_11-20-33.png)

##### 说明

![说明](./img/Snipaste_2023-05-14_11-21-47.png)

##### 半开

![半开](./img/Snipaste_2023-05-14_11-22-43.png)

##### RT

![RT](./img/Snipaste_2023-05-14_11-24-39.png)

###### 是什么

![是什么](./img/Snipaste_2023-05-14_12-14-19.png)

###### 结论

![结论](./img/Snipaste_2023-05-14_11-49-04.png)

##### 异常比例

###### 是什么

![是什么](./img/Snipaste_2023-05-14_12-15-17.png)

###### 结论

![结论](./img/Snipaste_2023-05-14_11-54-19.png)

##### 异常数

###### 是什么

![是什么](./img/Snipaste_2023-05-14_12-16-03.png)

##### 热点参数限流

###### 复习

![复习](./img/Snipaste_2023-05-14_12-20-51.png)

##### 参数例外项

![例外项](./img/Snipaste_2023-05-14_12-55-11.png)

###### 异常

![异常](./img/Snipaste_2023-05-14_12-57-59.png)

##### 系统自适应限流

![配置参数说明](./img/Snipaste_2023-05-14_13-00-41.png)

##### SentinelResource

###### 需要兜底的问题

![面临的问题](./img/Snipaste_2023-05-14_13-07-23.png)

![兜底说明](./img/Snipaste_2023-05-14_14-13-13.png)

##### 更多

![更多说明](./img/Snipaste_2023-05-14_14-15-40.png)

![多说一句](./img/Snipaste_2023-05-14_14-16-48.png)

###### 核心API

![核心API](./img/Snipaste_2023-05-14_14-18-29.png)

##### 集成Ribbon

![结构](./img/Snipaste_2023-05-14_14-24-16.png)

###### 代码结构

![代码](./img/Snipaste_2023-05-14_14-34-00.png)

###### 结论

![结论](./img/Snipaste_2023-05-14_14-32-13.png)

###### 图说

![图说](./img/Snipaste_2023-05-14_14-34-50.png)

![配置](./img/Snipaste_2023-05-14_14-49-59.png)

##### 集成Feign

![业务接口](./img/Snipaste_2023-05-14_14-57-53.png)

![兜底接口](./img/Snipaste_2023-05-14_14-58-20.png)

![实现](./img/Snipaste_2023-05-14_14-59-54.png)

##### 熔断框架比较

![比较](./img/Snipaste_2023-05-14_14-55-49.png)

##### 持久化

![是什么](./img/Snipaste_2023-05-14_15-04-26.png)

###### 关键配置

![配置](./img/Snipaste_2023-05-14_15-07-47.png)

![nacos配置](./img/Snipaste_2023-05-14_15-10-31.png)

![内容解析](./img/Snipaste_2023-05-14_15-11-29.png)



### SpringCloud Alibaba Seata处理分布式事务

#### 分布式问题

![说明1](./img/Snipaste_2023-05-14_15-17-55.png)

![分布式之后](./img/Snipaste_2023-05-14_15-19-27.png)

![一句话](./img/Snipaste_2023-05-14_15-20-42.png)

#### 解决方案

![解决方案](./img/Snipaste_2023-05-14_15-35-05.png)

[官方文档](https://seata.io/zh-cn/index.html)

##### 分布式过程

![过程](./img/Snipaste_2023-05-14_15-38-08.png)

###### 一

![一加三](./img/Snipaste_2023-05-14_15-38-52.png)

###### 三

![三](./img/Snipaste_2023-05-14_15-41-02.png)

![三2](./img/Snipaste_2023-05-14_15-41-43.png)

#### 处理过程

![处理过程](./img/Snipaste_2023-05-14_15-45-50.png)

##### 怎么玩

![怎么玩1](./img/Snipaste_2023-05-14_16-13-33.png)

![解决方案](./img/Snipaste_2023-05-14_16-14-36.png)

![专注业务](./img/Snipaste_2023-05-14_16-42-35.png)

#### 业务说明

![业务说明](./img/Snipaste_2023-05-14_16-44-00.png)

##### 建库效果

![建库](./img/Snipaste_2023-05-14_17-05-21.png)

##### 代码

![代码](./img/Snipaste_2023-05-14_18-17-18.png)

#### 补充

![Seata](./img/Snipaste_2023-05-14_18-19-47.png)

##### 再看三大组件

![再看三大组件](./img/Snipaste_2023-05-14_18-22-21.png)

![三大组件2](./img/Snipaste_2023-05-14_18-24-29.png)

##### 分布式事务执行流程

![流程](./img/Snipaste_2023-05-14_18-25-26.png)

##### 如何对业务无侵入

![无侵入](./img/Snipaste_2023-05-14_18-28-20.png)

###### 是什么

![是什么](./img/Snipaste_2023-05-14_18-29-38.png)

###### 一阶段加载

![一阶段加载](./img/Snipaste_2023-05-14_18-30-40.png)

###### 二阶段提交

![二阶段提交](./img/Snipaste_2023-05-14_18-32-28.png)

###### 二阶段回滚

![二阶段回滚](./img/Snipaste_2023-05-14_18-33-47.png)

###### 补充

![补充](./img/Snipaste_2023-05-14_18-40-41.png)

![buchong-all](./img/buchong-all.png)

### 面试杂谈

![谈1](./img/Snipaste_2023-05-14_18-47-44.png)











