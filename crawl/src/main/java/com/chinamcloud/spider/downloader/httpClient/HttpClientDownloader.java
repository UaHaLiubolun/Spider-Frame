package com.chinamcloud.spider.downloader.httpClient;


import com.chinamcloud.spider.downloader.Downloader;
import com.chinamcloud.spider.model.Page;
import com.chinamcloud.spider.model.Site;
import com.chinamcloud.spider.model.Task;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientDownloader implements Downloader {


    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpRequestConvert httpUriRequestConverter = new HttpRequestConvert();

    /**
     * 获取HttpClient 一个Site对应一个HttpClient
     * @param site Site
     * @return HttpClient
     */
    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) return httpClientGenerator.getClient(null);
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(site.getDomain());
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }


    @Override
    public Page download(Task task) {
        if (task == null || task.getRequest().getUrl() == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        Page page = new Page();
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(task);
        System.out.println(task.getRequest().getUrl());
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(),
                    requestContext.getHttpClientContext());
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new IOException();
            }
            page = handleResponse(task, httpResponse);
            task.success();
        } catch (IOException e) {
            task.error();
            e.printStackTrace();
        } finally {
            if (httpResponse != null)
                EntityUtils.consumeQuietly(httpResponse.getEntity());
        }
        return page;
    }


    /**
     * 处理responseBody 生成Page
     * @param task task
     * @param httpResponse  返回
     * @return Page
     * @throws IOException
     */
    private Page handleResponse(Task task, HttpResponse httpResponse) throws IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        Page page = new Page();
        page.setRawText(new String(bytes, task.getSite().getCharset()));
        return page;
    }

    @Override
    public void setThread(int threadNum) {
        httpClientGenerator.setPoolSize(threadNum);
    }
}
