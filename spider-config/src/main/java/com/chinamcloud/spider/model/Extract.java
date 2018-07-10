package com.chinamcloud.spider.model;


public class Extract {

    private String value;

    private String filed;

    public static enum  Type {XPath, Regex, Css, JsonPath};

    private Type type = Type.XPath;

    public static enum Source {
        Html, Url, RawHtml, RawText
    }

    private Source source = Source.Html;

    private boolean multi = false;

    private boolean notNull = false;

    private DataConversion dataConversion = null;

    public DataConversion getDataConversion() {
        return dataConversion;
    }

    public void setDataConversion(DataConversion dataConversion) {
        this.dataConversion = dataConversion;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }
}
