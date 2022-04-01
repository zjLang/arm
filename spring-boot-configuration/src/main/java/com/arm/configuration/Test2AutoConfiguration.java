package com.arm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaolangjing
 */
@Configuration
public class Test2AutoConfiguration {

    @Bean
    public Test2Bean test2Bean() {
        return new Test2Bean("从jar包通过spring.factories中获取的bean");
    }
}

