package com.chinamcloud.spider.pipeline;


import com.chinamcloud.spider.orm.dao.MapDao;
import us.codecraft.webmagic.Task;

import java.util.Date;
import java.util.Map;

public class MongoPipeline implements ObjectPipeline{

    private MapDao objectDao;

    @Override
    public void process(Object o, Task task) {
        if (objectDao == null) {
            objectDao = new MapDao(task.getUUID());
        }
        Map map = (Map) o;
        map.put("createdAt", new Date().getTime());
        //TODO 语言暂时写死
        map.put("langType", "ch");
        objectDao.add((Map) o);
    }
}
