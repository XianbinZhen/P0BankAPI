package dev.zhen.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection createConnection() {
        try {
//            String cred = "jdbc:postgresql://35.188.34.92:5432/bankDB?user=xianbin&password=password";
//            Connection connection = DriverManager.getConnection(cred);
            Connection connection = DriverManager.getConnection(System.getenv("CONN_CRED"));
            return connection;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
}
