package com.arm.configuration;

import lombok.Data;

@Data
public class Test2Bean {
    private String test;

    public Test2Bean(String test) {
        this.test = test;
    }
}
