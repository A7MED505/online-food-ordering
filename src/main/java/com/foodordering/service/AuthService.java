package com.foodordering.service;

import com.foodordering.model.User;
import com.foodordering.util.PasswordHasher;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuthService {
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();

    public User register(String email, String plainPassword) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email must not be empty");
        }
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("password must not be empty");
        }
        if (usersByEmail.containsKey(email)) {
            throw new IllegalArgumentException("email already exists");
        }
        String hashed = PasswordHasher.hash(plainPassword);
        User user = new User(UUID.randomUUID().toString(), email, hashed);
        usersByEmail.put(email, user);
        return user;
    }

    public boolean login(String email, String plainPassword) {
        User user = usersByEmail.get(email);
        if (user == null) {
            return false;
        }
        return PasswordHasher.verify(plainPassword, user.getHashedPassword());
    }
}
