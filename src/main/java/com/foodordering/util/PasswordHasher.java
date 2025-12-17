package com.foodordering.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHasher {
    private PasswordHasher() {
    }

    public static String hash(String plain) {
        if (plain == null || plain.isBlank()) {
            throw new IllegalArgumentException("password must not be empty");
        }
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean verify(String plain, String hashed) {
        if (plain == null || plain.isBlank()) {
            return false;
        }
        if (hashed == null || hashed.isBlank()) {
            return false;
        }
        return BCrypt.checkpw(plain, hashed);
    }
}
