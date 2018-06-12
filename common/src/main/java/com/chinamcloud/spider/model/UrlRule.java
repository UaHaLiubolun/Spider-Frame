package com.chinamcloud.spider.model;

import java.util.List;

public class UrlRule {

    private String urlId;

    private Extract url;

    private String method;

    private List<Extract> requestBody;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public Extract getUrl() {
        return url;
    }

    public void setUrl(Extract url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Extract> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(List<Extract> requestBody) {
        this.requestBody = requestBody;
    }
}
