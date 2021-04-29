package com.arm.language;

/**
 * @author zhaolangjing
 * @since 2021-3-4 11:35
 */
public class ChildClass extends ParentClass {

    static {
        System.out.println( "execute child static code " + ChildClass.class.getName() );
    }


    public ChildClass() {
        System.out.println( "execute child constructor " + ChildClass.class.getName() );
    }

    /**
     * out:
     * <p>
     * execute parent static code com.arm.language.ParentClass
     * execute child static code com.arm.language.ChildClass
     * execute parent constructor com.arm.language.ParentClass
     * execute child constructor com.arm.language.ChildClass
     * <p>
     * 子类构造函数自动调用父类构造函数
     *
     * @param args
     */
    public static void main(String[] args) {
        new ChildClass();
    }
}
