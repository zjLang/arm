#### 1.nohup启动jar：

```
 https://blog.csdn.net/sangyongjia/article/details/49181939
 nohup java -jar xxx.jar  -Xmx1024m -Xms1024m -> log.out  2>&1 &
   
 nohup java -jar /home/codegen.war  -Xmx300m -Xms300m -> codegen.out  2>&1 &

```

#### 2.生成设置tomcat默认访问首页地址

```
 修改或者添加  $tomcat_home/webapps/ROOT/index.html，添加js脚本:
 <script language="javascript"> 
    window.location.href = "http://" + window.location.hostname + "/datapipeline-web";
 </script>
```