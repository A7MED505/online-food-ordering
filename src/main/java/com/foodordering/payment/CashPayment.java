package com.foodordering.payment;

public class CashPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        return amount > 0;
    }
}
