package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.model.MSite;
import com.chinamcloud.spider.model.Site;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;


public class SiteDao extends Dao{


    private MongoCollection<MSite> collection;

    {
        collection = getCollection("site", MSite.class);
    }

    public boolean addSite(MSite site) {
        try {
            collection.insertOne(site);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MSite> getAll() {
        MongoCursor<MSite> mSiteMongoCursor = collection.find().iterator();
        List<MSite> mSites = new ArrayList<>();
        while (mSiteMongoCursor.hasNext()) {
            mSites.add(mSiteMongoCursor.next());
        }
        return mSites;
    }

    public Site getSiteById(String id) {
        try {
            MSite site = collection.find(eq("siteId", id)).first();
            return site;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
