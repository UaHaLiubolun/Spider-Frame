package com.chinamcloud.spider.process;

import com.chinamcloud.spider.handle.PageModelExtractor;
import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConfigPageProcess implements PageProcessor{

    private Site site;

    private List<PageModelExtractor> pageModelExtractors = new ArrayList<>();

    private boolean extractLinks = true;

    public static ConfigPageProcess create(Site site, PageModel... pageModels) {
        ConfigPageProcess configPageProcess = new ConfigPageProcess(site);
        for (PageModel pageModel : pageModels) {
            configPageProcess.addPageModelExtractors(pageModel);
        }
        return configPageProcess;
    }

    private ConfigPageProcess addPageModelExtractors(PageModel pageModel) {
        PageModelExtractor pageModelExtractor = PageModelExtractor.create(pageModel);
        pageModelExtractors.add(pageModelExtractor);
        return this;
    }


    private ConfigPageProcess(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        pageModelExtractors.stream().forEach(pageModelExtractor -> {
            if (isExtractLinks(page.getRequest().getUrl(), pageModelExtractor.getPageModel().getTargetUrl())) {
                extractLinks(page, pageModelExtractor.getPageModel().getHelpUrl(), pageModelExtractor.getPageModel());
            }
            extractLinks(page, pageModelExtractor.getPageModel().getTargetUrl(), pageModelExtractor.getPageModel());
            Object process = pageModelExtractor.process(page);
            if (process == null || (process instanceof List && ((List) process).size() == 0)) {

            } else {
                put(page, process, pageModelExtractor.getPageModel());
            }
        });
        if (page.getResultItems().getAll().size() == 0) {
            page.getResultItems().setSkip(true);
        }
    }

    private void put(Page page, Object process, PageModel pageModel) {
        if (pageModel.getMultiExtract() != null && pageModel.getMultiExtract().isMulti()) {
            List<Object> objects = (List) process;
            for (int i = 0; i < objects.size(); i++) {
                page.putField(String.valueOf(objects.hashCode() + i), objects.get(i));
            }
        } else {
            page.putField(String.valueOf(process.hashCode()), process);
        }
    }

    private void extractLinks(Page page, List<String> regex, PageModel pageModel) {
        if (regex == null || regex.size() == 0) return;
        List<String> links = page.getHtml().links().all();
        links.stream().forEach(link -> {
            regex.stream().forEach(r -> {
                Pattern pattern = Pattern.compile(r);
                Matcher matcher = pattern.matcher(link);
                if (matcher.find()) {
                    String url = matcher.group(0);
                    page.addTargetRequest(new Request(url, isExtractLinks(url, pageModel.getTargetUrl())));
                }
            });
        });
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public boolean isExtractLinks(String url, List<String> targetUrls) {
        boolean isExtract = true;
        for (String s : targetUrls) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                isExtract = false;
                break;
            }
        }
        return isExtract;
    }

}
