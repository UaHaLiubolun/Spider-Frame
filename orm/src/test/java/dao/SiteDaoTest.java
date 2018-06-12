package dao;


import com.chinamcloud.spider.dao.SiteDao;
import com.chinamcloud.spider.model.DataRule;
import com.chinamcloud.spider.model.Extract;
import com.chinamcloud.spider.model.MSite;

import java.util.Arrays;

public class SiteDaoTest {

    private SiteDao siteDao;

    {
        siteDao = new SiteDao();
    }

    private MSite getSite() {
        MSite mSite = new MSite();
        mSite.setSiteId("test");
        mSite.setDomain("news.sina.com.cn");
        mSite.setTargetUrl("http://news.sina.com.cn/\\w+/2018-\\d+-\\d+/\\w+.*");
        mSite.setHelpUrl(Arrays.asList("http://news.sina.com.cn/\\w+.*"));
        mSite.setDataRules(Arrays.asList(getDataRule()));
        mSite.setStartUrl("http://news.sina.com.cn");
        return mSite;
    }

    private DataRule getDataRule() {
        DataRule dataRule = new DataRule();
        dataRule.setCollection("news.sina");
        Extract extract = new Extract();
        extract.setExpression("//div[@class='article']/tidyText()");
        extract.setFiled("content");
        Extract extract1 = new Extract();
        extract1.setFiled("title");
        extract1.setExpression("//h1[@class='main-title']/text()");
        dataRule.setExtracts(Arrays.asList(extract, extract1));
        return dataRule;
    }

    public static void main(String[] args) {
        SiteDaoTest siteDaoTest = new SiteDaoTest();
        siteDaoTest.siteDao.addSite(siteDaoTest.getSite());
    }
}
