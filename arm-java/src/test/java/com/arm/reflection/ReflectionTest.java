package com.arm.reflection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhaolangjing
 * @since 2021-3-20 21:07
 */
@Slf4j
public class ReflectionTest {
    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        // 错误写法，这样并不能用自定义加载类加载文件。
        //MyClassLoader myClassLoader = new MyClassLoader();
        /*new Thread(()->{
            Thread.currentThread().setContextClassLoader( myClassLoader );
            ClassLoader classLoader = Reflection.class.getClassLoader();
            System.out.println(classLoader.getClass().getSimpleName());
        }).start();*/

        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> startupClass = myClassLoader.loadClass("com.arm.reflection.Reflection");
        Reflection reflection = (Reflection)startupClass.newInstance();
        System.out.println(reflection.getClass().getClassLoader().getClass().getSimpleName());



        Class<Reflection> reflectionClass = Reflection.class;
        Method[] methods = Reflection.class.getMethods();
        for (Method method : methods) {
            log.info( "method:" + method.getName() );
            log.info( "method:" + method.getReturnType() );
        }
        Method method = Reflection.class.getMethod( "method", null );
        //Reflection reflection = new Reflection();
        log.info( method.invoke( reflection, null ).toString() );
    }
}
