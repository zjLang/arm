#### spring在事务 DataSourceTransactionManager JpaTransactionManager同时存在的情况下，是如何做到事务同步的？

+ 问题

```
    配置：
    <bean id="dataSource" class="com.wisesoft.activiti.core.datasource.ActivitiDynamicDataSource">
        ...  
    </bean> // 数据源配置
    
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 所有方法以transaction结未都开启事务, 并且使用默认的事务冒泡类型"REQUIRED" 默认事务管理器：transactionManager -->
    <tx:advice id="txAdvice">
        <tx:attributes>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="query*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="list*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*"/> <!--propagation="REQUIRED" 默认-->
        </tx:attributes>
    </tx:advice>
    <!-- 事务切面 默认事务管理器：transactionManager -->
    <aop:config expose-proxy="true" proxy-target-class="true">
        <aop:pointcut id="txPointcut1" expression="execution(* com.wisesoft..service..*.*(..))"/>
        <aop:pointcut id="txPointcut2" expression="execution(* com.coyee.core..service..*.*(..))"/>
        <aop:advisor id="txAdvisor1" advice-ref="txAdvice" pointcut-ref="txPointcut1"/>
        <aop:advisor id="txAdvisor2" advice-ref="txAdvice" pointcut-ref="txPointcut2"/>
    </aop:config>
    
    <-- DataSourceTransactionManager 事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    
    
    <bean id="jpaTransactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean> 
    
    <tx:annotation-driven transaction-manager="jpaTransactionManager" proxy-target-class="true"/>
    
    
    entityManagerFactory 中管理的仍然是 dataSource数据源
```

+ 解析

```
发现问题：
 1.首先我在一个方法里面调用了调用两个不同的代理dao。一个通过jdbcTemplate方式另一个通过springJPA方式。然后我就跟踪代码，发现在jdbcTemplate中
 获取的jdbc链接：ConnectionHolder内联的对象居然是sessionImpl。看到这里我隐隐知道是什么问题了。
    这里的重点：DataSourceUtils.doGetConnection(DataSource dataSource) -> ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
 
 2.那么这里为什么获取到的 ConnectionHolder会是内联的sessionImpl呢？
    这里我们来分析一个比较重要的类：TransactionSynchronizationManager 该类 事务同步管理器，该类用于管理当前线程的事务资源。
 该类有三类方法：getResource  bindResource  unbindResource 显然在获取链接时调用方法getResource 那设置就是bindResource方法

 3.那bindResource方法又是那里调用的呢？
    DataSourceTransactionManager在doBegin()中调用了 TransactionSynchronizationManager.bindResource(getDataSource(), txObject.getConnectionHolder());
 JpaTransactionManager在doBegin()中调用了 TransactionSynchronizationManager.bindResource(getDataSource(), txObject.getConnectionHolder());
 从上面就可以看出了：我们在事务开启的方法中设置了相关资源。那么问题就变成了为什么在事务开启时是由那个事务管理器加载的呢？ 
    
 4.我们回到事务开始执行的地方，跟踪事务执行过程：TransactionInterceptor.invoke() -> invokeWithinTransaction()
   我们来分析一下下面一段代码：
    // 1.通过当前被代理的方法（一般是service方法）获取事务属性。 eg：PROPAGATION_REQUIRED,ISOLATION_DEFAULT; '',-java.lang.Exception
    final TransactionAttribute txAttr = getTransactionAttributeSource().getTransactionAttribute(method, targetClass);
    // 2.通过事务属性获取一个事务管理器， 在determineTransactionManager方法中执行：
        else if (StringUtils.hasText(this.transactionManagerBeanName)) {
			return determineQualifiedTransactionManager(this.transactionManagerBeanName);
		} 这里的  transactionManagerBeanName = jpaTransactionManager
		
    final PlatformTransactionManager tm = determineTransactionManager(txAttr);
    // 3.暂不分析
    final String joinpointIdentification = methodIdentification(method, targetClass, txAttr);
    if (txAttr == null || !(tm instanceof CallbackPreferringPlatformTransactionManager)) {
        // 在这里开启事务，并调用begin()方法。
        TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
        ......
    }
    
 5.那么，在上述步骤中，为什么 transactionManagerBeanName 会被设置成 jpaTransactionManager？
    这里我们又要说回TransactionInterceptor类了，这个类通过springAOP后，生成了需要被代理的对象。从每次的debug TransactionInterceptor的对象地址不一可知。
 即：spring在创建jpa的事务相关代理的时候，就会分析该类的事务类型。设置每个被代理对象(TransactionInterceptor)的基本属性。
 在上诉配置中，我们的tx:annotation-driven注解驱动上面加了代理是 jpaTransactionManager。而我们的执行方法是打了@Transaction注解的。
 未加注解的则使用的是事务注解切面配置。
   
问题分析整理：
 1.从配置开始，spring进行AOP代码对象的初始化，并根据配置设置对应的属性。
 2.事务AOP代理从 TransactionInterceptor.invoke()方法开始，在其中通过方法配置设置事务属性，通过事务属性获取事务管理器，然后开启事务进行后续操作。
 3.结论：一个事务链执行过程中，只能有一个事务管理器去管理该线程的事务。如果在方法调用中使用了多源事务管理器（多源事务管理的同一个数据源（DataSource））
    ，事务就会就会报已下异常：
    Could not open JPA EntityManager for transaction; nested exception is java.lang.IllegalStateException: Already value [org.springframework.jdbc.datasource.ConnectionHolder@54c50afc] for key [com.wisesoft.activiti.core.datasource.ActivitiDynamicDataSource@2d9b8b59] bound to thread [http-nio-8089-exec-12]
    该异常出现的原因是：普通的service方法（M1）根据事务切面进行事务的管理，此时她被DataSourceTransactionManager管理，该方法通过DataSourceTransactionManager
    开启事务（begin()方法）。M1方法调用了被注解标记的service方法（M2）。M2被JpaTransactionManager管理，其也去执行（begin()方法）开启事务，造成报错。
    
 4.通过上述分析，可以得出结论：JpaTransactionManager可以管理普通的jdbc链接器（JdbcTemplate）。上诉配置可以被简化成：

    <bean id="dataSource" class="com.wisesoft.activiti.core.datasource.ActivitiDynamicDataSource">
        ...  
    </bean> // 数据源配置
    
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 所有方法以transaction结未都开启事务, 并且使用默认的事务冒泡类型"REQUIRED" 默认事务管理器：transactionManager -->
    <tx:advice id="txAdvice" >
        <tx:attributes>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="query*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="list*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*"/> <!--propagation="REQUIRED" 默认-->
        </tx:attributes>
    </tx:advice>
    <!-- 事务切面 默认事务管理器：transactionManager -->
    <aop:config expose-proxy="true" proxy-target-class="true">
        <aop:pointcut id="txPointcut1" expression="execution(* com.wisesoft..service..*.*(..))"/>
        <aop:pointcut id="txPointcut2" expression="execution(* com.coyee.core..service..*.*(..))"/>
        <aop:advisor id="txAdvisor1" advice-ref="txAdvice" pointcut-ref="txPointcut1"/>
        <aop:advisor id="txAdvisor2" advice-ref="txAdvice" pointcut-ref="txPointcut2"/>
    </aop:config>
    
    <-- DataSourceTransactionManager 事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    
    
    <bean id="jpaTransactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean> 
    
    <tx:annotation-driven transaction-manager="jpaTransactionManager" proxy-target-class="true"/>
    
 5.从上述我们知道了，不同事务管理器可以管理同一数据源？同一事务管理器能否管理不同数据源呢？
 其实从配置transactionManager的bean就可以看出来，这是无法实现的。
```

| 序号  | 结论                           | 
|:----|:-----------------------------|
| 1   | 事务管理器绑定数据源（dataSource）来管理事务， | 
