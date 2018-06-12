package com.chinamcloud.spider.dao;


import com.mongodb.client.MongoCollection;

import java.util.Map;

public class MapDao extends Dao{

    private MongoCollection<Map> collection;


    public MapDao(String collectionName) {
        this.collection = getCollection(collectionName, Map.class);
    }

    public boolean add(Map map) {
        try {
            collection.insertOne(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
