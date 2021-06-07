## 项目部署
#### Ⅰ.tomcat部署（centOS）
+ 1.准备工作，安装jdk（参考：其他/安装jdk）
+ 2.安装tomcat
    + 1.拷贝apache-tomcat-9.0.14.zip
    + 2.unzip apache-tomcat-9.0.14.zip
    + 3.修改配置server.xml，如果需要热部署的话
    ```
        <Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true" startStopThreads="8" undeployOldVersion="true">
    ```
    + 4.bin/setclasspath.sh 头部添加(根据实际需求调整):
    ```
        JAVA_OPTS="-server -Xms2048m -Xmx2048m -XX:PermSize=1024m -XX:MaxPermSize=1024m"
    ```
+ 3.上传war部署
 
    
#### Ⅱ.springBoot部署

#### Ⅲ.docker部署


## 其他
#### 安装jdk
+ 1.安装jdk（根据需要安装对应的jdk就行了，如果通外网，可以使用yun安装），一下介绍考包安装
   + 1.使用rz或者Xftp上传包到路径（jdk-8u191-linux-x64.tar.gz）：rz
   + 2.解压 ：tar zxvf jdk-8u191-linux-x64.tar.gz
   + 3.设置环境变量 ，vim /etc/profile 在末尾添加 ：
   ```
    export JAVA_HOME=/home/jdk1.8.0_191
    export JRE_HOME=/home/jdk1.8.0_191/jre
    export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
    export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
   ```
   + 4.使配置生效： source /etc/profile
   + 5.检查：java -version
       		