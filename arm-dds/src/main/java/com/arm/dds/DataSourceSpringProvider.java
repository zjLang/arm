package com.arm.dds;

import com.arm.dds.core.DataSourceTypeEnum;
import com.arm.dds.core.DynamicDataSource;
import com.arm.dds.core.DynamicDataSourceException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DataSourceSpringProvider implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    private static DataSourceProvider DataSourceProvider;

    @Value("${dds.dataSourceType:druid}")
    private DataSourceTypeEnum type;

    /**
     * 获取数据源管理对象
     *
     * @return
     */
    public static DynamicDataSource getDynamicDataSource() {
        return DataSourceProvider.getDynamicDataSource();
    }

    public static DataSource getDataSource(String url) throws DynamicDataSourceException {
        return getDataSource(url, null, null);
    }

    /**
     * 获取数据源对象
     * 注意:别缓存对象
     *
     * @return
     */
    public static DataSource getDataSource(String url, String userName, String password) throws DynamicDataSourceException {
        return DataSourceProvider.getDataSource(url, userName, password);
    }


    public static Connection getConnection(String url) throws SQLException, DynamicDataSourceException {
        return getDataSource(url).getConnection();
    }

    public static Connection getConnection(String url, String userName, String userPassword) throws SQLException, DynamicDataSourceException {
        return getDataSource(url, userName, userPassword).getConnection();
    }


    public static boolean closeDynamicDataSource() {
        return DataSourceProvider.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (type == null) {
            Properties properties = System.getProperties();
            String property = properties.getProperty("dds.dataSourceType");
            if (property != null) {
                type = DataSourceTypeEnum.valueOf(DataSourceTypeEnum.class, property);
            }
        }
        DataSourceProvider = new DataSourceProvider(type);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
