package com.foodordering.service;

import com.foodordering.model.PaymentMethod;
import com.foodordering.payment.CashPayment;
import com.foodordering.payment.PaymentStrategy;
import com.foodordering.payment.CardPayment;
import com.foodordering.payment.WalletPayment;

public class PaymentService {

    public boolean process(double amount, PaymentMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("payment method is required");
        }
        PaymentStrategy strategy = resolveStrategy(method);
        return strategy.pay(amount);
    }

    private PaymentStrategy resolveStrategy(PaymentMethod method) {
        switch (method) {
            case CASH:
                return new CashPayment();
            case CARD:
                return new CardPayment("0000-0000-0000-0000");
            case WALLET:
                return new WalletPayment(1_000); // demo balance
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }
}
