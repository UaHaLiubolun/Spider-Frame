package com.chinamcloud.spider;

import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.pipeline.ConfigPipeline;
import com.chinamcloud.spider.pipeline.ConsolePipeLine;
import com.chinamcloud.spider.pipeline.ObjectPipeline;
import com.chinamcloud.spider.process.ConfigPageProcess;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ConfigSpider extends Spider{

    private ConfigPipeline configPipeline;

    private ConfigPageProcess configPageProcess;

    public ConfigSpider(PageProcessor pageProcessor) {
        super(pageProcessor);
    }

    public ConfigSpider(ConfigPageProcess configPageProcess) {
        super(configPageProcess);
        this.configPageProcess = configPageProcess;
    }

    public ConfigSpider(Site site, ObjectPipeline objectPipeline, PageModel... pageModels) {
        this(ConfigPageProcess.create(site, pageModels));
        this.configPipeline = new ConfigPipeline();
        this.configPipeline.put(objectPipeline);
        super.addPipeline(configPipeline);
    }


    public static ConfigSpider create(Site site, PageModel... pageModels) {
        return new ConfigSpider(site, new ConsolePipeLine(), pageModels);
    }

    public static ConfigSpider create(Site site, ObjectPipeline objectPipeline, PageModel... pageModels) {
        return new ConfigSpider(site, objectPipeline, pageModels);
    }

}
