package com.foodordering.util;

import com.foodordering.model.MenuItem;
import com.foodordering.repository.MenuItemRepository;

import java.util.Optional;
import java.util.UUID;

public class DataInitializer {

    public static void initializeMenuItems(MenuItemRepository menuItemRepository) {
        try {
            Object[][] seed = new Object[][]{
                {"Pizza Margherita", 25.0},
                {"Pizza Pepperoni", 28.0},
                {"Hamburger", 15.0},
                {"Cheeseburger", 16.0},
                {"Fried Chicken", 18.0},
                {"Grilled Chicken Breast", 22.0},
                {"Caesar Salad", 12.0},
                {"Greek Salad", 14.0},
                {"Spaghetti Carbonara", 20.0},
                {"Lasagna", 21.0},
                {"Falafel Wrap", 11.0},
                {"Shawarma", 13.0},
                {"Fish and Chips", 19.0},
                {"Grilled Salmon", 35.0},
                {"Beef Steak", 40.0},
                {"Soda", 5.0},
                {"Iced Tea", 4.0},
                {"Fresh Orange Juice", 6.0},
                {"Coffee", 3.5},
                {"Chocolate Cake", 8.0},
                {"Cheesecake", 9.0},
                {"Tiramisu", 10.0},
                {"Ice Cream", 6.0}
            };

            for (Object[] row : seed) {
                String name = (String) row[0];
                double price = (Double) row[1];

                Optional<MenuItem> existing = menuItemRepository.findByName(name);
                if (existing.isPresent()) {
                    MenuItem current = existing.get();
                    if (Double.compare(current.getPrice(), price) != 0) {
                        MenuItem updated = new MenuItem(current.getId(), current.getName(), price);
                        menuItemRepository.update(updated);
                    }
                } else {
                    MenuItem created = new MenuItem(UUID.randomUUID().toString(), name, price);
                    menuItemRepository.save(created);
                }
            }

            System.out.println("Menu items initialized successfully!");
        } catch (Exception e) {
            System.err.println("Error initializing menu items: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
