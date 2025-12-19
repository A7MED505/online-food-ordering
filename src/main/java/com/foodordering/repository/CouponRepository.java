package com.foodordering.repository;

import com.foodordering.model.Coupon;
import com.foodordering.model.CouponType;
import com.foodordering.db.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class CouponRepository {

    public void save(Coupon coupon) throws SQLException {
        String query = "INSERT INTO coupons (id, code, type, value, expiry_date, active, max_discount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, coupon.getId());
            stmt.setString(2, coupon.getCode());
            stmt.setString(3, coupon.getType().name());
            stmt.setDouble(4, coupon.getValue());
            stmt.setDate(5, java.sql.Date.valueOf(coupon.getExpiryDate()));
            stmt.setBoolean(6, coupon.isActive());
            stmt.setDouble(7, coupon.getMaxDiscount());
            stmt.executeUpdate();
        }
    }

    public Optional<Coupon> findById(String id) throws SQLException {
        String query = "SELECT id, code, type, value, expiry_date, active, max_discount FROM coupons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCoupon(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Coupon> findByCode(String code) throws SQLException {
        String query = "SELECT id, code, type, value, expiry_date, active, max_discount FROM coupons WHERE UPPER(code) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCoupon(rs));
                }
            }
        }
        return Optional.empty();
    }

    public List<Coupon> findAll() throws SQLException {
        String query = "SELECT id, code, type, value, expiry_date, active, max_discount FROM coupons WHERE active = true ORDER BY code";
        List<Coupon> coupons = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                coupons.add(mapRowToCoupon(rs));
            }
        }
        return coupons;
    }

    public List<Coupon> findAllValid() throws SQLException {
        String query = "SELECT id, code, type, value, expiry_date, active, max_discount FROM coupons WHERE active = true AND expiry_date >= CURDATE() ORDER BY code";
        List<Coupon> coupons = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                coupons.add(mapRowToCoupon(rs));
            }
        }
        return coupons;
    }

    public void update(Coupon coupon) throws SQLException {
        String query = "UPDATE coupons SET code = ?, type = ?, value = ?, expiry_date = ?, active = ?, max_discount = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, coupon.getCode());
            stmt.setString(2, coupon.getType().name());
            stmt.setDouble(3, coupon.getValue());
            stmt.setDate(4, java.sql.Date.valueOf(coupon.getExpiryDate()));
            stmt.setBoolean(5, coupon.isActive());
            stmt.setDouble(6, coupon.getMaxDiscount());
            stmt.setString(7, coupon.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String query = "DELETE FROM coupons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String query = "SELECT COUNT(*) FROM coupons WHERE active = true";
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
                stmt.executeUpdate("DELETE FROM coupons");
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private Coupon mapRowToCoupon(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String code = rs.getString("code");
        CouponType type = CouponType.valueOf(rs.getString("type"));
        double value = rs.getDouble("value");
        LocalDate expiryDate = rs.getDate("expiry_date").toLocalDate();

        Coupon coupon = new Coupon(id, code, type, value, expiryDate);
        coupon.setActive(rs.getBoolean("active"));
        coupon.setMaxDiscount(rs.getDouble("max_discount"));
        return coupon;
    }
}
