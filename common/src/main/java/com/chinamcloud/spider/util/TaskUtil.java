package com.chinamcloud.spider.util;


public class TaskUtil {

    public static String generatorId(String url) {
        return EncrpytUtil.md5(url);
    }
}
