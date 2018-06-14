package com.chinamcloud.spider.handle;


import com.chinamcloud.spider.model.Extract;
import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PageModelExtractor {

    private PageModel pageModel;

    public PageModel getPageModel() {
        return pageModel;
    }

    public static PageModelExtractor create(PageModel pageModel) {
        PageModelExtractor pageModelExtractor = new PageModelExtractor(pageModel);
        return pageModelExtractor;
    }

    public PageModelExtractor(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public Object process(Page page) {
        boolean matcher = false;
        for (String links : pageModel.getTargetUrl()) {
            Pattern pattern = Pattern.compile(links);
            if (pattern.matcher(page.getUrl().toString()).matches()) {
                matcher = true;
            }
        }
        if (!matcher) return null;
        if (pageModel.getMultiExtract() == null) {
            return processSingle(page, null, true);
        } else {
            Selector selector = ExtractorsUtil.getSelector(pageModel.getMultiExtract());
            if (pageModel.getMultiExtract().isMulti()) {
                List<Object> os = new ArrayList<Object>();
                List<String> list = selector.selectList(page.getRawText());
                for (String s : list) {
                    Object o = processSingle(page, s, false);
                    if (o != null) {
                        os.add(o);
                    }
                }
                return os;
            } else {
                String select = selector.select(page.getRawText());
                Object o = processSingle(page, select, false);
                return o;
            }
        }
    }


    private Map<String, Object> processSingle(Page page, String html, boolean isRaw) {
        Map<String, Object> map = new HashMap<>();
        for (Extract extract : pageModel.getExtracts()) {
            if (extract.isMulti()) {
                List<String> value;
                Selector selector = ExtractorsUtil.getSelector(extract);
                switch (extract.getSource()) {
                    case RawHtml:
                        value = page.getHtml().selectDocumentForList(selector);
                        break;
                    case Html:
                        if (isRaw) {
                            value = page.getHtml().selectDocumentForList(selector);
                        } else {
                            value = selector.selectList(html);
                        }
                        break;
                    case Url:
                        value = selector.selectList(page.getUrl().toString());
                        break;
                    case RawText:
                        value = selector.selectList(page.getRawText());
                        break;
                    default:
                        value = selector.selectList(html);
                }
                if ((value == null || value.size() == 0) && extract.isNotNull()) {
                    return null;
                }
                map.put(extract.getFiled(), value);
            } else {
                String value;
                Selector selector = ExtractorsUtil.getSelector(extract);
                switch (extract.getSource()) {
                    case RawHtml:
                        value = page.getHtml().selectDocument(selector);
                        break;
                    case Html:
                        if (isRaw) {
                            value = page.getHtml().selectDocument(selector);
                        } else {
                            value = selector.select(html);
                        }
                        break;
                    case Url:
                        value = selector.select(page.getUrl().toString());
                        break;
                    case RawText:
                        value = selector.select(page.getRawText());
                        break;
                    default:
                        value = selector.select(html);
                }
                if (value == null && extract.isNotNull()) {
                    return null;
                }
                map.put(extract.getFiled(), value);
            }
        }
        return map;
    }
}
