package com.arm.reflection;

/**
 * @author zhaolangjing
 * @since 2021-3-20 22:50
 */
public class MyClassLoader extends ClassLoader {

    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super( parent );
    }
}
