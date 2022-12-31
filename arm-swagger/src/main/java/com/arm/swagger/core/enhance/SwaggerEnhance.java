package com.arm.swagger.core.enhance;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import io.swagger.models.Swagger;
import springfox.documentation.schema.Model;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Documentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Swagger 范型增强
 *
 * @author ewa
 */
public class SwaggerEnhance {
    /**
     * 范性类和范性属性的一种映射关系。
     */
    //private final Map<String, String> classParadigmFieldMap;

    private final Documentation documentation;

    private Swagger swagger;
    /**
     * swagger 模型中未被解析的api模型（范型中的）
     * 因为swagger的解析冲@Api注解开始。
     */
    private Map<String, Class<?>> swaggerApiModelsMap;

    /**
     * 范型的提取物
     * eg:com.arm.swagger.model.ApiResponseEntity<com.arm.swagger.model.User>
     */
    //private Set<String> paradigmLinks;

    private final Map<String, Model> models = new HashMap<>();
    private final Map<String, MyApi> myApis = new TreeMap<>();


    public SwaggerEnhance(Documentation documentation, Swagger swagger, Map<String, String> classParadigmFieldMap) {
        this.documentation = documentation;
        this.swagger = swagger;
    }

    /**
     *
     */
    public Swagger enhance() {
        // 整理出所有的范型返回值
        extractParadigmLink();
        // 类值解析


        return null;
    }


    private void extractParadigmLink() {
        Map<String, List<ApiListing>> apiListings = documentation.getApiListings();
        apiListings.forEach((k, apiListing) -> {
            for (ApiListing listing : apiListing) {
                List<ApiDescription> apis = listing.getApis();
                models.putAll(listing.getModels());
                if (CollectionUtils.isNotEmpty(apis)) {
                    for (ApiDescription api : apis) {
                        ModelReference responseModel = api.getOperations().get(0).getResponseModel();
                        MyApi myApi = new MyApi(api.getPath(), responseModel.getType(), responseModel.getTypeSignature().get());
                        myApis.put(api.getPath(), myApi);
                    }
                }
            }
        });
    }


    private static class MyApi {
        /**
         * url地址
         */
        private final String url;
        /**
         * swagger 定义的 modelType
         * eg：ApiResponseEntity«User»
         */
        private final String responseModelSimpleName;

        /**
         * swagger定义的模型签名
         * com.arm.swagger.model.ApiResponseEntity<com.arm.swagger.model.User>
         */
        private final String responseModelTypeSingNature;

        public MyApi(String url, String responseModelSimpleName, String responseModelTypeSingNature) {
            this.url = url;
            this.responseModelSimpleName = responseModelSimpleName;
            this.responseModelTypeSingNature = responseModelTypeSingNature;
        }

        public String getUrl() {
            return url;
        }

        public String getResponseModelSimpleName() {
            return responseModelSimpleName;
        }

        public String getResponseModelTypeSingNature() {
            return responseModelTypeSingNature;
        }
    }


}
