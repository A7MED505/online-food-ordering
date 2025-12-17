package com.foodordering.service;

import com.foodordering.model.PaymentMethod;

public class PaymentService {
    public boolean process(double amount, PaymentMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("payment method is required");
        }
        return amount > 0;
    }
}
