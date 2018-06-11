package com.chinamcloud.spider.downloader.httpClient;



import com.chinamcloud.spider.model.Request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestBodyGenerator {

    public static HttpRequestBody generator(Request request, String charset) {
        if (request.getContentType() == null ||
                request.getRequestBody() == null)
            return null;

        switch (request.getRequestBody()) {
            case "application/json":
                return HttpRequestBody.json(request.getRequestBody(), charset);
            case "application/x-www-form-urlencoded" :
                return HttpRequestBody.form(mapConvert(request.getRequestBody()), charset);
            case "text/xml":
                return HttpRequestBody.xml(request.getRequestBody(), charset);
        }

        return null;
    }

    private static Map<String, Object> mapConvert(String param) {
        List<String> params = Arrays.asList(param.split("&"));
        Map<String, Object> paramMap = new HashMap<>();
        params.stream().forEach(
                tmp -> {
                    String[] tmps = tmp.split("=");
                    paramMap.put(tmps[0], tmps[1]);
                }
        );
        return paramMap;
    }
}
