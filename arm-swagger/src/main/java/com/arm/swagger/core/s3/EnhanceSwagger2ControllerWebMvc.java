package com.arm.swagger.core.s3;


import com.arm.swagger.core.enhance.SwaggerUtil;
import io.swagger.models.*;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.PropertySourcedMapping;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2ControllerWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.isEmpty;
import static springfox.documentation.swagger.common.HostNameProvider.componentsFrom;

@Controller
@ConditionalOnClass(name = "javax.servlet.http.HttpServletRequest")
@ApiIgnore
public class EnhanceSwagger2ControllerWebMvc {
    private static final String DEFAULT_URL = "/v2/api-docs";
    private static final Logger LOGGER = LoggerFactory.getLogger(Swagger2ControllerWebMvc.class);
    private static final String HAL_MEDIA_TYPE = "application/hal+json";

    private final String hostNameOverride;
    private final DocumentationCache documentationCache;
    private final ServiceModelToSwagger2Mapper mapper;
    private final JsonSerializer jsonSerializer;

    @Autowired
    public EnhanceSwagger2ControllerWebMvc(
            Environment environment,
            DocumentationCache documentationCache,
            ServiceModelToSwagger2Mapper mapper,
            JsonSerializer jsonSerializer) {

        this.hostNameOverride =
                environment.getProperty(
                        "springfox.documentation.swagger.v2.host",
                        "DEFAULT");
        this.documentationCache = documentationCache;
        this.mapper = mapper;
        this.jsonSerializer = jsonSerializer;
    }

    @RequestMapping(
            value = DEFAULT_URL,
            method = RequestMethod.GET,
            produces = {APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE})
    @PropertySourcedMapping(
            value = "${springfox.documentation.swagger.v2.path}",
            propertyKey = "springfox.documentation.swagger.v2.path")
    @ResponseBody
    public ResponseEntity<Json> getDocumentation(
            @RequestParam(value = "group", required = false) String swaggerGroup,
            HttpServletRequest servletRequest) {

        String groupName = ofNullable(swaggerGroup).orElse(Docket.DEFAULT_GROUP_NAME);
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        if (documentation == null) {
            LOGGER.warn("Unable to find specification for group {}", groupName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Swagger swagger = mapper.mapDocumentation(documentation);
        //extend2(swagger);
        UriComponents uriComponents = componentsFrom(servletRequest, swagger.getBasePath());
        swagger.basePath(isEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
        if (isEmpty(swagger.getHost())) {
            swagger.host(hostName(uriComponents));
        }
        return new ResponseEntity<>(jsonSerializer.toJson(swagger), HttpStatus.OK);
    }

    private String hostName(UriComponents uriComponents) {
        if ("DEFAULT".equals(hostNameOverride)) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            if (port > -1) {
                return String.format("%s:%d", host, port);
            }
            return host;
        }
        return hostNameOverride;
    }

    private void extend2(Swagger swagger) {
        // 响应返回参数增强
        // 只处理get 和 post请求
        swagger.getPaths().forEach((k, v) -> {
            enhance(v.getGet(), swagger);
            enhance(v.getPost(), swagger);
        });
    }

    private void enhance(Operation operation, Swagger swagger) {
        Map<String, Model> definitions = swagger.getDefinitions();
        if (operation != null) {
            String description = operation.getDescription();
            System.out.println("summary1:" + description);
            // 无法不指定 ApiResponseEntity ，因为巨范型话，无法知道是那个值需要被范型处理
            if (SwaggerUtil.isParadigm(description)) {
                Map<String, Response> responses = operation.getResponses();
                responses.forEach((k, response) -> {
                    if(response.getSchema() instanceof RefProperty){
                        RefProperty property = (RefProperty) response.getSchema();
                        // 401 400等为null ，
                        if (property != null) {
                            // 如果范型已经存在就不再解析
                            if (definitions.get(description) == null) {
                                System.out.println("summary2:" + description);
                                Model model = definitions.get(property.getSimpleRef());
                                //通过原始引用获取原始模型， 处理需要被范型的字段
                                Property sourceProperty = definitions.get(property.getSimpleRef()).getProperties().get(SwaggerUtil.PARADIGM_PROPERTY);

                                Property newProperty = SwaggerUtil.getNewProp(sourceProperty, SwaggerUtil.getRealType(description), swagger.getDefinitions());
                                // 往模型库中添加一个新的模型 ， 查询新值
                                Model newModel = new ModelImpl();
                                BeanUtils.copyProperties(model, newModel);
                                newModel.getProperties().put(SwaggerUtil.PARADIGM_PROPERTY, newProperty);
                                definitions.put(description, newModel);
                            }
                            // 接着把operation上的引用更新到该模型上
                            RefProperty newRefProp = new RefProperty();
                            BeanUtils.copyProperties(property, newRefProp);
                            newRefProp.set$ref(description);
                            response.setSchema(newRefProp);
                        }
                    }
                });
            }
        }
    }
}
