package com.chinamcloud.spider;

import com.chinamcloud.spider.downloader.Downloader;
import com.chinamcloud.spider.downloader.httpClient.HttpClientDownloader;
import com.chinamcloud.spider.model.Page;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.scheduler.core.DuplicateRemoverScheduler;
import com.chinamcloud.spider.thread.CountableThreadPool;

import java.util.Date;

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
                threadPool.shutdown();
                // 判断是否时停止
                break;
            } else {
                threadPool.execute(() -> {
                    try {
                        if (task == null) return;
                        processTask(task);
                        if (task.getSite().getSleep() != 0) Thread.sleep(task.getSite().getSleep());
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
//        onDownloadSuccess(task, page);
    }

//    private void onDownloadSuccess(Task task, Page page) {
//        task.getRequest().getFilter().filter(page);
//        if (page.getPipelines() != null) {
//            for (Pipeline pipeline : page.getPipelines()) {
//                pipeline.save(page);
//            }
//        }
//        addPageTask(page);
//    }
//
//    private void addPageTask(Page page) {
//        if (page.getNewRequests() == null) return;
//        for (Request request : page.getNewRequests()) {
//            Task task = new Task(site, request);
//            addTask(task);
//        }
//    }
//
//    private void addTask(Task task) {
//        scheduler.push(task);
//    }
}




