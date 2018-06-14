import com.chinamcloud.spider.ConfigSpider;
import com.chinamcloud.spider.model.Extract;
import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.redis.RedisPool;
import com.chinamcloud.spider.scheduler.RedisScheduler;
import us.codecraft.webmagic.Site;

import java.util.Arrays;

public class SoHuNewsTest {


    public static void main(String[] args) {

        PageModel pageModel = new PageModel();
        pageModel.setTargetUrl(Arrays.asList("www.sohu.com/a/\\d+_\\d+"));
        pageModel.setHelpUrl(Arrays.asList("http://www.sohu.com/c/\\d+/\\d+"));
        Extract extract = new Extract();
        extract.setFiled("title");
        extract.setValue("//h1/text()");
        pageModel.setExtracts(Arrays.asList(extract));
        ConfigSpider.create(Site.me().setSleepTime(0), pageModel)
                .setScheduler(new RedisScheduler(RedisPool.jedisPool()))
                .addUrl("http://news.sohu.com/")
                .start();


        PageModel pageModelSina = new PageModel();
        pageModelSina.setTargetUrl(Arrays.asList("http://news.sina.com.cn/\\w+/2018-\\d+-\\d+/\\w+.*"));
        pageModelSina.setHelpUrl(Arrays.asList("http://news.sina.com.cn/\\w{3,20}/"));
        Extract extractSina = new Extract();
        extractSina.setFiled("title");
        extractSina .setValue("//h1[@class='main-title']/text()");
        pageModelSina.setExtracts(Arrays.asList(extractSina));
        ConfigSpider.create(Site.me().setSleepTime(0), pageModelSina)
                .setScheduler(new RedisScheduler(RedisPool.jedisPool()))
                .addUrl("http://news.sina.com.cn")
                .runAsync();
    }
}
