package com.chinamcloud.spider.model;


import org.bson.types.ObjectId;
import us.codecraft.webmagic.Site;

import java.util.List;

public class SiteTask {

    private ObjectId id;

    private List<String> startUrls;

    // 线程池大小
    private int threadNum = 1;
    // 间隔时间
    private int interval = 60 * 60;
    // Site
    private Site site;
    // 规则
    private List<PageModel> pageModels;
    // 上一次执行时间
    private int lastTime;
    // 是否启用
    private boolean isOn = true;
    // 是否正在运行
    private boolean isRun = false;

    public List<String> getStartUrls() {
        return startUrls;
    }

    public void setStartUrls(List<String> startUrls) {
        this.startUrls = startUrls;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<PageModel> getPageModels() {
        return pageModels;
    }

    public void setPageModels(List<PageModel> pageModels) {
        this.pageModels = pageModels;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
}
