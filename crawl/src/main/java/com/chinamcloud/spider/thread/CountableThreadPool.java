package com.chinamcloud.spider.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CountableThreadPool implements ThreadPool{

    private int threadNum;

    private AtomicInteger threadAlive = new AtomicInteger();

    private ReentrantLock reentrantLock = new ReentrantLock();

    private Condition condition = reentrantLock.newCondition();

    private ExecutorService executorService;

    public CountableThreadPool(int threadNum) {
        this.threadNum = threadNum;
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    @Override
    public void execute(final Runnable runnable) {
        if (threadAlive.get() >= threadNum) {
            try {
                reentrantLock.lock();
                while (threadAlive.get() >= threadNum) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        threadAlive.incrementAndGet();
        executorService.execute(() ->  {
            try {
                runnable.run();
            } finally {
                try {
                    reentrantLock.lock();
                    threadAlive.decrementAndGet();
                    condition.signal();
                } finally {
                    reentrantLock.unlock();
                }
            }
        });
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public AtomicInteger getThreadAlive() {
        return threadAlive;
    }
}
