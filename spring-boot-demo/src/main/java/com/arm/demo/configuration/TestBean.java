package com.arm.demo.configuration;


import lombok.Data;

/**
 * @author zhaolangjing
 */
@Data
public class TestBean {
    private String test;

    public TestBean(String test) {
        this.test = test;
    }
}
