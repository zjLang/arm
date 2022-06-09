
zlj@zljdeMacBook-Pro ~ % /bin/bash -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"

               开始执行Brew自动安装程序 
              [cunkai.wang@foxmail.com] 
           ['2021-08-16 16:33:03']['11.4']
        https://zhuanlan.zhihu.com/p/111014448 


请选择一个下载镜像，例如中科大，输入1回车。
源有时候不稳定，如果git克隆报错重新运行脚本选择源。cask非必须，有部分人需要。
1、中科大下载源 2、清华大学下载源 3、北京外国语大学下载源  4、腾讯下载源（不推荐） 5、阿里巴巴下载源(不推荐 缺少cask源)  

请输入序号: 1


  你选择了中国科学技术大学下载源
  
！！！此脚本将要删除之前的brew(包括它下载的软件)，请自行备份。
->是否现在开始执行脚本（N/Y） Y

--> 脚本开始执行
 Mac os设置开机密码方法：
  (设置开机密码：在左上角苹果图标->系统偏好设置->用户与群组->更改密码)
  (如果提示This incident will be reported. 在用户与群组中查看是否管理员) 
==> 通过命令删除之前的brew、创建一个新的Homebrew文件夹
请输入开机密码，输入过程不显示，输入完后回车
Password:
开始执行
-> 创建文件夹 /usr/local/Homebrew
运行代码 ==> /usr/bin/sudo /bin/mkdir -p /usr/local/Homebrew
此步骤成功
运行代码 ==> /usr/bin/sudo /bin/chmod -R a+rwx /usr/local/Homebrew
运行代码 ==> /usr/bin/sudo /usr/sbin/chown zlj /usr/local/Homebrew
运行代码 ==> /usr/bin/sudo /usr/bin/chgrp admin /usr/local/Homebrew
git version 2.30.1 (Apple Git-130)

下载速度觉得慢可以ctrl+c或control+c重新运行脚本选择下载源
==> 克隆Homebrew基本文件

未发现Git代理（属于正常状态）
Cloning into '/usr/local/Homebrew'...
remote: Enumerating objects: 196642, done.
remote: Total 196642 (delta 0), reused 0 (delta 0)
Receiving objects: 100% (196642/196642), 50.79 MiB | 578.00 KiB/s, done.
Resolving deltas: 100% (146745/146745), done.
此步骤成功
--创建Brew所需要的目录
运行代码 ==> /usr/bin/sudo /bin/chmod u+rwx /usr/local/lib /usr/local/sbin
运行代码 ==> /usr/bin/sudo /bin/chmod g+rwx /usr/local/lib /usr/local/sbin
运行代码 ==> /usr/bin/sudo /usr/sbin/chown zlj /usr/local/lib /usr/local/sbin
运行代码 ==> /usr/bin/sudo /usr/bin/chgrp admin /usr/local/lib /usr/local/sbin
运行代码 ==> /usr/bin/sudo /bin/mkdir -p /usr/local/bin /usr/local/etc /usr/local/include /usr/local/share /usr/local/var /usr/local/opt /usr/local/share/zsh /usr/local/share/zsh/site-functions /usr/local/var/homebrew /usr/local/var/homebrew/linked /usr/local/Cellar /usr/local/Caskroom /usr/local/Frameworks
运行代码 ==> /usr/bin/sudo /bin/chmod g+rwx /usr/local/bin /usr/local/etc /usr/local/include /usr/local/share /usr/local/var /usr/local/opt /usr/local/share/zsh /usr/local/share/zsh/site-functions /usr/local/var/homebrew /usr/local/var/homebrew/linked /usr/local/Cellar /usr/local/Caskroom /usr/local/Frameworks
运行代码 ==> /usr/bin/sudo /usr/sbin/chown zlj /usr/local/bin /usr/local/etc /usr/local/include /usr/local/share /usr/local/var /usr/local/opt /usr/local/share/zsh /usr/local/share/zsh/site-functions /usr/local/var/homebrew /usr/local/var/homebrew/linked /usr/local/Cellar /usr/local/Caskroom /usr/local/Frameworks
运行代码 ==> /usr/bin/sudo /usr/bin/chgrp admin /usr/local/bin /usr/local/etc /usr/local/include /usr/local/share /usr/local/var /usr/local/opt /usr/local/share/zsh /usr/local/share/zsh/site-functions /usr/local/var/homebrew /usr/local/var/homebrew/linked /usr/local/Cellar /usr/local/Caskroom /usr/local/Frameworks
运行代码 ==> /usr/bin/sudo /usr/sbin/chown -R zlj:admin /usr/local/Homebrew
运行代码 ==> /usr/bin/sudo /bin/mkdir -p /Users/zlj/Library/Caches/Homebrew
运行代码 ==> /usr/bin/sudo /bin/chmod g+rwx /Users/zlj/Library/Caches/Homebrew
运行代码 ==> /usr/bin/sudo /usr/sbin/chown -R zlj /Users/zlj/Library/Caches/Homebrew
--依赖目录脚本运行完成
==> 创建brew的替身
==> 克隆Homebrew Core
此处如果显示Password表示需要再次输入开机密码，输入完后回车
Cloning into '/usr/local/Homebrew/Library/Taps/homebrew/homebrew-core'...
remote: Enumerating objects: 1021031, done.
remote: Total 1021031 (delta 0), reused 0 (delta 0)
Receiving objects: 100% (1021031/1021031), 412.03 MiB | 576.00 KiB/s, done.
Resolving deltas: 100% (705495/705495), done.
Updating files: 100% (6052/6052), done.
此步骤成功
==> 克隆Homebrew Cask 图形化软件
  此处如果显示Password表示需要再次输入开机密码，输入完后回车
Password:
Cloning into '/usr/local/Homebrew/Library/Taps/homebrew/homebrew-cask'...
remote: Enumerating objects: 638561, done.
remote: Total 638561 (delta 0), reused 0 (delta 0)
Receiving objects: 100% (638561/638561), 268.03 MiB | 580.00 KiB/s, done.
Resolving deltas: 100% (456234/456234), done.
此步骤成功
==> 克隆Homebrew services 管理服务的启停
  
Password:
Cloning into '/usr/local/Homebrew/Library/Taps/homebrew/homebrew-services'...
remote: Enumerating objects: 1141, done.
remote: Total 1141 (delta 0), reused 0 (delta 0), pack-reused 1141
Receiving objects: 100% (1141/1141), 331.50 KiB | 231.00 KiB/s, done.
Resolving deltas: 100% (484/484), done.
此步骤成功
==> 配置国内镜像源HOMEBREW BOTTLE
sed: /Users/zlj/.zprofile: No such file or directory
环境变量写入->/Users/zlj/.zprofile
此步骤成功
运行代码 ==> /usr/bin/sudo /bin/chmod -R a+rwx /usr/local/Homebrew
运行代码 ==> /usr/bin/sudo /usr/sbin/chown zlj /usr/local/Homebrew
运行代码 ==> /usr/bin/sudo /usr/bin/chgrp admin /usr/local/Homebrew

==> 安装完成，brew版本

Homebrew 3.2.6-123-g0e63fb8-dirty
Homebrew/homebrew-core (git revision 79ae2c5a35; last commit 2021-08-16)
Homebrew/homebrew-cask (git revision d215265d92; last commit 2021-08-16)
Brew前期配置成功
电脑系统版本：11.4
All user-space services OK, nothing cleaned...

  ==> brew update-reset
  
==> Fetching /usr/local/Homebrew...

==> Resetting /usr/local/Homebrew...
Branch 'master' set up to track remote branch 'master' from 'origin'.
Reset branch 'master'
Your branch is up to date with 'origin/master'.

==> Fetching /usr/local/Homebrew/Library/Taps/homebrew/homebrew-cask...
remote: Enumerating objects: 4, done.
remote: Counting objects: 100% (4/4), done.
remote: Compressing objects: 100% (3/3), done.
remote: Total 4 (delta 1), reused 4 (delta 1)
Unpacking objects: 100% (4/4), 4.89 KiB | 501.00 KiB/s, done.
From https://mirrors.ustc.edu.cn/homebrew-cask
   d215265d92..9af094a2d4  master     -> origin/master

==> Resetting /usr/local/Homebrew/Library/Taps/homebrew/homebrew-cask...
Branch 'master' set up to track remote branch 'master' from 'origin'.
Reset branch 'master'
Your branch is up to date with 'origin/master'.

==> Fetching /usr/local/Homebrew/Library/Taps/homebrew/homebrew-core...
remote: Enumerating objects: 64, done.
remote: Counting objects: 100% (64/64), done.
remote: Compressing objects: 100% (21/21), done.
remote: Total 64 (delta 43), reused 64 (delta 43)
Unpacking objects: 100% (64/64), 25.68 KiB | 106.00 KiB/s, done.
From https://mirrors.ustc.edu.cn/homebrew-core
   79ae2c5a35..cf1eaf6b1d  master     -> origin/master

==> Resetting /usr/local/Homebrew/Library/Taps/homebrew/homebrew-core...
Updating files: 100% (5789/5789), done.
Branch 'master' set up to track remote branch 'master' from 'origin'.
Reset branch 'master'
Your branch is up to date with 'origin/master'.

==> Fetching /usr/local/Homebrew/Library/Taps/homebrew/homebrew-services...

==> Resetting /usr/local/Homebrew/Library/Taps/homebrew/homebrew-services...
Branch 'master' set up to track remote branch 'master' from 'origin'.
Reset branch 'master'
Your branch is up to date with 'origin/master'.


        Brew自动安装程序运行完成
          国内地址已经配置完成

  桌面的Old_Homebrew文件夹，大致看看没有你需要的可以删除。

              初步介绍几个brew命令
本地软件库列表：brew ls
查找软件：brew search google（其中google替换为要查找的关键字）
查看brew版本：brew -v  更新brew版本：brew update
安装cask软件：brew install --cask firefox 把firefox换成你要安装的
        
        欢迎右键点击下方地址-打开URL 来给点个赞
         https://zhuanlan.zhihu.com/p/111014448 

 重启终端 或者 运行 source /Users/zlj/.zprofile   否则可能无法使用
  
