package com.example.deliver;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBUtil {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/chatting";
        String username = "root";
        String password = "";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
