package com.foodordering.db;

import java.sql.*;

public class CouponTableInitializer {

    public static void createCouponTable() {
        String sql = "CREATE TABLE IF NOT EXISTS coupons (" +
                "id VARCHAR(36) PRIMARY KEY," +
                "code VARCHAR(50) UNIQUE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "value DOUBLE NOT NULL," +
                "expiry_date DATE NOT NULL," +
                "active BOOLEAN DEFAULT true," +
                "max_discount DOUBLE DEFAULT 999999," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Coupons table created or already exists");
        } catch (SQLException e) {
            System.err.println("Error creating coupons table: " + e.getMessage());
        }
    }
}
