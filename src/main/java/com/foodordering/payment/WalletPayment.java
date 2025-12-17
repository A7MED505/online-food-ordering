package com.foodordering.payment;

public class WalletPayment implements PaymentStrategy {
    private double balance;

    public WalletPayment(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("balance must be non-negative");
        }
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}
