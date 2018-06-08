package com.chinamcloud.spider.scheduler.core;


import com.chinamcloud.spider.model.Task;

public interface Scheduler {


    void push(Task task);

    Task poll();

}
