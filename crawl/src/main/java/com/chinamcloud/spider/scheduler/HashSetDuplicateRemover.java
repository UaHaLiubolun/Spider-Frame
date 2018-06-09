package com.chinamcloud.spider.scheduler;


import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.scheduler.core.DuplicateRemover;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashSet去重
 */
public class HashSetDuplicateRemover implements DuplicateRemover {

    private Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    @Override
    public boolean isDuplicate(Task task) {
        return !urls.add(task.getRequest().getUrl());
    }

}
