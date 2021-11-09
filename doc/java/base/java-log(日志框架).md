## logback

+ 1.[官网](http://logback.qos.ch/manual/appenders.html)
  
+ 2.logback日志配置文件查找加载顺序:

```
ch.qos.logback.classic.util.ContextInitializer
public URL findURLOfDefaultConfigurationFile(boolean updateStatus) {
    ClassLoader myClassLoader = Loader.getClassLoaderOfObject(this);
    // 1.从系统环境变量配置（logback.configurationFile）中查找
    // eg: -Dlogback.configurationFile=E:\下载文件\apache-tomcat-9.0.43\properties\logback-test.xml
    URL url = findConfigFileURLFromSystemProperties(myClassLoader, updateStatus);
    if (url != null) {
        return url;
    }
    // 2.从 logback-test.xml 中查找
    url = getResource(TEST_AUTOCONFIG_FILE, myClassLoader, updateStatus);
    if (url != null) {
        return url;
    }
    // 3.从 logback.groovy 中查找
    url = getResource(GROOVY_AUTOCONFIG_FILE, myClassLoader, updateStatus);
    if (url != null) {
        return url;
    }
    // 4.从 logback.xml 中查找
    return getResource(AUTOCONFIG_FILE, myClassLoader, updateStatus);
}
```