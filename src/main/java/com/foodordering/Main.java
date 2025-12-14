package com.foodordering;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main entry point for the Online Food Ordering System.
 * This class initializes the JavaFX application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Temporary welcome screen - will be replaced with login view
        Label welcomeLabel = new Label("Welcome to Online Food Ordering System");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        StackPane root = new StackPane();
        root.getChildren().add(welcomeLabel);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Food Ordering System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
