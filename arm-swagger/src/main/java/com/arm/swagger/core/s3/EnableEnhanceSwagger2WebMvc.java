package com.arm.swagger.core.s3;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.lang.annotation.*;

/**
 * 兼容 当swagger 使用 EnableSwagger2WebMvc注解时
 * 高版本
 *
 * @author ewa
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({EnhanceSwagger2DocumentationWebMvcConfiguration.class})
@ConditionalOnWebApplication
@ConditionalOnClass(EnableSwagger2WebMvc.class)
public @interface EnableEnhanceSwagger2WebMvc {

}
