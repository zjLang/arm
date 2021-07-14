package com.arm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("test.dhtml")
    @ResponseBody
    public String test() {
        return "hello world test";
    }

    @RequestMapping("test1")
    @ResponseBody
    public String test1() {
        return "hello world test1";
    }
}
