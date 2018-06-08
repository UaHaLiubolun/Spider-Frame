package com.chinamcloud.spider.scheduler.core;


import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.scheduler.HashSetDuplicateRemover;
import com.chinamcloud.spider.scheduler.QueueScheduler;


public class DuplicateRemoverScheduler implements DuplicateRemover, Scheduler {

    private Scheduler scheduler;

    private DuplicateRemover duplicateRemover;

    public DuplicateRemoverScheduler(Scheduler scheduler, DuplicateRemover duplicateRemover) {
        this.scheduler = scheduler;
        this.duplicateRemover = duplicateRemover;
    }

    public DuplicateRemoverScheduler() {
        this.duplicateRemover = new HashSetDuplicateRemover();
        this.scheduler = new QueueScheduler();
    }

    @Override
    public void push(Task task) {
        if (!isDuplicate(task)) scheduler.push(task);
    }

    @Override
    public Task poll() {
        return scheduler.poll();
    }


    @Override
    public boolean isDuplicate(Task task) {
        return duplicateRemover.isDuplicate(task);
    }
}
