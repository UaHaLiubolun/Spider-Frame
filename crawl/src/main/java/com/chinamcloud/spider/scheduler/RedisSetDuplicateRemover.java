package com.chinamcloud.spider.scheduler;


import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.redis.RedisPool;
import com.chinamcloud.spider.scheduler.core.DuplicateRemover;
import redis.clients.jedis.Jedis;

public class RedisSetDuplicateRemover implements DuplicateRemover {


    private static String SET_PREFIX = "set";

    @Override
    public boolean isDuplicate(Task task) {
        Jedis jedis = RedisPool.getJedis();
        try {
            return jedis.sadd(SET_PREFIX, task.getTaskId()) == 0;
        } finally {
            RedisPool.returnResource(jedis);
        }
    }
}
