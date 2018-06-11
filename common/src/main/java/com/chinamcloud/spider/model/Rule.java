package com.chinamcloud.spider.model;


import java.util.List;

public class Rule {

    private String ruleId;

    private String filter;

    private List<DataRule> dataRules;

    private List<UrlRule> urlRules;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<DataRule> getDataRules() {
        return dataRules;
    }

    public void setDataRules(List<DataRule> dataRules) {
        this.dataRules = dataRules;
    }

    public List<UrlRule> getUrlRules() {
        return urlRules;
    }

    public void setUrlRules(List<UrlRule> urlRules) {
        this.urlRules = urlRules;
    }
}
