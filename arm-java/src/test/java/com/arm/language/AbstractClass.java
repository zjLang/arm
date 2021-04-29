package com.arm.language;

/**
 * @author zhaolangjing
 * @since 2021-3-4 14:07
 */
public class AbstractClass {

    static {
        System.out.println( "execute abstract static code " + AbstractClass.class.getName() );
    }

    public AbstractClass() {
        System.out.println( "execute abstract constructor " + AbstractClass.class.getName() );
    }


}
