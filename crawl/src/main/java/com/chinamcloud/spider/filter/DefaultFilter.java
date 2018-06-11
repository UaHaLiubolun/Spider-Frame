package com.chinamcloud.spider.filter;

import com.chinamcloud.spider.model.DataRule;
import com.chinamcloud.spider.model.Page;
import com.chinamcloud.spider.model.Task;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;


public class DefaultFilter implements PageFilter {


    @Override
    public void filter(Task task, Page page) {

        List<DataRule> dataRules = task.getRule().getDataRules();
        Document document = page.getDocument();
        dataRules.stream().forEach(
                dataRule -> {
                    Map<String, String> rule = dataRule.getRules();
                    for (Map.Entry<String, String> entry : rule.entrySet()) {
                        System.out.println(document.select(entry.getValue()).text());
                    }
                }
        );
    }
}
