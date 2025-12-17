package com.foodordering.payment;

public class CardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CardPayment(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("card number must not be empty");
        }
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        return amount > 0 && cardNumber.length() >= 4;
    }
}
