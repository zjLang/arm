# springBoot mongoDB

## maven依赖

```
<dependencies>
    <dependency> 
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency> 
</dependencies>
```

## 配置

```
单机：spring.data.mongodb.uri=mongodb://name:pass@localhost:27017/test
集群：spring.data.mongodb.uri=mongodb://user:pwd@ip1:port1,ip2:port2/database
```

## 注意事项

+ 1.mongoTemplate生成数据时默认带上了field: _class。

```
    具体类：DefaultMongoTypeMapper  MappingMongoConverter
    写入点：
    org/springframework/data/mongodb/core/convert/MappingMongoConverter#write(Object obj, Bson bson) ->
    (396) boolean handledByCustomConverter = conversions.hasCustomWriteTarget(entityType, Document.class); // 判断是否有自定义转换器
    最终转换为：
    DefaultMongoTypeMapper#writeTypeTo(Bson sink, Object alias) 中的typeKey判断。所以修改办法：
    
    解决方案：
    @Configuration
    public class MangoConfig {
        @Bean
        public MongoTemplate mangoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext mongoMappingContext) {
            MappingMongoConverter mongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), mongoMappingContext);
            // 设置 typeKey为null，删除_class field
            mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
            return new MongoTemplate(mongoDbFactory, mongoConverter);
        }
    }
    
```

+ 2.修改类，字段映射关系

```
默认使用 类名称和字段名   
请使用：
org.springframework.data.mongodb.core.mapping.Document，
org.springframework.data.mongodb.core.mapping.Field

其他：
索引标记：org.springframework.data.mongodb.core.index.Indexed
文本索引：org.springframework.data.mongodb.core.index.TextIndexed
内连引用：org.springframework.data.mongodb.core.mapping.DBRef （主要时mongo文档总大小不能超过16M,超过就要考虑用这个设计）
```

+ 3.MongoDB Server generation 3 验证模型
![img.png](../../data/img/db/mongo-1.png)

+ 4.MongoTemplate它是线程安全的，可以跨多个实例重用