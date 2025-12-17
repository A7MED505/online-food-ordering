package com.foodordering.service;

import com.foodordering.model.User;
import com.foodordering.repository.UserRepository;
import com.foodordering.util.DbTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private UserRepository userRepository;
    private AuthService service;

    @BeforeEach
    void setup() throws Exception {
        DbTestUtil.clearAll();
        userRepository = new UserRepository();
        service = new AuthService(userRepository);
    }

    @Test
    void registerAndLoginSuccess() {
        User user = service.register("test@example.com", "secret");
        assertNotNull(user.getId());
        assertTrue(service.login("test@example.com", "secret"));
    }

    @Test
    void duplicateEmailThrows() {
        service.register("dup@example.com", "secret");
        assertThrows(IllegalArgumentException.class, () -> service.register("dup@example.com", "secret2"));
    }

    @Test
    void wrongPasswordFails() {
        service.register("user@example.com", "secret");
        assertFalse(service.login("user@example.com", "wrong"));
    }
}
