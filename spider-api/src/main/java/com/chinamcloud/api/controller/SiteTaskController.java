package com.chinamcloud.api.controller;


import com.chinamcloud.api.CodeResult;
import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.SiteTask;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/siteTask")
@CrossOrigin
public class SiteTaskController {

    private SiteTaskDao siteTaskDao;

    {
        siteTaskDao = new SiteTaskDao();
    }


    @GetMapping()
    public CodeResult getAll() {
        return CodeResult.successResult(null, siteTaskDao.getAll());
    }

    @PutMapping()
    public CodeResult modify(
            @RequestBody SiteTask siteTask) {
        boolean isSuccess = siteTaskDao.modify(siteTask.getSite().getDomain(), siteTask);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    @PostMapping()
    public CodeResult add(@RequestBody SiteTask siteTask) {
        boolean isSuccess = siteTaskDao.add(siteTask);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

}
