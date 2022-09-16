# git操作文档

## git基本操作约定

⼀、提交格式 type （scope）: message ⼆、参数介绍：

+ 1、type：指的代码的提交类型，不同的提交类型表⽰对应不同的代码改动，⽐如：
    - feat：新功能的开发
    - fix：bug的修复
    - docs：⽂档格式的改动
    - style：代码格式改变
    - refactor：对已有的功能进⾏重构
    - perf：性能优化
    - test：增加测试
    - build：改变了build⼯具
    - revert：撤销上⼀次的commit提交
    - chore：构建过程或辅助⼯具的变动
+ 2、scope：⽤于说明commit影响的范围，⽐如：权限模块、还是⾸页

## idea新建git并push到git

+ 1.创建maven项目。
+ 2.选中目录并选中vcs -> lmport into Version Control -> Create Git Repository。
  ![avatar](../data/img/git/git-1.png)
+ 3.创建一个忽略文件用来添加需要被忽略文件。
  ![avatar](../data/img/git/git-2.png)
+ 4.选择提交项目 vcs -> commit。
+ 6.在git上面创建一个repositories。注意是repositories不是project。
+ 7.选择push项目到git，vcs -> push。注意这里使用git协议，别用http，可能遇到未知错误😂。
  ![avatar](../data/img/git/git-3.png)

### 错误1： Failed to connect to github.com port 443: Operation timed out

> 描述：idea clone,pull代码一直报错,没发成功，报上述错误 。
> 处理办法：首先的访问github需要梯子，在梯子的情况下还出现上诉错误。找到socket代码端口，命令行输入如下两行代码：
> git config --global http.proxy http://127.0.0.1:7890
> git config --global https.proxy https://127.0.0.1:7890
> 使用命令：cat ~/.gitconfig 查看。

## idea切换分支

> 右下脚点击git分支，然后选择本地分支中（common local branches）的一个，单击选择 checkout 。