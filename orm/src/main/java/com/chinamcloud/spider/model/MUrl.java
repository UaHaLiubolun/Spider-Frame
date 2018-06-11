package com.chinamcloud.spider.model;


import org.bson.types.ObjectId;

public class MUrl extends Url{

    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
