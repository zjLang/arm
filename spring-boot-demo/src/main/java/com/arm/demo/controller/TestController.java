package com.arm.demo.controller;

import org.springframework.stereotype.Controller;

/**
 * @author zhaolangjing
 */
@Controller
//@RequestMapping("test")
public class TestController {

    //@RequestMapping("test")
    //@ResponseBody
    public String test() {
        return "hello world s";
    }
}
