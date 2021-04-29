package com.arm.spring.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * 演示对象
 *
 * @author zhaolangjing
 * @since 2021-3-12 14:37
 */
@Slf4j
public class Dog extends InstantiationAwareBeanPostProcessorAdapter implements BeanNameAware, BeanFactoryAware,
        InitializingBean, DisposableBean, BeanPostProcessor {
    private String name;

    public Dog(String name) {
        log.info( "execute dog constructor " );
        this.name = name;
    }

    public void init() {
        log.info( "execute dog init" );
    }

    public void destroyXml() {
        log.info( "execute dog destroyXml" );
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info( "execute dog postProcessBeforeInitialization {} , {}", bean, beanName );
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info( "execute dog postProcessAfterInitialization {} , {}", bean, beanName );
        return bean;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info( "execute dog setBeanFactory" );
    }

    @Override
    public void setBeanName(String s) {
        log.info( "execute dog setBeanName:" + s );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info( "execute dog afterPropertiesSet" );
    }

    @Override
    public void destroy() throws Exception {
        log.info( "execute dog destroy" );
    }
}
