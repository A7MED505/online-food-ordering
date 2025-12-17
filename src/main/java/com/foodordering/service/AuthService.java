package com.foodordering.service;

import com.foodordering.model.User;
import com.foodordering.repository.UserRepository;
import com.foodordering.util.PasswordHasher;
import java.sql.SQLException;
import java.util.UUID;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String email, String plainPassword) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email must not be empty");
        }
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("password must not be empty");
        }
        try {
            if (userRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("email already exists");
            }
            String hashed = PasswordHasher.hash(plainPassword);
            User user = new User(UUID.randomUUID().toString(), email, hashed);
            userRepository.save(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register user", e);
        }
    }

    public boolean login(String email, String plainPassword) {
        try {
            return userRepository.findByEmail(email)
                    .map(u -> PasswordHasher.verify(plainPassword, u.getHashedPassword()))
                    .orElse(false);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to login", e);
        }
    }
}
