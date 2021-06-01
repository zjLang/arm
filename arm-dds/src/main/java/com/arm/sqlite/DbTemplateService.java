package com.arm.sqlite;


import com.arm.dds.core.DynamicDataSourceException;

import java.util.List;
import java.util.Map;

/**
 * db数据操作服务接口
 */
public interface DbTemplateService<T> {
    /**
     * 数组查询
     *
     * @param dbPath
     * @param sql
     * @param args
     * @return
     * @throws DynamicDataSourceException
     */
    List<Map<String, Object>> queryForList(String dbPath, String sql, Object... args) throws DynamicDataSourceException;

    Map<String, Object> queryForMap(String dbPath, String sql, Object... args) throws DynamicDataSourceException;

    /**
     * 查询table数据
     *
     * @return
     */
    //Pager<T> queryTableList(String dbPath, Pager<T> pager, String sql, Object... args) throws DynamicDataSourceException;

    /**
     * 修改db的数据
     *
     * @param dbPath
     * @param sql
     * @param args
     * @return
     * @throws DynamicDataSourceException
     */
    int update(String dbPath, String sql, Object... args) throws DynamicDataSourceException;

    <T> T queryForObject(String dbPath, Class<T> tClass, String sql, Object... args) throws DynamicDataSourceException;

}
