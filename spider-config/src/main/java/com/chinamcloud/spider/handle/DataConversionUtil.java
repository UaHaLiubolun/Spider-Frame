package com.chinamcloud.spider.handle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataConversionUtil {


    public long fmtDate(Object value, String expression) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expression);
        try {
            Date date = simpleDateFormat.parse((String) value);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
