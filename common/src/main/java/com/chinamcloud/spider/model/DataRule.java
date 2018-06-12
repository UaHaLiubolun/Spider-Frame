package com.chinamcloud.spider.model;


import java.util.List;

public class DataRule {

    private String collection;

    private List<Extract> extracts;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public List<Extract> getExtracts() {
        return extracts;
    }

    public void setExtracts(List<Extract> extracts) {
        this.extracts = extracts;
    }
}
