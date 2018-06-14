package com.chinamcloud.spider.pipeline;


import com.chinamcloud.spider.dao.MapDao;
import us.codecraft.webmagic.Task;

import java.util.Map;

public class MongoPipeline implements ObjectPipeline{

    private MapDao objectDao;

    @Override
    public void process(Object o, Task task) {
        if (objectDao == null) {
            objectDao = new MapDao(task.getUUID());
        }
        objectDao.add((Map) o);
    }
}
