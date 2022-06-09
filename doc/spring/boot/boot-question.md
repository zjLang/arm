# springBoot使用中遇到的一些坑

## 1.bean未能被加载

```
pom导入该包时别加：optional 属性。如下：

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-indexer</artifactId>
    <!--<optional>true</optional>--> <!--千万别加这个，加了会造成依赖无法传递，引用包的bean无法被spring 加载   -->
</dependency>

千万别加这个,加了会造成依赖无法传递,引用改包的项目的bean无法被spring 加载报错。除非引用的项目也加入改依赖引用。
```