package com.arm.code.mix.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author z-ewa
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "hello world";
    }
}
