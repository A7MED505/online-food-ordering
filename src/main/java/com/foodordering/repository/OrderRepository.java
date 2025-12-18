package com.foodordering.repository;

import com.foodordering.db.DatabaseConnection;
import com.foodordering.model.Order;
import com.foodordering.model.PaymentMethod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepository {

    public void save(Order order, PaymentMethod paymentMethod) throws SQLException {
        String sql = "INSERT INTO orders (id, user_id, total, status, payment_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getId());
            stmt.setString(2, order.getUser().getId());
            stmt.setDouble(3, order.getTotal());
            stmt.setString(4, order.getStatus().name());
            stmt.setString(5, paymentMethod.name());
            stmt.executeUpdate();
        }
    }

    public void update(Order order, PaymentMethod paymentMethod) throws SQLException {
        String sql = "UPDATE orders SET total = ?, status = ?, payment_type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, order.getTotal());
            stmt.setString(2, order.getStatus().name());
            stmt.setString(3, paymentMethod.name());
            stmt.setString(4, order.getId());
            stmt.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM orders";
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
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders")) {
            stmt.executeUpdate();
        }
    }
}
