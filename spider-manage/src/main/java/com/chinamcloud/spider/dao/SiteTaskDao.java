package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.model.SiteTask;
import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;


public class SiteTaskDao extends Dao {

    private MongoCollection<SiteTask> collection;

    {
        collection = getCollection("siteTask", SiteTask.class);
    }


    public boolean add(SiteTask siteTask) {
        try {
            collection.insertOne(siteTask);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SiteTask> getOn() {
        List<SiteTask> siteTasks = new ArrayList<>();
        try {
            MongoCursor<SiteTask> taskMongoCursor =
                    collection.find(and(eq("on", true), eq("run", false))).iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }

    public boolean startRun(String domain, boolean run) {
        boolean result = false;
        try {
            UpdateResult updateResult =
                    collection.updateOne(eq("site.domain", domain), combine(set("run", run)));
            result = updateResult.getMatchedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    public boolean endRun(String domain) {
        boolean result = false;
        try {
            int now = (int)(new Date().getTime() / 1000);
            UpdateResult updateResult =
                    collection.updateOne(eq("site.domain", domain),
                            combine(
                                    set("run", false),
                                    set("lastTime", now)));
            result = updateResult.getMatchedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
