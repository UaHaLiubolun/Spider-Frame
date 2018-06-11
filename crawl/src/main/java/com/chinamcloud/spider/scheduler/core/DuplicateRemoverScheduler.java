package com.chinamcloud.spider.scheduler.core;


import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.scheduler.HashSetDuplicateRemover;
import com.chinamcloud.spider.scheduler.QueueScheduler;


public class DuplicateRemoverScheduler implements DuplicateRemover, Scheduler {

    private Scheduler scheduler;

    private DuplicateRemover duplicateRemover;

    public static DuplicateRemoverScheduler get() {
        return new DuplicateRemoverScheduler();
    }

    public DuplicateRemoverScheduler setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public DuplicateRemoverScheduler setDuplicateRemover(DuplicateRemover duplicateRemover) {
        this.duplicateRemover = duplicateRemover;
        return this;
    }

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
        if(task.getRequest().isDuplicate()) {
            if (!isDuplicate(task)) scheduler.push(task);
        } else scheduler.push(task);
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
