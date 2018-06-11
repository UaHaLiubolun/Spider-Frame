package com.chinamcloud.spider.model;


import org.bson.types.ObjectId;

public class MSite extends Site{

    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
