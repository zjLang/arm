## 1.设置用程序打开某个文件

### 1.使用sbulime打开配置文件

> 1.安装sublime。
> 2.打开用户配置文件：vim ~/.bash_profile
> 3.输入：alias subl="'/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl'"
> 4.wq保存
> 5.生效：source ~/.bash_profile
> 6.执行：subl ~/.bash_profile

### 2.配置jdk切换

> https://www.cnblogs.com/cb0327/p/6670154.html
> 列出所有的java_home安装： /usr/libexec/java_home -V
>
> MAC下同时安装多个版本的JDK:配置 /etc/profile

> export JAVA_16_HOME=/Users/zhaolangjing/Library/Java/JavaVirtualMachines/openjdk-16.0.1/Contents/Home  
> export JAVA_8_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_201.jdk/Contents/Home  
> export JAVA_HOME=$JAVA_8_HOME  
> alias jdk16="export JAVA_HOME=$JAVA_16_HOME"    
> alias jdk8="export JAVA_HOME=$JAVA_8_HOME"
>
> 更新配置 source /etc/profile
>

# 资料

[profile,bash_profile含义](https://blog.csdn.net/hnoysz/article/details/78666272)