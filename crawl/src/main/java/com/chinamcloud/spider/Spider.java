package com.chinamcloud.spider;

import com.chinamcloud.spider.downloader.Downloader;
import com.chinamcloud.spider.downloader.httpClient.HttpClientDownloader;
import com.chinamcloud.spider.filter.DefaultFilter;
import com.chinamcloud.spider.filter.PageFilter;
import com.chinamcloud.spider.model.*;
import com.chinamcloud.spider.scheduler.core.DuplicateRemoverScheduler;
import com.chinamcloud.spider.thread.CountableThreadPool;
import com.chinamcloud.spider.util.TaskUtil;
import java.util.Date;
import java.util.List;


public class Spider implements Runnable{

    private Downloader downloader;

    private DuplicateRemoverScheduler scheduler;

    private int threadNum;

    private CountableThreadPool threadPool;

    private Date startTime;

    public Spider(int threadNum) {
        this.threadNum = threadNum;
    }

    public Spider setScheduler(DuplicateRemoverScheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    private void initComponent() {
        // 默认是用HttpClient
        if (downloader == null) this.downloader = new HttpClientDownloader();
        downloader.setThread(threadNum);
        if (threadPool == null) {
            threadPool = new CountableThreadPool(threadNum);
        }
        if (scheduler == null) {
            scheduler = new DuplicateRemoverScheduler();
        }
        startTime = new Date();
    }

    public void run() {
        initComponent();
        while (!Thread.currentThread().isInterrupted()) {
            final Task task = scheduler.poll();
            if (task == null && threadPool.getThreadAlive().get() == 0) {
//                threadPool.shutdown();
//                // 判断是否时停止
//                break;
            } else {
                threadPool.execute(() -> {
                    try {
                        if (task == null) return;
                        if (task.getSite().getSleep() != 0) Thread.sleep(task.getSite().getSleep());
                        processTask(task);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                });
            }
        }
    }


    private void processTask(Task task) {
        Page page = null;
        while (!task.isSuccess() && task.getRetryCount() <= task.getSite().getRetryCount()) {
            page = downloader.download(task);
        }
        getFilter().filter(task, page);
        addTask(task, page);
    }


    private PageFilter getFilter() {
        return new DefaultFilter();
    }

    private void addTask(Task task, Page page) {
        List<Request> requests = page.getRequests();
        if (requests == null || requests.size() == 0) return;
        requests.stream().forEach(
                request -> {
                    Task task1  = generatorTask(request, task);
                    scheduler.push(task1);
                }
        );
    }

    private Task generatorTask(Request request, Task task) {
        Task task1 = new Task();
        task1.setTaskId(TaskUtil.generatorId(request.getUrl()));
        task1.setSite(task.getSite());
        task1.setRequest(request);
        return task1;
    }

}




