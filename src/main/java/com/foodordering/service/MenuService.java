package com.foodordering.service;

import com.foodordering.model.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MenuService {
    private final List<MenuItem> items = new ArrayList<>();

    public MenuItem addItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("item must not be null");
        }
        items.add(item);
        return item;
    }

    public List<MenuItem> listItems() {
        return Collections.unmodifiableList(items);
    }

    public Optional<MenuItem> findByName(String name) {
        if (name == null || name.isBlank()) {
            return Optional.empty();
        }
        return items.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
