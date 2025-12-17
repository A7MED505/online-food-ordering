package com.foodordering.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Restaurant {
    private final String id;
    private String name;
    private String address;
    private double rating; // 0-5
    private final List<MenuItem> menuItems = new ArrayList<>();

    public Restaurant(String id, String name, String address, double rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be empty");
        }
        setName(name);
        setAddress(address);
        setRating(rating);
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address must not be empty");
        }
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
        this.rating = rating;
    }

    public void addMenuItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("menu item must not be null");
        }
        menuItems.add(item);
    }

    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
