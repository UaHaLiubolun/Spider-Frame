package com.chinamcloud.spider.model;


import com.chinamcloud.spider.selector.Html;
import com.chinamcloud.spider.selector.Json;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page implements Serializable{

    private static final long serialVersionUID = 5659170945717023123L;

    private String rawText;

    private Document document;

    private Html html;

    private Json json;

    private List<Request> requests;


    public Json getJson() {
        if (json == null) {
            json = new Json(rawText);
        }
        return json;
    }

    public Html getHtml() {
        if (html == null) {
            html = new Html(rawText);
        }
        return html;
    }

    public void addRequest(Request request) {
        if (requests == null) {
            requests = new ArrayList<>();
        }
        requests.add(request);
    }

    public List<Request> getRequests() {
        return requests;
    }

    public Document getDocument() {
        return Jsoup.parse(rawText);
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
