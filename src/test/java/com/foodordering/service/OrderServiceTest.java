package com.foodordering.service;

import com.foodordering.model.MenuItem;
import com.foodordering.model.Order;
import com.foodordering.model.OrderItem;
import com.foodordering.model.User;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void createOrderCalculatesTotal() {
        OrderService service = new OrderService(0.1); // 10% tax
        User user = new User("u1", "u@example.com", "hashed");
        OrderItem item1 = new OrderItem(new MenuItem("1", "Pizza", 20.0), 2); // 40
        OrderItem item2 = new OrderItem(new MenuItem("2", "Soda", 5.0), 1);   // 5

        Order order = service.createOrder(user, List.of(item1, item2));

        // subtotal 45, tax 10% -> 49.5
        assertEquals(49.5, order.getTotal());
        assertEquals(user, order.getUser());
    }

    @Test
    void recalcWithDiscount() {
        OrderService service = new OrderService(0.0);
        User user = new User("u1", "u@example.com", "hashed");
        OrderItem item = new OrderItem(new MenuItem("1", "Fries", 10.0), 3); // 30
        Order order = service.createOrder(user, List.of(item));

        service.recalculateTotal(order, 5.0);
        assertEquals(25.0, order.getTotal());
    }
}
