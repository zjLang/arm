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

/**
 * @author z-ewa
 */
@Service
public class JdbcTestServiceImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void query() {
        String sql = "select * from re_log  limit 1";
        RowCountCallbackHandler rcch = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, rcch);
        String[] coloumnName = rcch.getColumnNames();
        int[] coloumnType = rcch.getColumnTypes();
        System.out.println(Arrays.toString(coloumnName));
        System.out.println(Arrays.toString(coloumnType));
    }


    public void query2() throws SQLException, JsonProcessingException {
        Connection connection = this.jdbcTemplate.getDataSource().getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        String tableName = "re_log";
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
        System.out.println("query success:" + s);
    }
}
