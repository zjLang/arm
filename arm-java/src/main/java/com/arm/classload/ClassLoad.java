package com.arm.classload;

import sun.misc.Launcher;

import java.net.URL;

/**
 * @author zhaolangjing
 * @since 2021-3-26 20:32
 */
public class ClassLoad {

    public static void main(String[] args) {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println( urL.toExternalForm() );
        }

    }

}
