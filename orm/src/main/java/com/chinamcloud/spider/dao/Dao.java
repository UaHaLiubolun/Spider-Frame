package com.chinamcloud.spider.dao;


import com.chinamcloud.spider.mongo.MongoJDBC;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;

public class Dao {

    private MongoDatabase mongoDatabase = MongoJDBC.getDataBase("spider");

    protected MongoCollection getCollection(String collection) {
        return mongoDatabase.getCollection(collection);
    }

    protected MongoCollection getCollection(String collection, Class c) {
        return mongoDatabase.getCollection(collection, c);
    }



}
