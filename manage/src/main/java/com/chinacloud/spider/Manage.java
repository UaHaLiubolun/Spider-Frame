package com.chinacloud.spider;


import com.alibaba.fastjson.JSON;
import com.chinacloud.spider.convert.TaskConvert;
import com.chinamcloud.spider.dao.SiteDao;
import com.chinamcloud.spider.model.MSite;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.redis.RedisUtil;

import java.util.List;

public class Manage {

    private SiteDao siteDao;

    {
        siteDao = new SiteDao();
    }

    public static void main(String[] args) {
        Manage manage = new Manage();
        List<MSite> mSites = manage.siteDao.getAll();
        mSites.stream().forEach(
                mSite -> {
                    Task task = TaskConvert.convert(mSite);
                    for (int i = 0; i < 1; i++) {
                        RedisUtil.pushList("spider-queue", JSON.toJSONString(task));
                    }
                }
        );
    }
}
