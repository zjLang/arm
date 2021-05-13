package com.arm.dds.core.core;

public class DynamicDataSourceException extends Exception {

    public DynamicDataSourceException() {
    }

    public DynamicDataSourceException(String info) {
        super(info);
    }

    public DynamicDataSourceException(String info, Exception e) {
        super(info, e);
    }
}
