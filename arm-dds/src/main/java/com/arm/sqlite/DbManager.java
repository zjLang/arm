package com.arm.sqlite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * db管理器
 */
@Component
public class DbManager {
    /**
     * 如果后面有多种实现模式的话，可以改成工厂模式
     */
    @Autowired
    private DbTemplateService dbService;

    @Autowired
    private DbTransaction dbTransaction;


    public DbTemplateService getDbService() {
        return dbService;
    }

    public DbTransaction getDbTransaction() {
        return dbTransaction;
    }

}
