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

    public MUrl getUrlOne(String type) {
        MUrl mUrl = new MUrl();
        mUrl.setUrlId("shahah");
        mUrl.setSiteId("test");
        Request request = new Request();
        request.setUrl("http://news.sina.com.cn/" + type);
        Rule rule = new Rule();
        rule.setUrlRules(Arrays.asList(getUrlRule()));
        mUrl.setRule(rule);
        mUrl.setRequest(request);
        return mUrl;
    }

    public MUrl getUrl() {
        MUrl mUrl = new MUrl();
        mUrl.setUrlId("testUrl");
        mUrl.setSiteId("test");
        Request request = new Request();
        request.setUrl("http://news.sina.com.cn/c/2018-06-11/doc-ihcufqif6793241.shtml");
        Rule rule = new Rule();
        DataRule dataRule = new DataRule();
        dataRule.setCollection("news");
        Extract extract = new Extract();
        extract.setFiled("content");
        extract.setResolver("xpath");
        extract.setExpression("//div[@class='article']/tidyText()");
        Extract extractOne = new Extract();
        extractOne.setFiled("title");
        extractOne.setResolver("xpath");
        extractOne.setExpression("//h1[@class='main-title']/text()");
        dataRule.setExtracts(Arrays.asList(extract, extractOne));
        rule.setDataRules(new ArrayList<>(Arrays.asList(dataRule)));
        rule.setUrlRules(Arrays.asList(getUrlRule()));
        mUrl.setRule(rule);
        mUrl.setRequest(request);
        return mUrl;
    }

    public UrlRule getUrlRule() {
        UrlRule urlRule = new UrlRule();
        urlRule.setUrlId("testUrl");
        Extract extract = new Extract();
        extract.setResolver("regex");
        extract.setExpression("http://news.sina.com.cn/\\w+/2018-\\d+-\\d+/\\w+.*");
        extract.setNum(-1);
        urlRule.setUrl(extract);
        return urlRule;
    }

    public static void main(String[] args) {
        UrlDaoTest urlDaoTest = new UrlDaoTest();
//        urlDaoTest.siteDao.addSite(urlDaoTest.getSite());
        urlDaoTest.urlDao.addUrl(urlDaoTest.getUrl());
        urlDaoTest.urlDao.addUrl(urlDaoTest.getUrlOne("society"));
        urlDaoTest.urlDao.addUrl(urlDaoTest.getUrlOne("china"));
        urlDaoTest.urlDao.addUrl(urlDaoTest.getUrlOne("world"));


    }


}
