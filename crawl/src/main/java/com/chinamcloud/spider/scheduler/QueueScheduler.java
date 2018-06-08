package com.chinamcloud.spider.scheduler;


import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.scheduler.core.Scheduler;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueScheduler implements Scheduler {

    private BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    @Override
    public void push(Task task) {
        queue.add(task);
    }

    @Override
    public Task poll() {
        return queue.poll();
    }
}
