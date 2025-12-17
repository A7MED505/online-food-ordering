package com.foodordering.service;

import com.foodordering.model.Order;
import com.foodordering.model.OrderItem;
import com.foodordering.model.OrderStatus;
import com.foodordering.model.User;
import com.foodordering.util.PriceCalculator;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final double taxRate;

    public OrderService(double taxRate) {
        if (taxRate < 0) {
            throw new IllegalArgumentException("taxRate must be non-negative");
        }
        this.taxRate = taxRate;
    }

    public Order createOrder(User user, List<OrderItem> items) {
        double total = PriceCalculator.calculateTotal(items, taxRate, 0);
        return new Order(UUID.randomUUID().toString(), user, items, total, OrderStatus.NEW);
    }

    public void recalculateTotal(Order order, double discount) {
        double total = PriceCalculator.calculateTotal(order.getItems(), taxRate, discount);
        order.setTotal(total);
    }

    public void markPaid(Order order) {
        order.setStatus(OrderStatus.PAID);
    }
}
