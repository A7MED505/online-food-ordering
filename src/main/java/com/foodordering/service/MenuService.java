package com.foodordering.service;

import com.foodordering.model.MenuItem;
import com.foodordering.repository.MenuItemRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MenuService {
    private final MenuItemRepository repository;

    public MenuService(MenuItemRepository repository) {
        this.repository = repository;
    }

    public MenuItem addItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        try {
            repository.save(item);
            return item;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add menu item", e);
        }
    }

    public List<MenuItem> listItems() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list menu items", e);
        }
    }

    public Optional<MenuItem> findByName(String name) {
        if (name == null || name.isBlank()) {
            return Optional.empty();
        }
        try {
            return repository.findByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find menu item", e);
        }
    }
}
