package com.foodordering.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OOP Principles: Polymorphism Tests")
public class PolymorphismTest {

    @Test
    @DisplayName("User reference can hold Customer object")
    void testPolymorphicReference() {
        User user = new Customer("1", "test@example.com", "pass", "Address", "Phone");
        
        assertNotNull(user);
        assertTrue(user instanceof Customer);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    @DisplayName("List of Users can contain mixed User and Customer objects")
    void testPolymorphicCollection() {
        List<User> users = new ArrayList<>();
        users.add(new User("1", "user1@example.com", "pass1"));
        users.add(new Customer("2", "customer@example.com", "pass2", "Address", "Phone"));
        users.add(new User("3", "user3@example.com", "pass3"));
        
        assertEquals(3, users.size());
        assertTrue(users.get(0) instanceof User);
        assertTrue(users.get(1) instanceof Customer);
        assertTrue(users.get(2) instanceof User);
    }

    @Test
    @DisplayName("Polymorphic method calls work with Customer as User")
    void testPolymorphicMethodCalls() {
        User user = new Customer("1", "test@example.com", "pass", "Address", "Phone");
        
        user.setHashedPassword("new_password");
        assertEquals("new_password", user.getHashedPassword());
    }

    @Test
    @DisplayName("Type checking with instanceof for specific behavior")
    void testInstanceofTypeChecking() {
        User user1 = new User("1", "user@example.com", "pass");
        User user2 = new Customer("2", "customer@example.com", "pass", "123 Main St", "555-0123");
        
        if (user1 instanceof Customer) {
            fail("Regular User should not be Customer");
        }
        
        if (user2 instanceof Customer) {
            Customer customer = (Customer) user2;
            assertEquals("123 Main St", customer.getAddress());
        } else {
            fail("Customer should be instance of Customer");
        }
    }

    @Test
    @DisplayName("Polymorphic equality demonstrates strict type checking")
    void testPolymorphicEquality() {
        User user = new User("1", "test@example.com", "pass");
        Customer customer = new Customer("1", "other@example.com", "pass", "Address", "Phone");
        User customerAsUser = customer;
        
        assertNotEquals(user, customer, "User and Customer are different classes even with same ID");
        assertEquals(customer, customerAsUser, "Customer reference types should be equal (same object)");
        assertNotEquals(user, customerAsUser, "User doesn't equal Customer even through User reference");
        
        assertTrue(user instanceof User);
        assertTrue(customer instanceof User);
        assertTrue(customer instanceof Customer);
    }

    @Test
    @DisplayName("Downcasting to access Customer-specific methods")
    void testDowncasting() {
        User user = new Customer("1", "test@example.com", "pass", "Original Address", "555-0000");
        
        assertTrue(user instanceof Customer);
        Customer customer = (Customer) user;
        
        assertEquals("Original Address", customer.getAddress());
        customer.setAddress("New Address");
        assertEquals("New Address", customer.getAddress());
    }

    @Test
    @DisplayName("Invalid downcast throws exception")
    void testInvalidDowncast() {
        User user = new User("1", "test@example.com", "pass");
        
        assertFalse(user instanceof Customer);
        assertThrows(ClassCastException.class, () -> {
            Customer customer = (Customer) user;
        }, "Downcasting User to Customer should fail");
    }

    @Test
    @DisplayName("Processing mixed collections polymorphically")
    void testMixedCollectionProcessing() {
        List<User> users = new ArrayList<>();
        users.add(new User("1", "user@example.com", "pass"));
        users.add(new Customer("2", "customer1@example.com", "pass", "Addr1", "111"));
        users.add(new Customer("3", "customer2@example.com", "pass", "Addr2", "222"));
        
        int customerCount = 0;
        for (User user : users) {
            if (user instanceof Customer) {
                customerCount++;
            }
        }
        
        assertEquals(2, customerCount, "Should count 2 customers in mixed list");
    }

    @Test
    @DisplayName("MenuItem collection demonstrates polymorphic behavior")
    void testMenuItemPolymorphism() {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("1", "Burger", 10.0));
        items.add(new MenuItem("2", "Pizza", 15.0));
        items.add(new MenuItem("3", "Salad", 8.0));
        
        double totalPrice = 0.0;
        for (MenuItem item : items) {
            totalPrice += item.getPrice();
        }
        
        assertEquals(33.0, totalPrice, 0.01, "Polymorphic iteration should calculate total");
    }

    @Test
    @DisplayName("PaymentMethod enum demonstrates type safety")
    void testPaymentMethodPolymorphism() {
        PaymentMethod method1 = PaymentMethod.CASH;
        PaymentMethod method2 = PaymentMethod.CARD;
        PaymentMethod method3 = PaymentMethod.WALLET;
        
        assertNotNull(method1);
        assertNotNull(method2);
        assertNotNull(method3);
        assertNotEquals(method1, method2);
        assertEquals("CASH", method1.name());
        assertEquals("CARD", method2.name());
    }
}
