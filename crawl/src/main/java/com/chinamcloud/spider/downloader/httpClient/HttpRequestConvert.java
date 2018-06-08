package com.chinamcloud.spider.downloader.httpClient;


import com.chinamcloud.spider.downloader.HttpConstant;
import com.chinamcloud.spider.model.Task;
import com.chinamcloud.spider.utils.UrlUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;


import java.util.Map;

public class HttpRequestConvert {

    public HttpClientRequestContext convert(Task task) {
        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(task));
        httpClientRequestContext.setHttpClientContext(convertHttpClientContext(task));
        return httpClientRequestContext;
    }


    private HttpClientContext convertHttpClientContext(Task task) {
        HttpClientContext httpContext = new HttpClientContext();
        // 设置Cookie
        if (task.getSite().getCookies() != null && !task.getSite().getCookies().isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : task.getSite().getCookies().entrySet()) {
                BasicClientCookie cookie1 = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie1.setDomain(UrlUtils.removePort(UrlUtils.getDomain(task.getRequest().getUrl())));
                cookieStore.addCookie(cookie1);
            }
            httpContext.setCookieStore(cookieStore);
        }
        return httpContext;
    }



    private HttpUriRequest convertHttpUriRequest(Task task) {
        RequestBuilder requestBuilder = selectRequestMethod(task).
                setUri(UrlUtils.fixIllegalCharacterInUrl(task.getRequest().getUrl()));
//        if (task.getSite().getHeaders() != null) {
//            for (Map.Entry<String, String> headerEntry : task.getSite().getDefaultHeaders().entrySet()) {
//                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
//            }
//        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (task.getSite() != null) {
            requestConfigBuilder.setConnectionRequestTimeout(task.getSite().getTimeOut())
                    .setSocketTimeout(task.getSite().getTimeOut())
                    .setConnectTimeout(task.getSite().getTimeOut())
                    .setCookieSpec(CookieSpecs.STANDARD);
        }

        // TODO 代理设置
//        if (task.getSite().isProxy()) {
//            String proxy = MySQLUtil.getProxy();
//            if (!proxy.equals("")) {
//                String[] tmp = proxy.split(":");
//                HttpHost httpHost = new HttpHost(tmp[0], Integer.parseInt(tmp[1]), "http");
//                requestConfigBuilder.setProxy(httpHost);
//            }
//        }

        requestBuilder.setConfig(requestConfigBuilder.build());
        HttpUriRequest httpUriRequest = requestBuilder.build();
        // TODO Task里也应该要有Headers
        if (task.getSite().getHeaders() != null && !task.getSite().getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : task.getSite().getHeaders().entrySet()) {
                httpUriRequest.addHeader(header.getKey(), header.getValue());
            }
        }
        return httpUriRequest;
    }


    /**
     * method convert
     * @param task task
     * @return requestBuilder
     */
    private RequestBuilder selectRequestMethod(Task task) {
        String method = task.getRequest().getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(),task);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), task);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }


    /**
     * form params convert
     * @param requestBuilder requestBuilder
     * @param task task
     * @return requestBuilder
     */
    private RequestBuilder addFormParams(RequestBuilder requestBuilder, Task task) {
        if (task.getRequest().getRequestBody() != null) {
            ByteArrayEntity entity = new ByteArrayEntity(task.getRequest().getRequestBody().getBody());
            entity.setContentType(task.getRequest().getRequestBody().getContentType());
            requestBuilder.setEntity(entity);
        }
        return requestBuilder;
    }

}
