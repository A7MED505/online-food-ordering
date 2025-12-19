package com.foodordering.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OOP Principles: Inheritance Tests")
public class InheritanceTest {

    @Test
    @DisplayName("Customer inherits from User")
    void testCustomerInheritsFromUser() {
        Customer customer = new Customer("1", "test@example.com", "pass123", "Address", "123456");
        
        assertTrue(customer instanceof User, "Customer should be instance of User");
        assertTrue(customer instanceof Customer, "Customer should be instance of Customer");
    }

    @Test
    @DisplayName("Customer inherits User properties")
    void testCustomerInheritsUserProperties() {
        Customer customer = new Customer("1", "john@example.com", "hashed_pwd", "123 Main St", "555-0100");
        
        assertEquals("1", customer.getId());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals("hashed_pwd", customer.getHashedPassword());
    }

    @Test
    @DisplayName("Customer has additional properties beyond User")
    void testCustomerHasAdditionalProperties() {
        Customer customer = new Customer("1", "test@example.com", "pass", "New York", "555-1234");
        
        assertEquals("New York", customer.getAddress());
        assertEquals("555-1234", customer.getPhone());
    }

    @Test
    @DisplayName("Customer can override User password")
    void testCustomerCanChangePassword() {
        Customer customer = new Customer("1", "test@example.com", "old_pass", "Address", "Phone");
        
        customer.setHashedPassword("new_pass");
        assertEquals("new_pass", customer.getHashedPassword());
    }

    @Test
    @DisplayName("Customer equals method respects class hierarchy")
    void testCustomerEqualsRespectInheritance() {
        Customer customer1 = new Customer("1", "test@example.com", "pass", "Addr1", "111");
        Customer customer2 = new Customer("1", "other@example.com", "pass", "Addr2", "222");
        User user = new User("1", "test@example.com", "pass");
        
        assertEquals(customer1, customer2, "Customers with same ID should be equal");
        assertNotEquals(customer1, user, "Customer should not equal User (different classes) - demonstrates strict type checking");
        assertNotEquals(user, customer1, "User should not equal Customer (different classes) - demonstrates strict type checking");
    }

    @Test
    @DisplayName("Inheritance preserves validation rules from parent")
    void testInheritancePreservesValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(null, "test@example.com", "pass", "Address", "Phone");
        }, "Should throw when ID is null (inherited validation)");

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("1", null, "pass", "Address", "Phone");
        }, "Should throw when email is null (inherited validation)");
    }
}
