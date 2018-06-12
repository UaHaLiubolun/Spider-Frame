package com.chinamcloud.spider.model;


import java.io.Serializable;
import java.util.Map;

public class Task implements Serializable{

    private String taskId;

    private Site site;

    private Request request;

    private boolean success = false;

    private int retryCount = 0;

    public void success() {success = true;}

    public void error() {retryCount++;}

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }


    public boolean isSuccess() {
        return success;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
