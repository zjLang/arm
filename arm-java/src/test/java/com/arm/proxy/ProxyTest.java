package com.arm.proxy;

/**
 * @author zhaolangjing
 * @since 2021-3-22 16:00
 */
public class ProxyTest {
    public static void main(String[] args) throws Throwable {
        Boy boy = new Boy();
        MyProxyHandler myProxyHandler = new MyProxyHandler();
        myProxyHandler.newInstance( boy );
        myProxyHandler.invoke( boy, boy.getClass().getMethod( "run", null ), null );
    }
}
