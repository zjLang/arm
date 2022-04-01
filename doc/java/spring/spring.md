# 1.包结构

```
├── spring-framework   （parent项目）  
│   ├── spring-aop     提供aop功能
│   ├── spring-aspects 提供aop的 aspectj 支持
│   ├── spring-beans   提供bean相关处理的功能（IoC,DI）关键类：BeanFactory FactoryBean 
│   ├── spring-context    spring ApplicationContext 上下文启动类
│   ├── spring-context-indexer     spring 打包工具，依赖之后可以加快，使用java的api机制，在编译期借助javac对注解进行处理生成预处理文件spring.components
│   ├── spring-context-support     spring-context 包内容的补充说明
│   ├── spring-core     核心工具类
│   ├── spring-expression     SpEL表达式支持包，可独立于spring单独使用。  
│   ├── spring-jcl     log支持，简单的，可不关注。
│   ├── spring-jdbc    Spring 对JDBC 数据访问进行封装的所有类
│   ├── spring-jms     spring提供的消息发送包，类似于 rabbitMq。
│   ├── spring-messaging   消息协议的相关定义
│   ├── spring-orm    对持久化框架的集成
│   ├── spring-oxm    提供了java对象和xml之间的转换
│   ├── spring-r2dbc  可轻松实现基于R2DBC的存储库。R2DBC（Reactive Relational Database Connectivity）是一个使用反应式驱动集成关系数据库的孵化器。Spring Data R2DBC运用熟悉的Spring抽象和repository 支持R2DBC
│   ├── spring-tx     事务控制
│   ├── spring-web    web支持
│   ├── spring-webflux   webflux支持
│   ├── spring-webmvc  mvc支持
│   ├── spring-websocket    websocket支持


核心 ：
    1.spring-core  核心工具类
    2.spring-bean  提供IoC,DI功能   
    3.spring-aop   提供aop功能
    4.spring-context spring ApplicationContext 上下文启动类
数据访问：    
    5.spring-dao  Spring DAO、Spring Transaction 进行数据访问的所有类
    6.spring-jdbc Spring 对JDBC 数据访问进行封装的所有类
    7.spring-tx   事务控制
web:    
    8.spring-support  支持UI模版（Velocity，FreeMarker，JasperReports），邮件服务，脚本服务(JRuby)，缓存Cache（EHCache），任务计划Scheduling（uartz）方面的类
    9.spring-web  Spring框架时所需的核心类，包括自动载入Web Application Context 特性的类、Struts 与JSF 集成类、文件上传的支持类、Filter 类和大量工具辅助类
    10.spring-webmvc 框架的Servlets，Web MVC框架，控制器和视图支持
```

参考：![包结构图](../../img/spring-all/包结构图.png)

# 2.基本概念

+ 1.面试切面的编程，旨在减少重复的代码

# 3.spring核心包

+ spring核心主要包括以下几个方面，core ，bean ，context ...

## [3.1 spring-core](./spring-core.md)

## [3.2 spring-bean/context](./spring-bean_context.md)

## [3.spring-aop](./spring-aop.md)