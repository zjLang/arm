### 1.dubbo-spring实现机制
##### 1.加载 （以ServiceBean为列）
+ 1. dubbo配置了 spring.handlers，spring.schemas 扩展项。
  2. 指定了扩展namespace类：DubboNamespaceHandler extends NamespaceHandlerSupport  
  3. ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory(); 加载bean定义

+ 实例化单实例beanfinishBeanFactoryInitialization(beanFactory) ->beanFactory.preInstantiateSingletons()
  在初始化bean的过程中：
  1.执行setApplicationContext() , 设置上下文和将监听器（serviceBean）加载到spring的上下文。
  2.调用spring提供的BeanFactoryUtils.beansOfTypeIncludingAncestors 依次初始化了ProviderConfig，ApplicationConfig，ModuleConfig，RegistryConfig，MonitorConfig，ProtocolConfig
  
##### 2.调用端实列生成过程
> GoodsService goodsService = (GoodsService) context.getBean( "goodsService" );
+ 1.调用spring的getBean()方法。这里spring把所有的注册接口都实例化了ReferenceBean。ReferenceBean是一个实现FactoryBean的工厂类，根据接口配置信息去创建一个接口代理类。
+ 2.ReferenceBean.getObject()。通过该方法生成接口代理

##### 3.客户端调用
+ 1.服务已经被代理，其持有一个handler属性=>InvokerInvocationHandler 调用handler.invoke()方法。调用的toString,hashCode,equals等方法直接调用对象方法返回。
+ 2.调用MockClusterInvoker.invoke()。其中判断非mock就走非mock逻辑。如果是mock调用就进行mock逻辑调用。
+ 3.继续往下，则是根据不同的cluster策略，调用具体的实现类的invoke()。
    在这之前先调用的AbstractClusterInvoker.invoke()。这一步做了一个很重要的工作：根据spi机制加载均衡策略。
    然后调用子类doInvoke方法，实现调用。子类决定了调用机制

##### 4.spi调用机制
> loadbalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(invokers.get(0).getUrl()
                      .getMethodParameter(RpcUtils.getMethodName(invocation), Constants.LOADBALANCE_KEY, Constants.DEFAULT_LOADBALANCE));
+ 以上诉调用说明：ExtensionLoader.getExtensionLoader。这里获取一种类型的加载器，如果有就返回，没有就创建一个并返回。
+ getExtension：初始化调用过程：getExtension()->createExtension()->getExtensionClasses()/injectExtension()
    + getExtensionClasses 机制说明：1.这里通过loadBalance类的全限定类名在/META-INF/services /META-INF/dubbo/ /META-INF/servicesinternal/目录下寻找文件
    2.寻找到了之后通过读取每一个行接续并把类加载到对应的缓存集合种。
    + injectExtension createExtension方法中通过name参数获取对应的class（接口实现）实例，创建对象，返回创建的对象。
    
### 2.dubbo负载均衡策略

LoadBalance

+ Random 随机策略（可配置权重）

+ RoundRobin  轮询策略

+ ShortestResponse  dubbo 3.0新加策略 ，请求响应最快策略

+ lastActive  最少活跃调用

+ ConsistentHash  一致性哈希

###  3.cluster实现策略

###### 集群容错

+ failOver  失败重随，失败后重新选择调用节点执行[retries]设置 默认为2次 。 默认
+ failSafe  失败安全，失败之后直接记录日志，返回结果。用于审计日志等。
+ failFast  快速失败，失败后直接抛出异常。 通常用于非幂等性的写操作，比如新增记录
+ failBack  失败定时，失败后将任务加入RetryTimerTask，定时重发。大于[retries]后放弃。
+ forking   定时调用多个服务，调用服务数以[forks]指定，只要有一个返回了就返回结果。
+ broadCast 广播调用所有提供者，逐个调用，任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息。（项目mq管理器）
+ available 可用性调用，循环找到活着的可调用节点进行调用并返回结果
+ mergeable 收集结果，循环调用所有节点，采用线程池方式，多线程调用，比如菜单服务，接口一样，但有多种实现，用group区分，现在消费方需从每种group中调用一次返回结果，合并结果返回，这样就可以实现聚合菜单项
+ mockCluster 本地伪装，服务降级，比如某验权服务，当服务提供方全部挂掉后，客户端不抛出异常，而是通过 Mock 数据返回授权失败
+ https://zhuanlan.zhihu.com/p/106140083



