package com.chinamcloud.spider.dao;


import com.chinamcloud.spider.model.MUrl;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;

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

    public MUrl getById(String id) {
        MUrl mUrl = null;
        try {
            mUrl = collection.find(eq("urlId", id)).first();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mUrl;
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
