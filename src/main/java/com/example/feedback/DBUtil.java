package com.example.feedback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL =
            "jdbc:postgresql://ider-database.westeurope.cloudapp.azure.com:5433/h184921?currentSchema=feedback";
    private static final String USER = "h184921";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found.", e);
        }
    }

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}