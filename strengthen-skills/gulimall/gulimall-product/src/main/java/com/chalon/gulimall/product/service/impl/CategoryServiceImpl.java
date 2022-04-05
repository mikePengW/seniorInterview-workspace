package com.chalon.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chalon.gulimall.product.entity.CategoryEntity;
import com.chalon.gulimall.product.service.CategoryService;
import com.chalon.gulimall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wei.peng
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

    /**
     * 级联更新所有相关的数据
     *
     * @param category
     * @CacheEvict：失效模式
     * 1、同时进行多种缓存操作 @Caching
     * 2、指定删除某个分区下的所有数据 @CacheEvict(value = "category",allEntries = true)
     * 3、存储同一类型的数据，都可以指定成同一个分区。分区名默认就是缓存的前缀
     */

//    @Caching(evict = {
//            @CacheEvict(value = "category", key = "'getLevel1Categorys'"),
//            @CacheEvict(value = "category", key = "'getCatalogJson'")
//    })
    // category::key
    @CacheEvict(value = "category", allEntries = true) // 失效模式
//    @CachePut // 双写模式
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
//    this.updateById(category);

        // 同时修改缓存中的数据
        // redis.del("catalogJSON");等待下次主动查询进行更新
    }

    /**
     * 1、每一个需要缓存的数据我们都来指定要放到那个名字的缓存。【缓存的分区（按照业务类型分）】
     * 2、@Cacheable({"category"})
     *      代表当前方法的结果需要缓存，如果缓存中有，方法不用调用。
     *      如果缓存中没有，会调用方法，最后将方法的结果放入缓存
     * 3、默认行为
     *      1）、如果缓存中有，方法不用调用。
     *      2）、key默认自动生成；缓存的名字::SimpleKey {}(自主生成的key值)
     *      3）、缓存的value的值。默认使用jdk序列化机制。将序列化后的数据存到redis
     *      4）、默认ttl时间 -1；
     * 4、自定义：
     *      1）、指定生成的缓存使用的key：   key属性指定。接受一个 SpEL
     *          SpEl的详细 https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache-spel-context
     *      2）、指定缓存的数据的存活时间：    配置文件中修改ttl
     *      3）、将数据保存为json格式：
     *          自定义RedisCacheConfiguration即可
     * 5、Spring-Cache的不足；
     *      1）、读模式：
     *          缓存穿透：查询一个null数据。解决：缓存空数据；cache-null-values=true
     *          缓存击穿：大量并发进来同时查询一个正好过期的数据。解决：加锁；？默认是无加锁的；sync = true (加锁，解决击穿问题)
     *          缓存雪崩：大量的key同时过期。解决：加随机时间（可能弄巧成拙）。加上过期时间即可。：spring.cache.redis.time-to-live=3600000
     *      2）、写模式：（缓存与数据库一致）
     *          1）、读写加锁（适用于读多写少，写多了一直等待也不合适）。
     *          2）、引入Canal，感知到MySQL的更新去更新数据库
     *          3）、读多写多，直接去数据库查询就行
     *      总结：
     *          常规数据（读多写少，即时性，一致性要求不高的数据）；完全可以使用Spring-Cache；写模式（只要缓存的数据有过期时间就足够了）
     *          特殊数据：特殊设计
     * 6、原理：
     *      CacheManager(RedisCacheManager)->Cache(RedisCache)->Cache负责缓存的读写
     *
     * @return
     */
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("getLevel1Categorys...");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        return null;
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        return null;
    }

    // TODO 产生堆外内存溢出：OutOfDirectMemoryError
    // 1）、springboot2.0以后默认使用lettuce作为操作redis的客户端。它使用netty进行网络通信。
    // 2）、lettuce的bug导致netty堆外内存溢出 -Xmx300m；netty如果没有指定堆外内存，默认使用 -Xmx300m
    //  可以通过-Dio.netty.maxDirectMemory进行设置
    // 解决方案：不能使用-Dio.netty.maxDirectMemory只去调大堆外内存。
    // 1）、升级lettuce客户端。（吞吐量大）     2）、切换使用jedis(好久没更新)
    // redisTemplate:
    // lettuce、jedis操作redis的底层客户端。Spring再次封装redisTemplate；
//    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson2() {
        // 给缓存中放json字符串,拿出的json字符串，还用逆转为能用的对象类型；【序列化与反序列化】

        /**
         * 1、空结果缓存：解决缓存穿透
         * 2、设置过期时间（加随机值）：解决缓存雪崩
         * 3、加锁：解决缓存击穿
         */

        // 1、加入缓存逻辑，缓存中的数据是json字符串
        // JSON跨语言，跨平台兼容。
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            // 2、缓存中没有，查询数据库
            System.out.println("缓存不命中...将要查询数据库...");
            Map<String, List<Catalog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();

            return catalogJsonFromDb;
        }

        System.out.println("缓存命中...直接返回...");
        // 转为我们指定的对象。
        Map<String, List<Catalog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        });
        return result;
    }

    /**
     * 缓存里面的数据如何和数据库保持一致
     * 缓存数据一致性
     * 1）、双写模式
     * 2）、失效模式
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

        // 1、锁的名字。锁的粒度，越细越好。
        // 锁的粒度：具体缓存的是某个数据，11-号商品；  product-11-lock product-12-lock     product-lock
        RLock lock = redisson.getLock("CatalogJson-lock");
        lock.lock();

        Map<String, List<Catalog2Vo>> dataFromDb;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            lock.unlock();
        }

        return dataFromDb;

    }

    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        // 1、占分布式锁。去redis占坑
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功...");
            // 加锁成功... 执行业务
            // 2、设置过期时间，必须和加锁是同步的，原子的
            // redisTemplate.expire("lock",30,TimeUnit.SECONDS);
            Map<String, List<Catalog2Vo>> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                // 删除锁
                Long lock1 = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class)
                        , Arrays.asList("lock"), uuid);
            }

            // 获取值对比+对比成功删除=原子操作 lua脚本解锁
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if (uuid.equals(lockValue)) {
//                // 删除我自己的锁
//                redisTemplate.delete("lock"); // 删除锁
//            }

            return dataFromDb;
        } else {
            // 加锁失败...重试。synchronized()
            // 休眠100ms重试
            System.out.println("获取分布式锁失败...等待重试");

            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }

            return getCatalogJsonFromDbWithRedisLock(); // 自旋的方式

        }

    }

    private Map<String, List<Catalog2Vo>> getDataFromDb() {
        // 得到锁以后，我们应该再去缓存中确定一次，如果没有才需要继续查询
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            // 缓存不为null直接返回
            Map<String, List<Catalog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
            return result;
        }
        System.out.println("查询了数据库......");

        Map<String, List<Catalog2Vo>> map = new HashMap<>();

        // 3、查到的数据再放入缓存，将对象转为json放在缓存中
        String s = JSON.toJSONString(map);
        redisTemplate.opsForValue().set("catalogJSON", s, 1, TimeUnit.DAYS);

        return map;
    }

    // 从数据库查询并封装分类数据
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDbWithLocalLock() {
        // 只要是同一把锁，就能锁住需要这个锁的所有线程
        // 1、synchronized (this)：SpringBoot所有的组件在容器中都是单例的。
        // TODO 本地锁：synchronized，JUC（Lock），在分布式情况下，想要锁住所有，必须使用分布式锁

        synchronized (this) {
            // 得到锁以后，我们应该再去缓存中确定一次，如果没有才需要继续查询
            return getDataFromDb();
        }

    }

}
