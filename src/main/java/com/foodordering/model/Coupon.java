package com.foodordering.model;

import java.time.LocalDate;
import java.util.Objects;

public class Coupon {
    private final String id;
    private final String code;
    private final CouponType type;
    private final double value;
    private final LocalDate expiryDate;
    private boolean active;
    private double maxDiscount;

    public Coupon(String id, String code, CouponType type, double value, LocalDate expiryDate) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be empty");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code must not be empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("value must be positive");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("expiryDate must not be null");
        }

        this.id = id;
        this.code = code.toUpperCase();
        this.type = type;
        this.value = value;
        this.expiryDate = expiryDate;
        this.active = true;
        this.maxDiscount = type == CouponType.PERCENTAGE ? 100 : Double.MAX_VALUE;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public CouponType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        if (maxDiscount < 0) {
            throw new IllegalArgumentException("maxDiscount must be non-negative");
        }
        this.maxDiscount = maxDiscount;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    public boolean isValid() {
        return active && !isExpired();
    }

    public double calculateDiscount(double subtotal) {
        if (!isValid()) {
            return 0;
        }

        double discount = 0;
        if (type == CouponType.PERCENTAGE) {
            discount = (subtotal * value) / 100;
        } else if (type == CouponType.FIXED_AMOUNT) {
            discount = value;
        }

        return Math.min(discount, Math.min(maxDiscount, subtotal));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id.equals(coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "code='" + code + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", active=" + active +
                ", expired=" + isExpired() +
                '}';
    }
}
