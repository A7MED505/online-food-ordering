package com.foodordering.service;

import com.foodordering.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void registerAndLoginSuccess() {
        AuthService service = new AuthService();
        User user = service.register("test@example.com", "secret");
        assertNotNull(user.getId());
        assertTrue(service.login("test@example.com", "secret"));
    }

    @Test
    void duplicateEmailThrows() {
        AuthService service = new AuthService();
        service.register("dup@example.com", "secret");
        assertThrows(IllegalArgumentException.class, () -> service.register("dup@example.com", "secret2"));
    }

    @Test
    void wrongPasswordFails() {
        AuthService service = new AuthService();
        service.register("user@example.com", "secret");
        assertFalse(service.login("user@example.com", "wrong"));
    }
}
