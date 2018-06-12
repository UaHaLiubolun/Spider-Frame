package com.chinamcloud.spider.filter;

import com.chinamcloud.spider.dao.MapDao;
import com.chinamcloud.spider.model.*;
import com.chinamcloud.spider.selector.Selectable;
import com.chinamcloud.spider.util.ValidateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultFilter implements PageFilter {


    @Override
    public void filter(Task task, Page page) {
        Rule rule = task.getRule();

        //判断是否自定义filter
        if (rule.getFilter() != null && !rule.getFilter().equals("")) {
            // todo 非必要功能后面添加
        } else {
            dataRule(rule.getDataRules(), page);
            urlUrl(rule.getUrlRules(), page);
        }
    }


    private void dataRule(List<DataRule> rules, Page page) {
        if (rules == null || rules.size() == 0 ) return;
        for (int i = 0; i < rules.size(); i++) {
            DataRule dataRule = rules.get(i);
            Map<String, Object> map = getExtracts(dataRule.getExtracts(), page);
            MapDao mapDao = new MapDao(dataRule.getCollection());
            mapDao.add(map);
        }
    }

    private void urlUrl(List<UrlRule> rules, Page page) {
        for (int i = 0; i < rules.size(); i++) {
            UrlRule urlRule = rules.get(i);
            if (urlRule.getUrl().getNum() == -1) {
                List<String> urls = (List<String>) getExtract(urlRule.getUrl(), page);
                if (ValidateUtil.notNull(urls)) {
                    addRequest(urlRule.getUrlId(), urls, page);
                }
            } else {
                String url = (String) getExtract(urlRule.getUrl(), page);
                if (ValidateUtil.notNull(url)) {
                    addRequest(urlRule.getUrlId(), url, page);
                }
            }
        }
    }

    private void addRequest(String urlId, List<String> strings, Page page) {
        strings.stream().forEach((string) -> addRequest(urlId, string, page));
    }

    private void addRequest(String urlId, String url, Page page) {
        Request request = new Request();
        request.setUrl(url);
        request.setUrlId(urlId);
        page.addRequest(request);
    }

    private Map<String, Object> getExtracts(List<Extract> extracts, Page page) {
        Map<String, Object> result = new HashMap<>();
        extracts.stream().forEach(extract -> {
            Object value = getExtract(extract, page);
            if (ValidateUtil.notNull(value)) {
                System.out.println(value);
                result.put(extract.getFiled(), value);
            }
        });
        return result;
    }


    private Object getExtract(Extract extract, Page page){
        Object selectable = null;
        switch (extract.getResolver().toLowerCase()) {
            case "xpath":
                selectable = xPath(extract, page);
                break;
            case "regex":
                selectable = regex(extract, page);
                break;
            case "json":
                //todo
                break;
            case "jsoup":
                break;
            case "css":
                break;
        }
        return selectable;
    }


    private Object xPath(Extract extract, Page page) {
       Selectable selectable =  page.getHtml().xpath(extract.getExpression());
       return selectable.toString();
    }


    private Object regex(Extract extract, Page page) {
        Selectable selectable =  page.getHtml().links().regex(extract.getExpression());
        if (extract.getNum() == -1) {
            return selectable.all();
        } else {
            return selectable.toString();
        }
    }




}
