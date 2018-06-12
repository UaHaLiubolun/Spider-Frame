package com.chinamcloud.spider.util;

public class ValidateUtil {

    public static boolean notNull(String value) {
        if (value == null || value.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean notNull(Object value) {
        if (value == null || value.equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
