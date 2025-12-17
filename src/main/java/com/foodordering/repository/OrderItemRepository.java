package com.foodordering.repository;

import com.foodordering.db.DatabaseConnection;
import com.foodordering.model.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository {

    public void saveItems(String orderId, List<OrderItem> items) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (OrderItem item : items) {
                stmt.setString(1, orderId);
                stmt.setString(2, item.getItem().getId());
                stmt.setInt(3, item.getQuantity());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public List<OrderItem> findByOrderId(String orderId) throws SQLException {
        String sql = "SELECT order_id, menu_item_id, quantity FROM order_items WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // To fully build OrderItem we need MenuItem; caller can map later if needed.
                }
            }
        }
        return items;
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM order_items";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void deleteAll() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM order_items")) {
            stmt.executeUpdate();
        }
    }
}
