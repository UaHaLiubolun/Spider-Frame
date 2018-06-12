package com.chinacloud.spider;


import com.alibaba.fastjson.JSON;
import com.chinacloud.spider.convert.TaskConvert;
import com.chinamcloud.spider.dao.UrlDao;
import com.chinamcloud.spider.model.MUrl;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.redis.RedisUtil;

import java.util.List;

public class Manage {

    private UrlDao urlDao;

    {
        urlDao = new UrlDao();
    }

    public static void main(String[] args) {
        Manage manage = new Manage();
        List<MUrl> mUrls = manage.urlDao.getAll();
        mUrls.stream().forEach(
                mUrl -> {
                    Task task = TaskConvert.convert(mUrl);
                    for (int i = 0; i < 1; i++) {
                        RedisUtil.pushList("spider-queue", JSON.toJSONString(task));
                    }
                }
        );
    }
}
