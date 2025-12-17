package com.foodordering.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {

    @Test
    void hashAndVerify() {
        String hashed = PasswordHasher.hash("secret");
        assertTrue(PasswordHasher.verify("secret", hashed));
        assertFalse(PasswordHasher.verify("wrong", hashed));
    }
}
