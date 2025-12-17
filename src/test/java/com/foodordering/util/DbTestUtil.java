package com.foodordering.util;

import com.foodordering.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;

public final class DbTestUtil {
    private DbTestUtil() {}

    public static void clearAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM order_items");
            stmt.executeUpdate("DELETE FROM orders");
            stmt.executeUpdate("DELETE FROM menu_items");
            stmt.executeUpdate("DELETE FROM users");
        }
    }
}
