package com.foodordering.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OOP Principles: Interface Tests")
public class InterfaceTest {

    @Test
    @DisplayName("MenuItem implements Orderable interface")
    void testMenuItemImplementsOrderable() {
        MenuItem item = new MenuItem("1", "Burger", 10.0);
        
        assertTrue(item instanceof Orderable, "MenuItem should implement Orderable");
    }

    @Test
    @DisplayName("Orderable interface methods work correctly")
    void testOrderableInterfaceMethods() {
        Orderable item = new MenuItem("1", "Pizza", 15.0);
        
        assertEquals("1", item.getId());
        assertEquals("Pizza", item.getName());
        assertEquals(15.0, item.getPrice());
    }

    @Test
    @DisplayName("Orderable description method works")
    void testOrderableDescription() {
        MenuItem item = new MenuItem("1", "Salad", 8.0);
        item.setDescription("Fresh green salad");
        
        Orderable orderable = item;
        assertEquals("Fresh green salad", orderable.getDescription());
    }

    @Test
    @DisplayName("Orderable availability check works")
    void testOrderableAvailability() {
        MenuItem item = new MenuItem("1", "Pasta", 12.0);
        
        Orderable orderable = item;
        assertTrue(orderable.isAvailable(), "Item should be available by default");
        
        item.setAvailable(false);
        assertFalse(orderable.isAvailable(), "Item availability should be false");
    }

    @Test
    @DisplayName("Collection of Orderable items")
    void testOrderableCollection() {
        List<Orderable> orderables = new ArrayList<>();
        orderables.add(new MenuItem("1", "Burger", 10.0));
        orderables.add(new MenuItem("2", "Pizza", 15.0));
        orderables.add(new MenuItem("3", "Salad", 8.0));
        
        assertEquals(3, orderables.size());
        
        double total = 0.0;
        for (Orderable item : orderables) {
            total += item.getPrice();
        }
        
        assertEquals(33.0, total, 0.01, "Total price should be 33.0");
    }

    @Test
    @DisplayName("Interface allows polymorphic behavior")
    void testInterfacePolymorphism() {
        Orderable item1 = new MenuItem("1", "Burger", 10.0);
        Orderable item2 = new MenuItem("2", "Pizza", 15.0);
        
        assertNotEquals(item1.getId(), item2.getId());
        assertTrue(item1.getPrice() < item2.getPrice());
    }

    @Test
    @DisplayName("Interface contract enforced - all methods callable")
    void testInterfaceContract() {
        Orderable item = new MenuItem("test-id", "Test Item", 20.0);
        
        assertDoesNotThrow(() -> {
            item.getId();
            item.getName();
            item.getPrice();
            item.getDescription();
            item.isAvailable();
        }, "All interface methods should be callable");
    }

    @Test
    @DisplayName("Interface enables loose coupling")
    void testInterfaceLooseCoupling() {
        List<Orderable> cart = new ArrayList<>();
        
        cart.add(createOrderable("1", "Item 1", 5.0));
        cart.add(createOrderable("2", "Item 2", 10.0));
        
        double total = calculateTotal(cart);
        assertEquals(15.0, total, 0.01);
    }

    private Orderable createOrderable(String id, String name, double price) {
        return new MenuItem(id, name, price);
    }

    private double calculateTotal(List<Orderable> items) {
        double total = 0.0;
        for (Orderable item : items) {
            if (item.isAvailable()) {
                total += item.getPrice();
            }
        }
        return total;
    }

    @Test
    @DisplayName("Interface allows filtering unavailable items")
    void testFilteringByAvailability() {
        List<Orderable> items = new ArrayList<>();
        
        MenuItem item1 = new MenuItem("1", "Available Item", 10.0);
        MenuItem item2 = new MenuItem("2", "Unavailable Item", 20.0);
        item2.setAvailable(false);
        MenuItem item3 = new MenuItem("3", "Another Available", 15.0);
        
        items.add(item1);
        items.add(item2);
        items.add(item3);
        
        List<Orderable> availableItems = new ArrayList<>();
        for (Orderable item : items) {
            if (item.isAvailable()) {
                availableItems.add(item);
            }
        }
        
        assertEquals(2, availableItems.size());
        assertEquals(25.0, calculateTotal(availableItems), 0.01);
    }

    @Test
    @DisplayName("Interface enables dependency injection pattern")
    void testDependencyInjection() {
        OrderProcessor processor = new OrderProcessor();
        
        Orderable item = new MenuItem("1", "Test Item", 50.0);
        double result = processor.processItem(item);
        
        assertEquals(50.0, result, 0.01);
    }

    private static class OrderProcessor {
        public double processItem(Orderable item) {
            if (item.isAvailable()) {
                return item.getPrice();
            }
            return 0.0;
        }
    }
}
