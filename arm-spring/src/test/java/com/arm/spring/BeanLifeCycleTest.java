package com.arm.spring;

import com.arm.spring.aop.IBuy;
import com.arm.spring.bean.Dog;
import com.arm.spring.event.Cat;
import com.arm.spring.event.MasterPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean的生命周期测试
 * https://www.cnblogs.com/zrtqsk/p/3735273.html
 * <p>
 * https://www.jianshu.com/p/1dec08d290c1
 * <p>
 * https://www.jianshu.com/p/9ea61d204559
 *
 * @author zhaolangjing
 * @since 2021-3-12 14:42
 */
@Slf4j
public class BeanLifeCycleTest {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( "classpath:spring-bean.xml" );

    @Test
    public void test() {
        log.debug( "context init over" );
        Dog dog = (Dog) context.getBean( "dog" );
        context.close();
        log.debug( dog.toString() );
    }

    @Test
    public void testEvent() {
        //Collection<ApplicationListener<?>> applicationListeners = context.getApplicationListeners();
        //applicationListeners.stream().forEach( v -> log.info( v.getClass().getName() ) );
        MasterPublisher masterPublisher = (MasterPublisher) context.getBean( "masterPublisher" );
        masterPublisher.sendEvent( new Cat( new Object() ) );
    }

    @Test
    public void testAop() {
        IBuy boy = (IBuy) context.getBean( "boy" );
        boy.buy( 0 );
    }


}
