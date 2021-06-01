package com.arm.sqlite;


import com.arm.dds.DataSourceSpringProvider;
import com.arm.dds.assist.NullDataSource;
import com.arm.dds.core.DynamicDataSourceException;
import com.arm.dds.util.SQLiteUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Component
public class DbTransactionTemplate implements DbTransaction {

    private TransactionTemplate transactionTemplate;

    //@Autowired
    //private DbManager dbManager;

    DbTransactionTemplate() {
        init();
    }

    @Override
    public <T> T execute(String url, DbTransactionAction action) throws TransactionException, DynamicDataSourceException {
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) transactionTemplate.getTransactionManager();
        JdbcTemplate jdbcTemplate;
        try {
            if (!url.startsWith(SQLiteUtil.SQLITE_PREFIX)) {
                url = SQLiteUtil.SQLITE_PREFIX + url;
            }
            DataSource dataSource = DataSourceSpringProvider.getDataSource(url);
            transactionManager.setDataSource(dataSource);
            jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (DynamicDataSourceException e) {
            e.printStackTrace();
            throw new DynamicDataSourceException("error", e);
        }
        TransactionCallback<T> tTransactionCallback = (TransactionCallback) status -> action.execute(jdbcTemplate);
        return transactionTemplate.execute(tTransactionCallback);
    }


    private void init() {
        // 初始化transactionTemplate ;
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(new NullDataSource());
        transactionTemplate = new TransactionTemplate(transactionManager);
    }
}
