package com.foodordering.model;

import java.util.Objects;

public class User {
    private final String id;
    private final String email;
    private String hashedPassword;

    public User(String id, String email, String hashedPassword) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email must not be empty");
        }
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("hashedPassword must not be empty");
        }
        this.id = id;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("hashedPassword must not be empty");
        }
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
