package com.arm.scene;


import com.arm.dds.core.DynamicDataSourceException;
import com.arm.sqlite.DbManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionTestServiceImpl implements TransactionTestService {

   /* @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private DruidDataSource druidDataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;*/

    @Autowired
    private DbManager dbManager;

   /* @Autowired
    private TransactionTemplate transactionTemplate;*/

    static final String sqLite_sql = "insert into hadwn_rest_gathr_tab_001 (CORP_NM) VALUES ('test1')";
    static final String mysql_sql = "insert into tx_user(username , age) VALUES ('zhangsan', 10);";
    static final String path = "E:\\\\2.资料\\\\2.公司资料\\\\1.在建项目\\\\15.国资委数据上报\\\\test文件\\\\toFile\\\\115100007650616494_0037_1002_20210226154242\\\\115100007650616494_0037_1002_20210226154242.db";

    @Override
    public void insertAll() throws DynamicDataSourceException {
        /*transactionTemplate.execute(a -> {
            try {
                insertMysql();
                insertSqLite();
            } catch (DynamicDataSourceException e) {
                e.printStackTrace();
            }
            return null;
        });*/

    }

    @Override
    public void insertMysql() {
        // 这样不行，需要用aop实现注入
        //dataSourceTransactionManager.setDataSource(druidDataSource);
        //jdbcTemplate.update(mysql_sql);
    }

    @Override
    public void insertSqLite() throws DynamicDataSourceException {
        dbManager.getDbService().update(path, sqLite_sql);
        throw new NullPointerException("test");
    }
}
