package com.foodordering.payment;

import com.foodordering.model.PaymentMethod;
import com.foodordering.service.PaymentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentStrategyTest {

    @Test
    void cashPaymentAcceptsPositiveAmount() {
        PaymentService service = new PaymentService();
        assertTrue(service.process(50, PaymentMethod.CASH));
    }

    @Test
    void cardPaymentRequiresCardNumberAndPositiveAmount() {
        CardPayment card = new CardPayment("1234");
        assertTrue(card.pay(20));
        assertFalse(card.pay(0));
    }

    @Test
    void walletPaymentDeductsBalance() {
        WalletPayment wallet = new WalletPayment(30);
        assertTrue(wallet.pay(10));
        assertEquals(20, wallet.getBalance(), 0.0001);
        assertFalse(wallet.pay(25));
    }
}
