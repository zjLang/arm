# [使用手册](../framework/springJPA.xmind)

**参考项目 arm-boot-demo**

## 1.基本概念和操作

+ 1.导入maven：

```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```

+ 2.jpa 默认使用数据库链接池：hikari 默认使用ORM ：hibernate 默认集成spring-tx。
+ 3.application.yml配置参考：
+ 4.参考链接

| 类型  | url                                             |                          备注 |
|:----|:------------------------------------------------|----------------------------:|
| 配置  | https://www.cnblogs.com/chenmfly/p/4295578.html |              spring xml配置方式 |
| 配置  | https://my.oschina.net/itwarcraft/blog/339253   | spring4 jpa hibernate4配置，重要 |
| 事务  | https://blog.51cto.com/u_15127701/2887908       |               事务conection说明 |

+ 5.spring boot配置

```
spring:
  devtools:
    restart:
      enabled: true #设置开启热部署
  jpa:
    hibernate:
      #ddl-auto: create  # 每次加载 hibernate 时都会删除上一次的生成的表，然后根据你的 model 类再重新来生成新表
      #ddl-auto: create-drop  # 每次加载 hibernate 时根据 model 类生成表，但是 sessionFactory 一关闭,表就自动删除。
      ddl-auto: update  # 最常用的属性，第一次加载 hibernate 时根据 model 类会自动建立起表的结构（前提是先建立好数据库），以后加载 hibernate 时根据 model 类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
      #ddl-auto: validate  # 每次加载 hibernate 时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect  # 默认使用的myISAM, 配置使用innoDB引擎

  datasource: # 不配置使用默认的链接池：Hikari
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: wisesoft
    url: jdbc:mysql://172.16.9.19:3306/zlj_tets?useUnicode=true&characterEncoding=utf-8&useOldAliasMetadataBehavior=true
    hikari:
      connection-test-query: select 1
```

+ spring4 + jpa + hibernate4配置

```
    1.pom引用 说明：
        1.spring-data-jpa 2版本以上需要spring5的支持。
        2.spring4 + jpa1 需要hibernate4版本  hibernate-core5以上的版本不需要引用hibernate-entitymanager，因为core集成了entitymanager里面的东西。
        
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>1.11.10.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>4.3.6.Final</version> <!--4.3.5.Final 5.2.17.Final-->
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>4.3.6.Final</version>
        </dependency>
        
    2.spring-xml集成配置 ：在实际使用中我实际使用了 jdbcTempalate 和jpa两种orm框架。
     <jpa:repositories base-package="com.wisesoft.datapipeline.standard.pyramid"
                      transaction-manager-ref="jpaTransactionManager"
                      entity-manager-factory-ref="entityManagerFactory"/>

    <bean id="entityManagerFactory"
          class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 指定Jpa持久化实现厂商类,这里以Hibernate为例 -->
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>
        </property>
        <!-- 指定Entity实体类包路径 -->
        <property name="packagesToScan" value="com.wisesoft.datapipeline.standard.pyramid"/>
        <!-- 指定JPA属性；如Hibernate中指定是否显示SQL的是否显示、方言等 -->
        <property name="jpaProperties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
                <!--<prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>-->
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.format_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
                <!-- 很重要，不然会报：no transaction is in progress 错误  -->
                <prop key="hibernate.allow_update_outside_transaction">true</prop>
            </props>
        </property>

        <!--<property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <property name="showSql" value="true"/>
                <property name="generateDdl" value="true"/>
                &lt;!&ndash;<property name="database" value="MYSQL"/>&ndash;&gt; &lt;!&ndash;设置方言的方式&ndash;&gt;
            </bean>
        </property>-->
    </bean>

    <bean id="jpaTransactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>
    
    <!-- 不配置将报错： org.springframework.transaction.CannotCreateTransactionException:
     Could not open JPA EntityManager for transaction; nested exception is java.lang.IllegalStateException:
      Already value [org.springframework.jdbc.datasource.ConnectionHolder@784c7dd1] for key 
      [com.wisesoft.activiti.core.datasource.ActivitiDynamicDataSource@4a95bbf1] bound to thread 
      [http-nio-8080-exec-9]
      从该错误中可以 
      -->
    <tx:annotation-driven transaction-manager="jpaTransactionManager" proxy-target-class="true"/>

```

## 2.自定义id生成策略

```
    1.实现 org.hibernate.id.IdentifierGenerator 接口
    public class UuidGenerator implements IdentifierGenerator {
        @Override
        public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
            BaseEntity entity = (BaseEntity) object; // 处理预先设置的id被置换的问题
            return !StringUtils.isEmpty(entity.getId())? entity.getId():Util.getUUID();
        }
    }
    
    2.父类字段继承问题，父类注解：@MappedSuperclass 
    
    3.实体类配置
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.arm.demo.UuidGenerator")
    private String id;
```

## 3.jpa与事务的结合与验证

## 4.表格整理

### hibernate方法设置（database-platform）

| DB类型   | 方言类                                       |    备注 |
|:-------|:------------------------------------------|------:|
| mysql  | org.hibernate.dialect.MySQL5InnoDBDialect |   三方库 |
| sqlite | com.arm.demo.SQLiteDialect                | 自定义实现 |

## 5.sql整理

```
1.查询int：
    @Query("select max(age) from User order by age")
    int getMaxAge();    

2.jpa提供的saveAll方法也是"更新数据"的方法
    public boolean updateUser() {
        List<User> all = userDao.findAll();
        for (User user : all) {
            user.setAge(100);
        }
        userDao.saveAll(all);
        return true;
    }
```

## 6.jpa save、saveAll方法原理

+ 重要文章：https://www.modb.pro/db/51252 , https://www.jianshu.com/p/11153affb528

```
    1.JPA saveAll方法底层默认是循环调用save方法。
    查看：org.springframework.data.jpa.repository.support.SimpleJpaRepository#saveAll
    
    2.save方法中的 "entityInformation.isNew(entity)" 方法仅仅是判断是否设置了id值，网上很多说法都是错的。
    
    3.重点：merge方法执行：org.hibernate.event.internal.DefaultMergeEventListener#onMerge(arg1,arg2)
    
    public void onMerge(MergeEvent event, Map copiedAlready) throws HibernateException {

		final MergeContext copyCache = (MergeContext) copiedAlready;
		final EventSource source = event.getSession();
		final Object original = event.getOriginal();

		if ( original != null ) {

			final Object entity;
			// 1.判断original是否是HibernateProxy代理。一般都不是，可以略过
			if ( original instanceof HibernateProxy ) {
				LazyInitializer li = ( (HibernateProxy) original ).getHibernateLazyInitializer();
				if ( li.isUninitialized() ) {
					LOG.trace( "Ignoring uninitialized proxy" );
					event.setResult( source.load( li.getEntityName(), li.getIdentifier() ) );
					return; //EARLY EXIT!
				}
				else {
					entity = li.getImplementation();
				}
			}
			else {
				entity = original;
			}
            // 2.查看是否已经提前做了merge操作
			if ( copyCache.containsKey( entity ) &&
					( copyCache.isOperatedOn( entity ) ) ) {
				LOG.trace( "Already in merge process" );
				event.setResult( entity );
			}
			// 3.重点从这里开始
			else {
				if ( copyCache.containsKey( entity ) ) {
					LOG.trace( "Already in copyCache; setting in merge process" );
					copyCache.setOperatedOn( entity, true );
				}
				event.setEntity( entity );
				EntityState entityState = null;

				// 4.检测session上下文中是否已经有该需要持久化数据的实体（即在保存数据前已经做了查询等操作）这部分和事务的控制十分紧密
				EntityEntry entry = source.getPersistenceContext().getEntry( entity );
				if ( entry == null ) {
					EntityPersister persister = source.getEntityPersister( event.getEntityName(), entity );
					Serializable id = persister.getIdentifier( entity, source );
					if ( id != null ) {
						final EntityKey key = source.generateEntityKey( id, persister );
						final Object managedEntity = source.getPersistenceContext().getEntity( key );
						entry = source.getPersistenceContext().getEntry( managedEntity );
						if ( entry != null ) {
							// we have specialized case of a detached entity from the
							// perspective of the merge operation.  Specifically, we
							// have an incoming entity instance which has a corresponding
							// entry in the current persistence context, but registered
							// under a different entity instance
							entityState = EntityState.DETACHED;
						}
					}
				}

				if ( entityState == null ) {
					entityState = getEntityState( entity, event.getEntityName(), entry, source );
				}
                // 5.通过不同的状态做不同的处理 transient（自由态）,persistent（持久态）,detached（托管态）
                //  1：若new的对象id为null，在数据库中没有与之匹配的记录，没有纳入session的管理，则为transient状态。
                //  2：若new的对象id不为null，在数据库中有与之匹配的记录，没有纳入session的管理，则为detached状态。
                //  3：对象session close之后，由persistent变为detached状态。
				switch ( entityState ) {
					case DETACHED:
					    // 6.未命中缓存中操作，该方法中会再去查询数据库，去判断数据是insert还是update。
					    // 更新未查询过的数据（即sessionContext没有缓存）或 保存新数据
					    // 该方法中有调用session.get()方法去数据库获取数据以判断该数据是更新还是保存等操作。
						entityIsDetached( event, copyCache );
						break;
					case TRANSIENT:
						entityIsTransient( event, copyCache );
						break;
						// 7.PERSISTENT，DELETED 都是命中缓存的操作。该操作就不会再去查询数据库了。
					case PERSISTENT:
						entityIsPersistent( event, copyCache );
						break;
					default: //DELETED
						throw new ObjectDeletedException(
								"deleted instance passed to merge",
								null,
								getLoggableName( event.getEntityName(), entity )
						);
				}
			}

		}

	}
    
    4.
    
```



# 项目测试
## 事务测试