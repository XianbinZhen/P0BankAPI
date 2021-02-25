package dev.zhen.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection createConnection() {
        try {
            Connection connection = DriverManager.getConnection(System.getenv("CONN_CRED"));
            return connection;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
}
