package com.chinamcloud.spider.model;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;

public class Page implements Serializable{

    private static final long serialVersionUID = 5659170945717023123L;

    private Task task;

    private String rawText;

    private Document document;

    public Document getDocument() {
        return Jsoup.parse(rawText);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
