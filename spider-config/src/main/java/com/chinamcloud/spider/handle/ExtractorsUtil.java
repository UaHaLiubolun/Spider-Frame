package com.chinamcloud.spider.handle;

import com.chinamcloud.spider.model.Extract;
import us.codecraft.webmagic.selector.*;


public class ExtractorsUtil {

    public static Selector getSelector(Extract extract) {
        String value = extract.getValue();
        Selector selector;
        switch (extract.getType()) {
            case Css:
                selector = new CssSelector(value);
                break;
            case Regex:
                selector = new RegexSelector(value);
                break;
            case XPath:
                selector = new XpathSelector(value);
                break;
            case JsonPath:
                selector = new JsonPathSelector(value);
                break;
            default:
                selector = new XpathSelector(value);
        }
        return selector;
    }
}
