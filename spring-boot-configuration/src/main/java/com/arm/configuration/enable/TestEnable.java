package com.arm.configuration.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhaolangjing
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(Test3AutoConfiguration.class)
public @interface TestEnable {
}
