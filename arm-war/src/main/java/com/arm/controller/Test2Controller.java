package com.arm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test2")
public class Test2Controller {
    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "hello world test";
    }
}
