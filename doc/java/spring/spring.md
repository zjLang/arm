## Ⅰ.包结构
```
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

#### 基本概念
+ 1.面试切面的编程，旨在减少重复的代码
