package com.foodordering.model;

import java.util.Objects;

public class MenuItem {
    private final String id;
    private String name;
    private double price;

    public MenuItem(String id, String name, double price) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price must be non-negative");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("price must be non-negative");
        }
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return id.equals(menuItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
