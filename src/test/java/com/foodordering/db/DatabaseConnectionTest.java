package com.foodordering.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() {
        assertDoesNotThrow(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                assertNotNull(conn, "Connection should not be null");
                assertFalse(conn.isClosed(), "Connection should be open");
                System.out.println("✓ Database connection successful!");
            }
        });
    }

    @Test
    public void testSelectFromUsers() {
        assertDoesNotThrow(() -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
                assertTrue(rs.next(), "Query should return a result");
                int count = rs.getInt(1);
                System.out.println("✓ Users table accessible. Current count: " + count);
            }
        });
    }

    @Test
    public void testSelectFromMenuItems() {
        assertDoesNotThrow(() -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM menu_items");
                assertTrue(rs.next(), "Query should return a result");
                int count = rs.getInt(1);
                System.out.println("✓ Menu items table accessible. Current count: " + count);
            }
        });
    }

    @Test
    public void testSelectFromOrders() {
        assertDoesNotThrow(() -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM orders");
                assertTrue(rs.next(), "Query should return a result");
                int count = rs.getInt(1);
                System.out.println("✓ Orders table accessible. Current count: " + count);
            }
        });
    }

    @Test
    public void testSelectFromOrderItems() {
        assertDoesNotThrow(() -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM order_items");
                assertTrue(rs.next(), "Query should return a result");
                int count = rs.getInt(1);
                System.out.println("✓ Order items table accessible. Current count: " + count);
            }
        });
    }
}
