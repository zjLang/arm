package com.arm.language;

/**
 * @author zhaolangjing
 * @since 2021-3-4 10:50
 */
public class ParentClass extends AbstractClass {

    static {
        System.out.println( "execute parent static code " + ParentClass.class.getName() );
    }

    public ParentClass() {
        System.out.println( "execute parent constructor " + ParentClass.class.getName() );
    }


}
