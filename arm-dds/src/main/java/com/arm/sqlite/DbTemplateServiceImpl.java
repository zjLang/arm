package com.arm.sqlite;


import com.arm.dds.core.DynamicDataSourceException;
import com.arm.dds.util.SQLiteUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DbTemplateServiceImpl<T> implements DbTemplateService<T> {

    @Override
    public List<Map<String, Object>> queryForList(String dbPath, String sql, Object... args) throws DynamicDataSourceException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dbPath);
        return jdbcTemplate.queryForList(sql, args);
    }

    @Override
    public Map<String, Object> queryForMap(String dbPath, String sql, Object... args) throws DynamicDataSourceException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dbPath);
        return jdbcTemplate.queryForMap(sql, args);
    }

    /*@Override
    public Pager<T> queryTableList(String dbPath, Pager<T> pager, String sql, Object... args) throws DynamicDataSourceException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dbPath);

        BaseJdbcDao baseJdbcDao = new BaseJdbcDao();
        baseJdbcDao.setDbType("mysql");
        baseJdbcDao.setJdbcTemplate(jdbcTemplate);

        return baseJdbcDao.findByMapPager(pager, sql, args);
    }*/

    @Override
    public int update(String dbPath, String sql, Object... args) throws DynamicDataSourceException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dbPath);
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public <T> T queryForObject(String dbPath, Class<T> tClass, String sql, Object... args) throws DynamicDataSourceException {
        return this.getJdbcTemplate(dbPath).queryForObject(sql, tClass, args);
    }


    private JdbcTemplate getJdbcTemplate(String dbPath) throws DynamicDataSourceException {
        return SQLiteUtil.getJdbcTemplate(SQLiteUtil.SQLITE_PREFIX + dbPath);
    }
}
