package com.arm.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author zhaolangjing
 * <p>
 * 1.redis 哨兵模式无法使用单机模式链接 会报错： ERR Client sent AUTH, but no password is set
 */
public class RedisTest {

    public static void main(String[] args) {
        // sentinel();
        single();
    }

    /**
     * 单机模式
     */
    public static void single() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "172.16.9.59", 26370,
                2000, "wisesoft", false);
        System.out.println("name:" + pool.getResource().get("name"));
    }

    /**
     * 哨兵模式测试
     */
    public static void sentinel() {
        String serverNames = "172.16.9.59:26370,172.16.9.59:26370,172.16.9.59:26370";
        String master = "redismaster";
        String password = "wisesoft";

        Pool<Jedis> pool = new JedisSentinelPool(master, new HashSet<>(Arrays.asList(serverNames.split(","))),
                new JedisPoolConfig(), 2000, 2000, password, 10);
        System.out.println("name:" + pool.getResource().get("name"));
    }

}
