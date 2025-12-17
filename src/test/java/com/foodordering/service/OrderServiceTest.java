package com.foodordering.service;

import com.foodordering.model.*;
import com.foodordering.repository.MenuItemRepository;
import com.foodordering.repository.OrderItemRepository;
import com.foodordering.repository.OrderRepository;
import com.foodordering.repository.UserRepository;
import com.foodordering.util.DbTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private UserRepository userRepository;
    private MenuItemRepository menuItemRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderService service;

    @BeforeEach
    void setup() throws Exception {
        DbTestUtil.clearAll();
        userRepository = new UserRepository();
        menuItemRepository = new MenuItemRepository();
        orderRepository = new OrderRepository();
        orderItemRepository = new OrderItemRepository();
        service = new OrderService(0.1, orderRepository, orderItemRepository);
    }

    @Test
    void createOrderCalculatesTotalAndPersists() throws Exception {
        User user = new User(UUID.randomUUID().toString(), "u@example.com", "hashed");
        userRepository.save(user);

        MenuItem pizza = new MenuItem("1", "Pizza", 20.0);
        MenuItem soda = new MenuItem("2", "Soda", 5.0);
        menuItemRepository.save(pizza);
        menuItemRepository.save(soda);

        OrderItem item1 = new OrderItem(pizza, 2); // 40
        OrderItem item2 = new OrderItem(soda, 1);  // 5

        Order order = service.createOrder(user, List.of(item1, item2), PaymentMethod.CASH);

        assertEquals(49.5, order.getTotal()); // subtotal 45 +10%
        assertEquals(user, order.getUser());
        assertEquals(1, orderRepository.count());
        assertEquals(2, orderItemRepository.count());
    }

    @Test
    void recalcWithDiscountUpdatesDb() throws Exception {
        User user = new User(UUID.randomUUID().toString(), "u@example.com", "hashed");
        userRepository.save(user);

        MenuItem fries = new MenuItem("1", "Fries", 10.0);
        menuItemRepository.save(fries);

        OrderItem item = new OrderItem(fries, 3); // 30
        Order order = service.createOrder(user, List.of(item), PaymentMethod.CASH);

        service.recalculateTotal(order, 5.0);
        // subtotal 30 +10% tax = 33, -5 discount = 28
        assertEquals(28.0, order.getTotal());
    }
}
