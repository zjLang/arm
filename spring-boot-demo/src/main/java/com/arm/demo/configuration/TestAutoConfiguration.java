package com.arm.demo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaolangjing
 */
@Configuration
public class TestAutoConfiguration {

    @Bean
    public TestBean testBean() {
        return new TestBean("本包启动无须配置spring.factories");
    }
}
