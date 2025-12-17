package com.foodordering.model;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    void addItemsAndSubtotal() {
        Cart cart = new Cart();
        cart.addItem(new MenuItem("m1", "Burger", 10.0), 2);
        cart.addItem(new MenuItem("m2", "Fries", 5.0), 1);

        assertEquals(25.0, cart.subtotal(), 0.0001);
        assertEquals(2, cart.getItems().size());
    }

    @Test
    void toOrderCalculatesTotalWithTaxAndDiscount() {
        Cart cart = new Cart();
        cart.addItem(new MenuItem("m1", "Burger", 10.0), 2); // 20
        cart.addItem(new MenuItem("m2", "Fries", 5.0), 1);   // 5
        Customer customer = new Customer(UUID.randomUUID().toString(), "c@test.com", "hash", "Addr", "123");

        Order order = cart.toOrder(customer, 0.10, 3.0); // subtotal 25 => +10% = 27.5 => -3 = 24.5
        assertEquals(OrderStatus.NEW, order.getStatus());
        assertEquals(24.5, order.getTotal(), 0.0001);
        assertEquals(2, order.getItems().size());
    }

    @Test
    void emptyCartThrows() {
        Cart cart = new Cart();
        Customer customer = new Customer("c1", "c@test.com", "hash", "Addr", "123");
        assertThrows(IllegalArgumentException.class, () -> cart.toOrder(customer, 0.1, 0));
    }
}
