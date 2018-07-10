package com.chinamcloud.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping(value = "/index")
    public String helloWorld() {
        return "hello world";
    }

}
