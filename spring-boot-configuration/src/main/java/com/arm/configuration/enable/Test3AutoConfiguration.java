package com.arm.configuration.enable;

import org.springframework.context.annotation.Bean;

/**
 * @author zhaolangjing
 */
public class Test3AutoConfiguration {

    @Bean
    public Test3Bean test3Bean() {
        return new Test3Bean("从jar包使用@Enable注解中获取bean");
    }
}

