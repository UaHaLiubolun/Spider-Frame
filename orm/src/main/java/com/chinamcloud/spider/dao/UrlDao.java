package com.chinamcloud.spider.dao;


import com.chinamcloud.spider.model.MUrl;
import com.chinamcloud.spider.model.Url;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UrlDao extends Dao{

    private MongoCollection<MUrl> collection = getCollection("url", MUrl.class);

    public boolean addUrl(MUrl url) {
        try {
            collection.insertOne(url);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MUrl> getAll() {
        List<MUrl> urls = new ArrayList<>();
        try {
            MongoCursor<MUrl> urlMongoCursor = collection.find().iterator();
            while (urlMongoCursor.hasNext()) {
                urls.add(urlMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }

}
