package com.chinamcloud.spider.model;


public class Extract {
    // 解析类型
    private String resolver = "xpath";
    // 字段
    private String filed;
    // 表达式
    private String expression;

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
