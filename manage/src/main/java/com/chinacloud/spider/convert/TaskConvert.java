package com.chinacloud.spider.convert;


import com.chinamcloud.spider.dao.SiteDao;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.model.Url;
import com.chinamcloud.spider.util.EncrpytUtil;

public class TaskConvert {


    public static Task convert(Url url) {
        Task task = new Task();
        SiteDao siteDao = new SiteDao();
        task.setSite(siteDao.getSiteById(url.getSiteId()));
        task.setRequest(url.getRequest());
        task.setRule(url.getRule());
        task.setTaskId(EncrpytUtil.md5(url.getRequest().getUrl()));
        return task;
    }

}
