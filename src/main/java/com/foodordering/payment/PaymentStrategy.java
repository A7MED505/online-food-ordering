package com.foodordering.payment;

public interface PaymentStrategy {
    boolean pay(double amount);
}
