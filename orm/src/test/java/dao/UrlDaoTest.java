package dao;


import com.chinamcloud.spider.dao.SiteDao;
import com.chinamcloud.spider.dao.UrlDao;
import com.chinamcloud.spider.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UrlDaoTest {

    public UrlDao urlDao;
    public SiteDao siteDao;

    {
        urlDao = new UrlDao();
        siteDao = new SiteDao();
    }

    public MSite getSite() {
        MSite site = new MSite();
        site.setRetryCount(10);
        site.setDomain("news.sina.com.cn");
        site.setProxy(false);
        site.setTimeOut(10000);
        site.setSiteId("test");
        return site;
    }

    public MUrl getUrl() {
        MUrl mUrl = new MUrl();
        mUrl.setUrlId("testUrl");
        mUrl.setSiteId("test");
        Request request = new Request();
        request.setUrl("http://news.sina.com.cn/c/2018-06-11/doc-ihcufqif6793241.shtml");
        Rule rule = new Rule();
        DataRule dataRule = new DataRule();
        Map<String, String> map = new HashMap<>();
        map.put("content", "div[class='article']");
        dataRule.setRules(map);
        rule.setDataRules(new ArrayList<>(Arrays.asList(dataRule)));
        mUrl.setRule(rule);
        mUrl.setRequest(request);
        return mUrl;
    }

    public static void main(String[] args) {
        UrlDaoTest urlDaoTest = new UrlDaoTest();
        urlDaoTest.siteDao.addSite(urlDaoTest.getSite());
        urlDaoTest.urlDao.addUrl(urlDaoTest.getUrl());
    }


}
