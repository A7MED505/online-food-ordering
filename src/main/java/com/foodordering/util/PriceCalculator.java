package com.foodordering.util;

import com.foodordering.model.OrderItem;
import java.util.List;

public final class PriceCalculator {
    private PriceCalculator() {
    }

    public static double calculateTotal(List<OrderItem> items, double taxRate, double discount) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }
        if (taxRate < 0) {
            throw new IllegalArgumentException("taxRate must be non-negative");
        }
        double subtotal = items.stream().mapToDouble(OrderItem::lineTotal).sum();
        double taxed = subtotal * (1 + taxRate);
        double finalTotal = taxed - discount;
        return Math.max(0, round2(finalTotal));
    }

    private static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
