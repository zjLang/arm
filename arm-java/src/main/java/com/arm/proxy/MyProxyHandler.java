package com.arm.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhaolangjing
 * @since 2021-3-22 15:49
 */
@Slf4j
public class MyProxyHandler implements InvocationHandler {

    private Object targetObject;

    /**
     * 编织代理对象
     * @param targetObject
     * @return
     */
    public Object newInstance(Object targetObject) {
        //targetObject.getClass().getClassLoader()：被代理对象的类加载器
        //targetObject.getClass().getInterfaces()：被代理对象的实现接口
        //this 当前对象，该对象实现了InvocationHandler接口所以有invoke方法，通过invoke方法可以调用被代理对象的方法
        this.targetObject = targetObject;
        return Proxy.newProxyInstance( targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this );
    }

    /**
     * 执行代码程序
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info( "do something" );
        Object invoke = method.invoke( proxy, args );
        log.info( "do something end" );
        return invoke;
    }
}
