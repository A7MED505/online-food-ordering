package com.foodordering.model;

import com.foodordering.util.PriceCalculator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Cart {
    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(MenuItem item, int quantity) {
        items.add(new OrderItem(item, quantity));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double subtotal() {
        double sum = 0;
        for (OrderItem item : items) {
            sum += item.lineTotal();
        }
        return sum;
    }

    public Order toOrder(Customer customer, double taxRate, double discount) {
        if (customer == null) {
            throw new IllegalArgumentException("customer must not be null");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("cart is empty");
        }
        double total = PriceCalculator.calculateTotal(items, taxRate, discount);
        return new Order(UUID.randomUUID().toString(), customer, items, total, OrderStatus.NEW);
    }
}
