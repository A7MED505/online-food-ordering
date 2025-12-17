package com.foodordering.service;

import com.foodordering.model.Order;
import com.foodordering.model.OrderItem;
import com.foodordering.model.OrderStatus;
import com.foodordering.model.PaymentMethod;
import com.foodordering.model.User;
import com.foodordering.repository.OrderItemRepository;
import com.foodordering.repository.OrderRepository;
import com.foodordering.util.PriceCalculator;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final double taxRate;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(double taxRate, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        if (taxRate < 0) {
            throw new IllegalArgumentException("taxRate must be non-negative");
        }
        this.taxRate = taxRate;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Order createOrder(User user, List<OrderItem> items, PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            paymentMethod = PaymentMethod.CASH;
        }
        double total = PriceCalculator.calculateTotal(items, taxRate, 0);
        Order order = new Order(UUID.randomUUID().toString(), user, items, total, OrderStatus.NEW);
        try {
            orderRepository.save(order, paymentMethod);
            orderItemRepository.saveItems(order.getId(), items);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create order", e);
        }
    }

    public void recalculateTotal(Order order, double discount) {
        double total = PriceCalculator.calculateTotal(order.getItems(), taxRate, discount);
        order.setTotal(total);
        try {
            orderRepository.update(order, PaymentMethod.CASH);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to recalculate order total", e);
        }
    }

    public void markPaid(Order order, PaymentMethod method) {
        if (method == null) {
            method = PaymentMethod.CASH;
        }
        order.setStatus(OrderStatus.PAID);
        try {
            orderRepository.update(order, method);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to mark order paid", e);
        }
    }
}
