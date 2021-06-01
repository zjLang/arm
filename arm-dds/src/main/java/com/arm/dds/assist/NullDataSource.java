package com.arm.dds.assist;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class NullDataSource implements DataSource {
    @Override
    public Connection getConnection() throws SQLException {
        throwException();
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throwException();
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throwException();
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throwException();
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throwException();
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throwException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throwException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throwException();
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException(" get error datasource , this datasource only to assist create PlatformTransactionManager");
    }

    private void throwException() throws SQLException {
        throw new SQLException(" get error datasource , this datasource only to assist create PlatformTransactionManager");
    }
}
