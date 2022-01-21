#### 1.生成设置tomcat默认访问首页地址 

```
 修改或者添加  $tomcat_home/webapps/ROOT/index.html，添加js脚本:
 <script language="javascript"> 
    window.location.href = "http://" + window.location.hostname + "/datapipeline-web";
 </script>
```
