package com.chinamcloud.spider.scheduler;


import com.alibaba.fastjson.JSON;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.redis.RedisPool;
import com.chinamcloud.spider.scheduler.core.Scheduler;
import redis.clients.jedis.Jedis;

public class RedisQueueScheduler implements Scheduler{

    private static String QUEUE_PREFIX = "queue";

    @Override
    public void push(Task task) {
        Jedis jedis = RedisPool.getJedis();
        try {
            jedis.rpush(QUEUE_PREFIX, JSON.toJSONString(task));
        } finally {
            RedisPool.returnResource(jedis);
        }
    }

    @Override
    public synchronized Task poll() {
        Jedis jedis = RedisPool.getJedis();
        try {
            String json = jedis.lpop(QUEUE_PREFIX);
            if (json != null) {
                return JSON.parseObject(json, Task.class);
            }
        } finally {
            RedisPool.returnResource(jedis);
        }
        return null;
    }
}
