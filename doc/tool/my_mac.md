@[TOC](目录)

# 配置

## 电源配置（pmset -g custom）

+ 默认配置（https://zhuanlan.zhihu.com/p/159468988）

```
Currently in use:
     standbydelaylow      10800
     standby              1
     halfdim              1
     hibernatefile        /var/vm/sleepimage
     proximitywake        0
     powernap             0
     gpuswitch            2
     disksleep            10
     standbydelayhigh     86400
     sleep                1
     hibernatemode        3
     ttyskeepawake        1
     displaysleep         2
     tcpkeepalive         1
     highstandbythreshold 50
     acwake               0
     lidwake              1
```

+ 修改命令

```
 修改命令：
    sudo pmset -b hibernatemode 25 
    sudo pmset -b tcpkeepalive 0 
 命令说明：
    hibernatemode一般不建议修改，如果要修改只建议改为0、3或25。
    hibernatemode = 0 台式机默认支持。系统将不会备份内存到持久化存储。系统必须从内存内容中唤醒；当断电时系统将会失去上下文。这是传统的普通睡眠方式。
    hibernatemode = 3 便携式计算机默认支持。系统将存储一份内存的备份到持久化存储（磁盘）中，并且在睡眠过程中持续给内存供电。系统将从内存中被唤醒，除非断电才强制从磁盘镜像会恢复。
    hibernatemode = 25 只能通过 pmset 才可以设置。系统将存储一份内存的备份到持久化存储（磁盘）中，并且将会给内存断电。系统将从磁盘镜像中恢复。如果你希望“休眠”——慢一点但是有益电池寿命，你应该使用这个设置。
    tcpkeepalive = 0 盒盖断网
    tcpkeepalive = 1 盒盖不断网
```

## homebrew（telnet...）

> 作用：Homebrew是一款Mac OS平台下的软件包管理工具，拥有安装、卸载、更新、查看、搜索等很多实用的功能。简单的一条指令，就可以实现包管理，
> 而不用你关心各种依赖和文件路径的情况，十分方便快捷。

#### 代码：

+ 安装任意包

```
    brew install <packageName>
    brew install telnet
    brew install node
```

+ 卸载任意包

```
    brew uninstall <packageName>
    brew uninstall git
```

+ 查询可用包 `brew search <packageName>`

+ 查看已安装包列表 `brew list`

+ 查看任意包信息 `brew info <packageName>`

+ 更新Homebrew `brew update`

+ 查看Homebrew版本 `brew -v`

+ Homebrew帮助信息 `brew -h`

#### 安装：

+ brew镜像安装地址：

```
 bin/bash -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"
```

+ 图形管理工具Cakebrew安装

```
 https://www.cakebrew.com/
```

+ [安装运行结果](/doc/data/img/brew-install.md)

+ 参考地址

```
 https://my.oschina.net/u/4198095/blog/5003400
 https://gitee.com/cunkai/HomebrewCN/
 https://juejin.cn/post/6844903840202899464
```

## pd安装

+ win10安装无法联网：sudo -b /Applications/Parallels\ Desktop.app/Contents/MacOS/prl_client_app
+ https://qiujunya.com/article/2020/9/9/103.html

## mongodb安装
```
1.安装
> cd /usr/local 
> sudo curl -O https://fastdl.mongodb.org/osx/mongodb-osx-ssl-x86_64-4.0.9.tgz
> sudo tar -zxvf mongodb-osx-ssl-x86_64-4.0.9.tgz
> sudo mv mongodb-osx-x86_64-4.0.9/ mongodb

2.设置环境变量
> export PATH=/usr/local/mongodb/bin:$PATH

3.设置db数据和日志保存路径，并给用户权限
> sudo mkdir -p /usr/local/var/mongodb
> sudo mkdir -p /usr/local/var/log/mongodb
> sudo chown zhaolangjing /usr/local/var/mongodb
> sudo chown zhaolangjing /usr/local/var/log/mongodb

4.启动 
> mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log --fork
--dbpath 设置数据存放目录
--logpath 设置日志存放目录
--fork 在后台运行
> ps aux | grep -v grep | grep mongod

> 5.打开操作终端
$ cd /usr/local/mongodb/bin 
$ ./mongo
```