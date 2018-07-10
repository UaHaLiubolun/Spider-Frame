import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.Extract;
import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.model.SiteTask;
import us.codecraft.webmagic.Site;

import java.util.Arrays;
import java.util.Date;

public class SiteTaskTest {

    public static void main(String[] args) {
        SiteTaskDao siteTaskDao = new SiteTaskDao();

        SiteTask siteTaskHuPu = new SiteTask();

        siteTaskHuPu.setSite(Site.me().setSleepTime(0).setDomain("bbs.hupu.com"));

        PageModel pageModelHuPu = new PageModel();
        pageModelHuPu.setTargetUrl(Arrays.asList("https://bbs.hupu.com/\\d+.html"));
        pageModelHuPu.setHelpUrl(Arrays.asList("https://bbs.hupu.com/\\[a-z]+"));
        Extract extractHuPu = new Extract();
        extractHuPu.setFiled("title");
        extractHuPu .setValue("//title/text()");
        Extract extractHuPuContent = new Extract();
        extractHuPuContent.setFiled("content");
        extractHuPuContent .setValue("//div[@class='quote-content']/tidyText()");
        pageModelHuPu.setExtracts(Arrays.asList(extractHuPu, extractHuPuContent));
        siteTaskHuPu.setPageModels(Arrays.asList(pageModelHuPu));
        siteTaskHuPu.setLastTime((int)(new Date().getTime() / 1000) - 61);
        siteTaskHuPu.setInterval(60);
        siteTaskHuPu.setStartUrls(Arrays.asList("https://bbs.hupu.com/all-gambia"));
//        siteTaskDao.add(siteTaskHuPu);


        SiteTask siteTaskSohu = new SiteTask();
        siteTaskSohu.setSite(Site.me().setSleepTime(0).setDomain("news.sohu.com"));
        PageModel pageModel = new PageModel();
        pageModel.setTargetUrl(Arrays.asList("www.sohu.com/a/\\d+_\\d+"));
        pageModel.setHelpUrl(Arrays.asList("http://www.sohu.com/c/\\d+/\\d+", "http://w+.sohu.com"));
        Extract extract = new Extract();
        extract.setFiled("title");
        extract.setValue("//h1/text()");
        pageModel.setExtracts(Arrays.asList(extract));
        siteTaskSohu.setPageModels(Arrays.asList(pageModel));
        siteTaskSohu.setLastTime((int)(new Date().getTime() / 1000) - 61);
        siteTaskSohu.setInterval(60);
        siteTaskSohu.setStartUrls(Arrays.asList("http://news.sohu.com/"));
        siteTaskDao.add(siteTaskSohu);



        SiteTask siteTaskSina = new SiteTask();
        siteTaskSina.setSite(Site.me().setSleepTime(0).setDomain("news.sina.com.cn"));
        PageModel pageModelSina = new PageModel();
        pageModelSina.setTargetUrl(Arrays.asList("http://news.sina.com.cn/\\w+/2018-\\d+-\\d+/\\w+.*"));
        pageModelSina.setHelpUrl(Arrays.asList("http://news.sina.com.cn/\\w{3,20}/"));
        Extract extractSina = new Extract();
        extractSina.setFiled("title");
        extractSina .setValue("//h1[@class='main-title']/text()");
        pageModelSina.setExtracts(Arrays.asList(extractSina));
        siteTaskSina.setPageModels(Arrays.asList(pageModelSina));
        siteTaskSina.setLastTime((int)(new Date().getTime() / 1000) - 61);
        siteTaskSina.setInterval(60);
        siteTaskSina.setStartUrls(Arrays.asList("http://news.sina.com.cn"));
        siteTaskDao.add(siteTaskSina);

    }
}
