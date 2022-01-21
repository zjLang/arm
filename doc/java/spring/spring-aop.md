## aop

## 带着问题阅读

+ 1.springAop 有几种创建方式 ？
+ 2.springAop 如何创建的aop启动器：AbstractAdvisorAutoProxyCreator？
+ 3.springAop 如何寻找到被标记需要被代理的类？
+ 4.springAop 如何处理多增强器问题，代理创建过程以及如何选择代理创建策略？
+ 5.springAop 如何执行，执行链是怎么样的，多增强器如何执行？
+ 6.aop代理可以分为哪些步骤

#### 基本概念

+ 1.面试切面的编程，旨在减少重复的代码

## 问题解答

### 问题6

```
可大致分为以下几步：
    1.容器启动，创建aop启动处理器
    2.通过JDK或者cglib创建代理类
    3.方法执行
```


