package com.arm.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhaolangjing
 * @since 2021-3-17 17:14
 */
@Slf4j
public class DubboTest {
    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");

    public static void main(String[] args) throws IOException {
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.in.read();
    }

}
