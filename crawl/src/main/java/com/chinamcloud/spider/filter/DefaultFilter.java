package com.chinamcloud.spider.filter;

import com.chinamcloud.spider.dao.MapDao;
import com.chinamcloud.spider.model.*;
import com.chinamcloud.spider.selector.Selectable;
import com.chinamcloud.spider.util.ValidateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultFilter implements PageFilter {


    @Override
    public void filter(Task task, Page page) {
        Site site = task.getSite();
        if (isTargetUrl(task)) {
            dataRule(site.getDataRules(), page);
        } else {
            addRequest(task, page);
        }
        addTarget(task, page);
    }

    private boolean isTargetUrl(Task task) {
        Pattern pattern = Pattern.compile(task.getSite().getTargetUrl());
        Matcher matcher = pattern.matcher(task.getRequest().getUrl());
        return matcher.find();
    }

    private void addTarget(Task task, Page page) {
        List<String> targetUrls = page.getHtml().links().regex(task.getSite().getTargetUrl()).all();
        addRequest(targetUrls, page, true);
    }

    private void addRequest(Task task, Page page) {
        List<String> urls = task.getSite().getHelpUrl();
        if (urls == null || urls.size() == 0) return;
        List<String> strings = new ArrayList<>();
        urls.stream().forEach(url -> {
             strings.addAll(page.getHtml().links().regex(url).all());
        });
        addRequest(strings, page, true);
    }

    private void addRequest(List<String> urls, Page page, boolean isDuplicate) {
        urls.stream().forEach(string -> {
            Request request = new Request();
            request.setUrl(string);
            request.setDuplicate(isDuplicate);
            page.addRequest(request);
        });
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


    private Map<String, Object> getExtracts(List<Extract> extracts, Page page) {
        Map<String, Object> result = new HashMap<>();
        extracts.stream().forEach(extract -> {
            Object value = getExtract(extract, page);
            if (ValidateUtil.notNull(value)) {
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
        return selectable.all();
    }



}
