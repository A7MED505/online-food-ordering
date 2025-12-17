package com.foodordering.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void createCustomerSuccess() {
        Customer customer = new Customer("c1", "c@test.com", "hash", "Address 1", "12345");
        assertEquals("c1", customer.getId());
        assertEquals("Address 1", customer.getAddress());
        assertEquals("12345", customer.getPhone());
    }

    @Test
    void emptyAddressThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("c1", "c@test.com", "hash", "", "123"));
    }

    @Test
    void emptyPhoneThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("c1", "c@test.com", "hash", "Addr", ""));
    }
}
