package com.chinamcloud.spider;


import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.SiteTask;
import com.chinamcloud.spider.model.SpiderRunInfo;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TimerRun implements Runnable{

    @Override
    public void run() {
        System.out.println("start task");
        Runnable runnable = () -> {
                SiteTaskDao siteTaskDao = new SiteTaskDao();
                List<SiteTask> siteTaskList = siteTaskDao.getOn();
                if (siteTaskList.size() == 0) return;
                Date date = new Date();
                siteTaskList = siteTaskList.stream().filter(siteTask ->
                        (siteTask.getLastTime() + siteTask.getInterval()) < (int)(date.getTime() / 1000)
                ).collect(Collectors.toList());
                if (siteTaskList.size() == 0) return;
                siteTaskList.stream().forEach(siteTask -> {
                    siteTask.getPageModels().stream().forEach(pageModel -> {
                        Thread thread =
                                new Thread(new SpiderTask(
                                                siteTask.getSite(),
                                                pageModel,
                                                siteTask.getThreadNum(),
                                                siteTask.getStartUrls()));
                        thread.start();
                    });
                });
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        schedule.scheduleAtFixedRate(new TimerRun(), 1, 60, TimeUnit.SECONDS);
    }
}
