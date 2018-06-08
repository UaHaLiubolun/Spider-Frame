package com.chinamcloud.spider.scheduler.core;


import com.chinamcloud.spider.model.Task;

public interface DuplicateRemover {

    boolean isDuplicate(Task task);

}
