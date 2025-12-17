package com.foodordering.service;

import com.foodordering.model.PaymentMethod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void processRejectsZeroAmount() {
        PaymentService service = new PaymentService();
        assertFalse(service.process(0, PaymentMethod.CASH));
    }

    @Test
    void processAcceptsPositiveAmount() {
        PaymentService service = new PaymentService();
        assertTrue(service.process(50, PaymentMethod.CARD));
    }
}
