package com.chinamcloud.spider.downloader;

import com.chinamcloud.spider.model.Page;
import com.chinamcloud.spider.model.Task;

public interface Downloader {

    Page download(Task task);

    void setThread(int threadNum);

}
