package com.foodordering.util;

import com.foodordering.model.Coupon;
import com.foodordering.model.CouponType;
import com.foodordering.repository.CouponRepository;
import java.time.LocalDate;
import java.util.UUID;

public class CouponDataInitializer {

    public static void initializeCoupons(CouponRepository couponRepository) {
        try {
            Object[][] seed = new Object[][]{
                {"SAVE10", CouponType.PERCENTAGE, 10.0, LocalDate.now().plusMonths(3)},
                {"SAVE20", CouponType.PERCENTAGE, 20.0, LocalDate.now().plusMonths(6)},
                {"FLAT5", CouponType.FIXED_AMOUNT, 5.0, LocalDate.now().plusMonths(2)},
                {"FLAT15", CouponType.FIXED_AMOUNT, 15.0, LocalDate.now().plusMonths(4)},
                {"WELCOME", CouponType.PERCENTAGE, 15.0, LocalDate.now().plusMonths(1)},
                {"BIGORDER", CouponType.FIXED_AMOUNT, 20.0, LocalDate.now().plusMonths(5)}
            };

            for (Object[] row : seed) {
                String code = (String) row[0];
                CouponType type = (CouponType) row[1];
                double value = (Double) row[2];
                LocalDate expiryDate = (LocalDate) row[3];

                boolean exists = couponRepository.findByCode(code).isPresent();
                if (!exists) {
                    Coupon coupon = new Coupon(UUID.randomUUID().toString(), code, type, value, expiryDate);
                    if (type == CouponType.PERCENTAGE) {
                        coupon.setMaxDiscount(Double.MAX_VALUE);
                    } else {
                        coupon.setMaxDiscount(value);
                    }
                    couponRepository.save(coupon);
                    System.out.println("Created coupon: " + code);
                } else {
                    System.out.println("Coupon already exists: " + code);
                }
            }

            int count = couponRepository.count();
            System.out.println("Coupons initialized successfully! Total: " + count);
        } catch (Exception e) {
            System.err.println("Error initializing coupons: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
