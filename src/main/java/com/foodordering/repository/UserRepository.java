package com.foodordering.repository;

import com.foodordering.model.User;
import com.foodordering.db.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class UserRepository {

    public void save(User user) throws SQLException {
        String query = "INSERT INTO users (id, email, hashed_password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getHashedPassword());
            stmt.setString(4, "USER");
            stmt.executeUpdate();
        }
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        String query = "SELECT id, email, hashed_password, role FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("id"), rs.getString("email"), rs.getString("hashed_password"));
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> findById(String id) throws SQLException {
        String query = "SELECT id, email, hashed_password, role FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("id"), rs.getString("email"), rs.getString("hashed_password"));
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    public List<User> findAll() throws SQLException {
        String query = "SELECT id, email, hashed_password, role FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("email"), rs.getString("hashed_password"));
                users.add(user);
            }
        }
        return users;
    }

    public boolean existsByEmail(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE users SET email = ?, hashed_password = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getHashedPassword());
            stmt.setString(3, user.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}
