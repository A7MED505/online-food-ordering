package com.foodordering;

import com.foodordering.controller.MenuController;
import com.foodordering.model.Customer;
import com.foodordering.repository.MenuItemRepository;
import com.foodordering.repository.OrderItemRepository;
import com.foodordering.repository.OrderRepository;
import com.foodordering.service.MenuService;
import com.foodordering.service.OrderService;
import com.foodordering.util.DataInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.UUID;

/**
 * Main entry point for the Online Food Ordering System.
 * This class initializes the JavaFX application with services and database.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize repositories
            MenuItemRepository menuItemRepository = new MenuItemRepository();
            OrderRepository orderRepository = new OrderRepository();
            OrderItemRepository orderItemRepository = new OrderItemRepository();

            DataInitializer.initializeMenuItems(menuItemRepository);

            // Initialize services
            MenuService menuService = new MenuService(menuItemRepository);
            OrderService orderService = new OrderService(0.10, orderRepository, orderItemRepository);

            // Create test customer (in real app, this would come from login)
            Customer customer = new Customer(
                    UUID.randomUUID().toString(),
                    "customer@example.com",
                    "hashed_password",
                    "Riyadh, Al Malaz",
                    "0501234567"
            );

            // Create menu controller and scene
            MenuController menuController = new MenuController(menuService, orderService, customer);
            primaryStage.setScene(menuController.createScene(primaryStage));

            primaryStage.setTitle("Food Ordering System");
            primaryStage.setWidth(1200);
            primaryStage.setHeight(700);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
