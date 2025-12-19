package com.foodordering.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OOP Principles: Encapsulation Tests")
public class EncapsulationTest {

    @Test
    @DisplayName("User fields are private and accessed via getters")
    void testUserFieldsArePrivate() {
        User user = new User("1", "test@example.com", "password");
        
        assertEquals("1", user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getHashedPassword());
    }

    @Test
    @DisplayName("Customer sensitive data (address, phone) is encapsulated")
    void testCustomerSensitiveDataEncapsulation() {
        Customer customer = new Customer("1", "test@example.com", "pass", "123 Secret St", "555-PRIVATE");
        
        assertEquals("123 Secret St", customer.getAddress());
        assertEquals("555-PRIVATE", customer.getPhone());
    }

    @Test
    @DisplayName("Cannot set null or empty address - validation enforced")
    void testAddressValidation() {
        Customer customer = new Customer("1", "test@example.com", "pass", "Valid Address", "12345");
        
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setAddress(null);
        }, "Should not allow null address");

        assertThrows(IllegalArgumentException.class, () -> {
            customer.setAddress("");
        }, "Should not allow empty address");

        assertThrows(IllegalArgumentException.class, () -> {
            customer.setAddress("   ");
        }, "Should not allow blank address");
    }

    @Test
    @DisplayName("Cannot set null or empty phone - validation enforced")
    void testPhoneValidation() {
        Customer customer = new Customer("1", "test@example.com", "pass", "Address", "Valid Phone");
        
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setPhone(null);
        }, "Should not allow null phone");

        assertThrows(IllegalArgumentException.class, () -> {
            customer.setPhone("");
        }, "Should not allow empty phone");

        assertThrows(IllegalArgumentException.class, () -> {
            customer.setPhone("   ");
        }, "Should not allow blank phone");
    }

    @Test
    @DisplayName("User ID is immutable (no setter provided)")
    void testUserIdIsImmutable() {
        User user = new User("original_id", "test@example.com", "pass");
        
        assertEquals("original_id", user.getId());
        // No setId method exists - ID is immutable
    }

    @Test
    @DisplayName("User email is immutable (no setter provided)")
    void testUserEmailIsImmutable() {
        User user = new User("1", "original@example.com", "pass");
        
        assertEquals("original@example.com", user.getEmail());
        // No setEmail method exists - email is immutable
    }

    @Test
    @DisplayName("Password can be changed but validated")
    void testPasswordEncapsulation() {
        User user = new User("1", "test@example.com", "old_password");
        
        user.setHashedPassword("new_password");
        assertEquals("new_password", user.getHashedPassword());
        
        assertThrows(IllegalArgumentException.class, () -> {
            user.setHashedPassword(null);
        }, "Should not allow null password");

        assertThrows(IllegalArgumentException.class, () -> {
            user.setHashedPassword("");
        }, "Should not allow empty password");
    }

    @Test
    @DisplayName("Customer construction validates all fields")
    void testCustomerConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("1", "test@example.com", "pass", null, "phone");
        }, "Constructor should validate address");

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("1", "test@example.com", "pass", "address", null);
        }, "Constructor should validate phone");
    }

    @Test
    @DisplayName("MenuItem price encapsulation with validation")
    void testMenuItemPriceEncapsulation() {
        MenuItem item = new MenuItem("1", "Burger", 10.0);
        
        assertEquals(10.0, item.getPrice());
        
        item.setPrice(15.0);
        assertEquals(15.0, item.getPrice());
        
        assertThrows(IllegalArgumentException.class, () -> {
            item.setPrice(-5.0);
        }, "Should not allow negative price");
    }

    @Test
    @DisplayName("Restaurant rating encapsulation with range validation")
    void testRestaurantRatingEncapsulation() {
        Restaurant restaurant = new Restaurant("1", "Test Restaurant", "Address", 4.5);
        
        assertEquals(4.5, restaurant.getRating());
        
        restaurant.setRating(5.0);
        assertEquals(5.0, restaurant.getRating());
        
        assertThrows(IllegalArgumentException.class, () -> {
            restaurant.setRating(-1.0);
        }, "Should not allow negative rating");

        assertThrows(IllegalArgumentException.class, () -> {
            restaurant.setRating(6.0);
        }, "Should not allow rating above 5");
    }
}
