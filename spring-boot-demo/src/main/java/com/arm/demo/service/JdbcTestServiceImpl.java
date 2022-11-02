package com.arm.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

/**
 * @author z-ewa
 */
@Service
public class JdbcTestServiceImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Connection connection;
    private DatabaseMetaData metaData;

    public void init() throws SQLException {
        if (connection == null) {
            connection = this.jdbcTemplate.getDataSource().getConnection();
            metaData = connection.getMetaData();
        }
    }

    public void query() {
        String sql = "select * from re_log  limit 1";
        RowCountCallbackHandler rcch = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, rcch);
        String[] coloumnName = rcch.getColumnNames();
        int[] coloumnType = rcch.getColumnTypes();
        System.out.println(Arrays.toString(coloumnName));
        System.out.println(Arrays.toString(coloumnType));
    }


    public List<Map<String, String>> getTable(String tableName) {
        try {
            init();
            ResultSet rs = metaData.getTables(connection.getCatalog(), connection.getSchema(), tableName, null);
            return template(rs, map -> {
                try {
                    map.put(rs.getString("TABLE_NAME"), rs.getString("REMARKS"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return map;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Map<String, String>> getPk(String tableName) throws SQLException, JsonProcessingException {
        init();
        ResultSet rs = metaData.getPrimaryKeys(null, connection.getSchema(), tableName);
        return template(rs, map -> {
            try {
                map.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return map;
        });

    }

    public List<Map<String, String>> getCloumn(String tableName) throws SQLException, JsonProcessingException {
        init();
        ResultSet resultSet = metaData.getColumns(null, connection.getSchema(), tableName, null);
        List<Map<String, String>> list = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> map = new HashMap<>();
            String columnName = resultSet.getString("COLUMN_NAME");
            String dataType = resultSet.getString("DATA_TYPE");
            String typeName = resultSet.getString("TYPE_NAME");
            // 0  1   NO  YES
            String nullable = resultSet.getString("NULLABLE");
            String isNullable = resultSet.getString("IS_NULLABLE");
            String remarks = resultSet.getString("REMARKS");
            String columnSize = resultSet.getString("COLUMN_SIZE");
            String bufferLength = resultSet.getString("BUFFER_LENGTH");
            String decimalDigits = resultSet.getString("DECIMAL_DIGITS");

            map.put("columnName", columnName);
            map.put("dataType", dataType);
            map.put("typeName", typeName);
            map.put("nullable", nullable);
            map.put("isNullable", isNullable);
            map.put("remarks", remarks);
            map.put("columnSize", columnSize);
            map.put("bufferLength", bufferLength);
            map.put("decimalDigits", decimalDigits);
            list.add(map);
        }
        String s = new ObjectMapper().writeValueAsString(list);
        return list;
    }


    private List<Map<String, String>> template(ResultSet rs, Function<Map<String, String>, Map<String, String>> function) throws SQLException, JsonProcessingException {
        List<Map<String, String>> list = new ArrayList<>();
        while (rs.next()) {
            Map<String, String> map = function.apply(new HashMap<>(8));
            list.add(map);
        }
        return list;
    }
}
