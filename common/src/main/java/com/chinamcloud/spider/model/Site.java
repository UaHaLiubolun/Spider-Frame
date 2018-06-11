package com.chinamcloud.spider.model;


import java.io.Serializable;
import java.util.Map;

public class Site implements Serializable{

    private String siteId;
    // 域名
    private String domain;
    // userAgent
    private String userAgent;
    // cookies
    private Map<String, String> cookies;
    // 编码
    private String charset = "utf-8";
    // 是否使用代理
    private boolean isProxy = false;
    // 重试次数
    private int retryCount = 0;
    // 睡眠时间
    private int sleep = 0;
    // 头信息
    private Map<String, String> headers;
    // 超时时间
    private int timeOut = 5000;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
