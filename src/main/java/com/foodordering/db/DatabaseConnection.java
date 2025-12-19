package com.foodordering.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            // Ensure driver is available (JDBC 4 auto-loads, but this is safe)
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();
            try (InputStream in = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) {
                    throw new RuntimeException("db.properties not found in classpath (src/main/resources)");
                }
                props.load(in);
            }

            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");

            if (url == null || username == null || password == null) {
                throw new RuntimeException("Missing db.url/db.username/db.password in db.properties");
            }

            CouponTableInitializer.createCouponTable();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
