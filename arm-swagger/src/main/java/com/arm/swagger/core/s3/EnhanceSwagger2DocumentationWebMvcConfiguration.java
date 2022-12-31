package com.arm.swagger.core.s3;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerMapping;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.SpringfoxWebConfiguration;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.WebMvcPropertySourcedRequestMappingHandlerMapping;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger.configuration.SwaggerCommonConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2JacksonModule;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * @author ewa
 */
@ConditionalOnClass(name = "springfox.documentation.spring.web.SpringfoxWebMvcConfiguration")
@Import({SpringfoxWebConfiguration.class, SpringfoxWebMvcConfiguration.class, SwaggerCommonConfiguration.class})
@ComponentScan(basePackages = {
        "springfox.documentation.swagger2.readers.parameter",
        "springfox.documentation.swagger2.mappers"
})
public class EnhanceSwagger2DocumentationWebMvcConfiguration {
    @Bean
    public JacksonModuleRegistrar swagger2Module() {
        return new Swagger2JacksonModule();
    }

    @Bean
    public HandlerMapping swagger2ControllerMapping(
            Environment environment,
            DocumentationCache documentationCache,
            ServiceModelToSwagger2Mapper mapper,
            JsonSerializer jsonSerializer) {
        return new WebMvcPropertySourcedRequestMappingHandlerMapping(
                environment,
                new EnhanceSwagger2ControllerWebMvc(environment, documentationCache, mapper, jsonSerializer));
    }
}
