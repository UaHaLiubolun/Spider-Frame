package com.chinamcloud.spider;

import com.chinamcloud.spider.dao.RunInfoDao;
import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.model.SpiderRunInfo;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.pipeline.MongoPipeline;
import com.chinamcloud.spider.scheduler.RedisScheduler;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import java.util.Date;
import java.util.List;


public class SpiderTask implements Runnable{

    private Site site;

    private PageModel pageModel;

    private int threadNum;

    private String[] urls;

    private SiteTaskDao siteTaskDao;

    {
        siteTaskDao = new SiteTaskDao();
    }

    public SpiderTask(Site site, PageModel pageModel, int threadNum, List<String> urls) {
        this.site = site;
        this.pageModel = pageModel;
        this.threadNum = threadNum;
        this.urls = urls.toArray(new String[urls.size()]);
    }

    @Override
    public void run() {
        if (beforeRun()) {
            Spider spider = ConfigSpider
                    .create(site, new MongoPipeline(), pageModel)
                    .setScheduler(new RedisScheduler(RedisPool.jedisPool()))
                    .thread(threadNum)
                    .addUrl(urls);
            // 同步任务爬取
            spider.run();
            afterRun(spider);
        }
    }


    private boolean beforeRun() {
        return siteTaskDao.startRun(site.getDomain(), true);
    }


    private void afterRun(Spider spider) {
        siteTaskDao.endRun(site.getDomain());
        SpiderRunInfo spiderRunInfo = new SpiderRunInfo(
                site.getDomain(),
                spider.getPageCount(),
                spider.getStartTime(),
                new Date()
        );
        RunInfoDao runInfoDao = new RunInfoDao();
        runInfoDao.add(spiderRunInfo);
    }
}

