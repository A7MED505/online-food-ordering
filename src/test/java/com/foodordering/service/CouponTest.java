package com.foodordering.service;

import com.foodordering.model.Coupon;
import com.foodordering.model.CouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CouponTest {
    private Coupon percentageCoupon;
    private Coupon fixedCoupon;

    @BeforeEach
    void setUp() {
        percentageCoupon = new Coupon("1", "SAVE20", CouponType.PERCENTAGE, 20, LocalDate.now().plusMonths(1));
        fixedCoupon = new Coupon("2", "FLAT10", CouponType.FIXED_AMOUNT, 10, LocalDate.now().plusMonths(1));
    }

    @Test
    void testCouponCreation() {
        assertEquals("SAVE20", percentageCoupon.getCode());
        assertEquals(CouponType.PERCENTAGE, percentageCoupon.getType());
        assertEquals(20, percentageCoupon.getValue());
        assertTrue(percentageCoupon.isActive());
    }

    @Test
    void testCouponValidation() {
        assertTrue(percentageCoupon.isValid());
        assertFalse(percentageCoupon.isExpired());
    }

    @Test
    void testCouponExpiry() {
        Coupon expiredCoupon = new Coupon("3", "OLD", CouponType.PERCENTAGE, 10, LocalDate.now().minusDays(1));
        assertTrue(expiredCoupon.isExpired());
        assertFalse(expiredCoupon.isValid());
    }

    @Test
    void testPercentageDiscount() {
        double discount = percentageCoupon.calculateDiscount(100);
        assertEquals(20, discount);
    }

    @Test
    void testFixedAmountDiscount() {
        double discount = fixedCoupon.calculateDiscount(50);
        assertEquals(10, discount);
    }

    @Test
    void testDiscountCapping() {
        double discount = fixedCoupon.calculateDiscount(5);
        assertEquals(5, discount);
    }

    @Test
    void testInactiveCoupon() {
        percentageCoupon.setActive(false);
        assertFalse(percentageCoupon.isValid());
        double discount = percentageCoupon.calculateDiscount(100);
        assertEquals(0, discount);
    }

    @Test
    void testMaxDiscount() {
        Coupon coupon = new Coupon("4", "LIMIT", CouponType.PERCENTAGE, 50, LocalDate.now().plusMonths(1));
        coupon.setMaxDiscount(20);
        double discount = coupon.calculateDiscount(100);
        assertEquals(20, discount);
    }

    @Test
    void testNullValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Coupon(null, "CODE", CouponType.PERCENTAGE, 10, LocalDate.now());
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("1", null, CouponType.PERCENTAGE, 10, LocalDate.now());
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("1", "CODE", null, 10, LocalDate.now());
        });
    }

    @Test
    void testValueValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("1", "CODE", CouponType.PERCENTAGE, -5, LocalDate.now());
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("1", "CODE", CouponType.PERCENTAGE, 0, LocalDate.now());
        });
    }

    @Test
    void testCouponEquality() {
        Coupon coupon1 = new Coupon("1", "SAME", CouponType.PERCENTAGE, 10, LocalDate.now().plusMonths(1));
        Coupon coupon2 = new Coupon("1", "SAME", CouponType.PERCENTAGE, 10, LocalDate.now().plusMonths(1));
        assertEquals(coupon1, coupon2);
    }

    @Test
    void testCodeNormalization() {
        Coupon coupon = new Coupon("1", "lowercase", CouponType.PERCENTAGE, 10, LocalDate.now().plusMonths(1));
        assertEquals("LOWERCASE", coupon.getCode());
    }
}
