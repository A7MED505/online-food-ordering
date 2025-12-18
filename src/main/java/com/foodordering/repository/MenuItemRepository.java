package com.foodordering.repository;

import com.foodordering.model.MenuItem;
import com.foodordering.db.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class MenuItemRepository {

    public void save(MenuItem item) throws SQLException {
        String query = "INSERT INTO menu_items (id, name, price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getId());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getPrice());
            stmt.executeUpdate();
        }
    }

    public Optional<MenuItem> findById(String id) throws SQLException {
        String query = "SELECT id, name, price FROM menu_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new MenuItem(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<MenuItem> findByName(String name) throws SQLException {
        String query = "SELECT id, name, price FROM menu_items WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new MenuItem(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    public List<MenuItem> findAll() throws SQLException {
        String query = "SELECT id, name, price FROM menu_items ORDER BY name";
        List<MenuItem> items = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                items.add(new MenuItem(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                ));
            }
        }
        return items;
    }

    public void update(MenuItem item) throws SQLException {
        String query = "UPDATE menu_items SET name = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String query = "DELETE FROM menu_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM menu_items";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void deleteAll() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            try {
                stmt.executeUpdate("DELETE FROM order_items");
                stmt.executeUpdate("DELETE FROM menu_items");
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
