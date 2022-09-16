package com.arm.sqlite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author z-ewa
 */
@Controller()
@RequestMapping("test")
public class TestController {

    @RequestMapping("test")
    @ResponseBody
    public Object test(String path) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            path = "jdbc:sqlite:" + path;
            conn = DriverManager.getConnection(path);
            conn.setAutoCommit(true);
            Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery(" select *  from hadwn_assis_tab;  ");
            conn.close();
            System.out.println("in:" + resultSet.toString());
            return convertList(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "none-null";
    }


    public static List<Map<String, Object>> convertList(ResultSet rs) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
