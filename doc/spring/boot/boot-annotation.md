# boot annotations

## Conditional 家族

+ **ConditionalOnClass** 当某些类存在时满足条件
+ **ConditionalOnBean** 当某些bean存在时满足条件
+ ConditionalOnCloudPlatform 当某个云平台存在时满足条件
+ ConditionalOnExpression 当某个springEL表达式满足时
+ ConditionalOnJava 当某个java版本匹配时满足条件 @ConditionalOnJava(JavaVersion.NINE)
+ ConditionalOnJndi 当某个JNDI条件满足时 @ConditionalOnJndi({ "java:/JmsXA", "java:/XAConnectionFactory" })
+ ConditionalOnMissingBean 当某些bean缺失时满足条件
+ ConditionalOnMissingClass 当某些类不存在时满足条件
+ **ConditionalOnProperty** 当某个属性满足值满足时
+ ConditionalOnResource 当某些资源存在时
+ ConditionalOnSingleCandidate bean实例为单列或者是满足单个实例的条件
+ ConditionalOnWarDeployment 当应用以war包条件部署时
+ ConditionalOnNotWebApplication 当不是一个应用程序时满足条件
+ ConditionalOnWebApplication 当是一个应用程序时满足条件

