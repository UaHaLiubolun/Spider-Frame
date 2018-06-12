package com.chinacloud.spider.convert;


import com.chinamcloud.spider.model.MSite;
import com.chinamcloud.spider.model.Request;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.util.EncrpytUtil;

public class TaskConvert {


    public static Task convert(MSite mSite) {
        Task task = new Task();
        task.setSite(mSite);
        Request request = new Request();
        request.setUrl(mSite.getStartUrl());
        task.setRequest(request);
        task.setTaskId(EncrpytUtil.md5(request.getUrl()));
        return task;
    }

}
