package com.chinamcloud.spider.thread;

import java.util.concurrent.atomic.AtomicInteger;

public interface ThreadPool {

    void execute(final Runnable runnable);

    void shutdown();

    AtomicInteger getThreadAlive();


}
