package com.chinamcloud.spider.filter;


import com.chinamcloud.spider.model.Page;
import com.chinamcloud.spider.model.Task;

public interface PageFilter {

    void filter(Task task, Page page);
}
