package com.foodordering.service;

import com.foodordering.model.Coupon;
import com.foodordering.repository.CouponRepository;
import java.util.List;
import java.util.Optional;

public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        if (couponRepository == null) {
            throw new IllegalArgumentException("couponRepository must not be null");
        }
        this.couponRepository = couponRepository;
    }

    public Optional<Coupon> findByCode(String code) throws Exception {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code must not be empty");
        }
        System.out.println("Searching for coupon: " + code.toUpperCase());
        Optional<Coupon> result = couponRepository.findByCode(code.toUpperCase());
        System.out.println("Coupon found: " + result.isPresent());
        return result;
    }

    public List<Coupon> listValidCoupons() throws Exception {
        return couponRepository.findAllValid();
    }

    public boolean validateAndApply(String code, double subtotal) throws Exception {
        Optional<Coupon> coupon = findByCode(code);
        if (coupon.isEmpty()) {
            return false;
        }

        Coupon c = coupon.get();
        return c.isValid() && subtotal > 0;
    }

    public double applyDiscount(String code, double subtotal) throws Exception {
        Optional<Coupon> coupon = findByCode(code);
        if (coupon.isEmpty()) {
            return 0;
        }

        return coupon.get().calculateDiscount(subtotal);
    }

    public void addCoupon(Coupon coupon) throws Exception {
        if (coupon == null) {
            throw new IllegalArgumentException("coupon must not be null");
        }
        couponRepository.save(coupon);
    }

    public void deactivateCoupon(String id) throws Exception {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isPresent()) {
            Coupon c = coupon.get();
            c.setActive(false);
            couponRepository.update(c);
        }
    }

    public String validateCoupon(String code) throws Exception {
        Optional<Coupon> coupon = findByCode(code);
        if (coupon.isEmpty()) {
            return "Coupon not found";
        }

        Coupon c = coupon.get();
        if (!c.isActive()) {
            return "Coupon is inactive";
        }
        if (c.isExpired()) {
            return "Coupon has expired";
        }

        return "valid";
    }
}
