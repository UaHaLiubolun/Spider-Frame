package com.chinamcloud.spider.redis;


import redis.clients.jedis.Jedis;

public class RedisUtil {

    private static Jedis jedis;

    static {
        jedis = RedisPool.getJedis();
    }

    public static void pushList(String name, String value) {
        jedis.rpush(name, value);
    }


}
