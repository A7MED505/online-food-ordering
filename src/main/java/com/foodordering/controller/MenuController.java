package com.foodordering.controller;

import com.foodordering.model.*;
import com.foodordering.payment.CardPayment;
import com.foodordering.payment.CashPayment;
import com.foodordering.payment.WalletPayment;
import com.foodordering.service.MenuService;
import com.foodordering.service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

import java.util.List;
import java.util.Optional;

public class MenuController {
    private MenuService menuService;
    private OrderService orderService;
    private Cart currentCart;
    private Customer currentCustomer;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private TableView<com.foodordering.model.MenuItem> menuTable;
    private TableView<OrderItem> cartTable;
    private Label subtotalLabel;
    private Label taxLabel;
    private Label discountLabel;
    private Label totalLabel;
    private ComboBox<PaymentMethod> paymentMethodCombo;
    private Spinner<Integer> quantitySpinner;

    public MenuController(MenuService menuService, OrderService orderService, Customer customer) {
        this.menuService = menuService;
        this.orderService = orderService;
        this.currentCustomer = customer;
        this.currentCart = new Cart();
    }

    public Scene createScene(Stage stage) {
        BorderPane root = new BorderPane();

        Label titleLabel = new Label("Food ordering - pick your favorites");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox topBox = new VBox(titleLabel);
        topBox.setPadding(new Insets(10));
        root.setTop(topBox);

        VBox leftBox = createMenuPanel();
        root.setLeft(leftBox);

        VBox centerBox = createCartPanel();
        root.setCenter(centerBox);

        VBox rightBox = createSummaryPanel(stage);
        root.setRight(rightBox);

        return new Scene(root, 1200, 700);
    }

    private VBox createMenuPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1;");

        Label label = new Label("📋 Menu");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        menuTable = new TableView<>();
        menuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<com.foodordering.model.MenuItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().getName()));

        TableColumn<com.foodordering.model.MenuItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(param -> new javafx.beans.property.SimpleDoubleProperty(param.getValue().getPrice()).asObject());

        ObservableList<TableColumn<com.foodordering.model.MenuItem, ?>> menuColumns = menuTable.getColumns();
        menuColumns.clear();
        menuColumns.add(nameCol);
        menuColumns.add(priceCol);

        try {
            List<com.foodordering.model.MenuItem> items = menuService.listItems();
            menuTable.setItems(FXCollections.observableArrayList(items));
        } catch (Exception e) {
            showError("Failed to load menu: " + e.getMessage());
        }

        HBox addBox = new HBox(10);
        addBox.setPadding(new Insets(5));

        Label qtyLabel = new Label("Qty:");
        quantitySpinner = new Spinner<>(1, 100, 1);
        quantitySpinner.setPrefWidth(70);

        Button addBtn = new Button("➕ Add to cart");
        addBtn.setStyle("-fx-padding: 8px; -fx-font-size: 12px;");
        addBtn.setOnAction(e -> addToCart());

        addBox.getChildren().addAll(qtyLabel, quantitySpinner, addBtn);

        panel.getChildren().addAll(label, menuTable, addBox);
        return panel;
    }

    private VBox createCartPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1;");

        Label label = new Label("🛒 Your cart");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        cartTable = new TableView<>();
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<OrderItem, String> itemCol = new TableColumn<>("Item");
        itemCol.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().getItem().getName()));

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(param -> new javafx.beans.property.SimpleIntegerProperty(param.getValue().getQuantity()).asObject());

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Line total");
        priceCol.setCellValueFactory(param -> new javafx.beans.property.SimpleDoubleProperty(param.getValue().lineTotal()).asObject());

        ObservableList<TableColumn<OrderItem, ?>> cartColumns = cartTable.getColumns();
        cartColumns.clear();
        cartColumns.add(itemCol);
        cartColumns.add(qtyCol);
        cartColumns.add(priceCol);

        Button removeBtn = new Button("❌ Remove");
        removeBtn.setStyle("-fx-padding: 8px; -fx-font-size: 12px;");
        removeBtn.setOnAction(e -> removeFromCart());

        Button clearBtn = new Button("🗑️ Clear cart");
        clearBtn.setStyle("-fx-padding: 8px; -fx-font-size: 12px;");
        clearBtn.setOnAction(e -> clearCart());

        HBox btnBox = new HBox(10);
        btnBox.getChildren().addAll(removeBtn, clearBtn);

        panel.getChildren().addAll(label, cartTable, btnBox);
        return panel;
    }

    private VBox createSummaryPanel(Stage stage) {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1;");

        Label label = new Label("📊 Order summary");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox subtotalBox = new HBox(10);
        subtotalBox.getChildren().addAll(new Label("Subtotal:"), subtotalLabel = new Label(formatMoney(0)));

        HBox taxBox = new HBox(10);
        taxBox.getChildren().addAll(new Label("Tax (10%):"), taxLabel = new Label(formatMoney(0)));

        HBox discountBox = new HBox(10);
        Label discountLbl = new Label("Discount:");
        Spinner<Double> discountSpinner = new Spinner<>(0, 1000, 0, 5);
        discountSpinner.setPrefWidth(100);
        discountSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updateSummary());
        discountBox.getChildren().addAll(discountLbl, discountSpinner);
        discountBox.getChildren().add(discountLabel = new Label(formatMoney(0)));

        HBox totalBox = new HBox(10);
        totalLabel = new Label(formatMoney(0));
        totalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #d9534f;");
        totalBox.getChildren().addAll(new Label("Total:"), totalLabel);

        HBox paymentBox = new HBox(10);
        Label paymentLabel = new Label("Payment method:");
        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.setItems(FXCollections.observableArrayList(PaymentMethod.values()));
        paymentMethodCombo.setValue(PaymentMethod.CASH);
        paymentBox.getChildren().addAll(paymentLabel, paymentMethodCombo);

        Button checkoutBtn = new Button("✅ Place order");
        checkoutBtn.setStyle("-fx-padding: 10px; -fx-font-size: 12px; -fx-background-color: #5cb85c; -fx-text-fill: white;");
        checkoutBtn.setPrefWidth(300);
        checkoutBtn.setOnAction(e -> checkout(stage, discountSpinner.getValue()));

        Separator separator = new Separator();

        panel.getChildren().addAll(
                label,
                new Separator(),
                subtotalBox,
                taxBox,
                discountBox,
                separator,
                totalBox,
                new Separator(),
                paymentBox,
                checkoutBtn
        );

        return panel;
    }

    private void addToCart() {
        com.foodordering.model.MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Select an item first");
            return;
        }

        int quantity = quantitySpinner.getValue();
        currentCart.addItem(selected, quantity);
        updateCartTable();
        updateSummary();
    }

    private void removeFromCart() {
        OrderItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Select a cart item to remove");
            return;
        }

        ObservableList<OrderItem> items = cartTable.getItems();
        items.remove(selected);
        updateSummary();
    }

    private void clearCart() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Clear the cart?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            cartTable.getItems().clear();
            currentCart = new Cart();
            updateSummary();
        }
    }

    private void updateCartTable() {
        cartTable.setItems(FXCollections.observableArrayList(currentCart.getItems()));
    }

    private void updateSummary() {
        double subtotal = currentCart.subtotal();
        double tax = subtotal * 0.10;
        double discount = 0;

        ObservableList<javafx.scene.Node> children = ((VBox) ((BorderPane) cartTable.getScene().getRoot()).getRight()).getChildren();
        for (javafx.scene.Node child : children) {
            if (child instanceof HBox) {
                HBox hbox = (HBox) child;
                for (javafx.scene.Node node : hbox.getChildren()) {
                    if (node instanceof Spinner) {
                        Spinner<?> spinner = (Spinner<?>) node;
                        if (spinner.getValue() instanceof Double) {
                            discount = (Double) spinner.getValue();
                        }
                    }
                }
            }
        }

        double total = subtotal + tax - discount;

        subtotalLabel.setText(formatMoney(subtotal));
        taxLabel.setText(formatMoney(tax));
        discountLabel.setText(formatMoney(discount));
        totalLabel.setText(formatMoney(Math.max(total, 0)));
    }

    private void checkout(Stage stage, double discount) {
        if (currentCart.getItems().isEmpty()) {
            showWarning("Cart is empty. Add items before paying");
            return;
        }

        try {
            Order order = currentCart.toOrder(currentCustomer, 0.10, discount);
            PaymentMethod method = paymentMethodCombo.getValue();

            if (processPayment(method, order.getTotal())) {
                orderService.markPaid(order, method);
                showSuccess("Order submitted. ID: " + order.getId());
                currentCart = new Cart();
                cartTable.getItems().clear();
                updateSummary();
            } else {
                showError("Payment failed. Please check payment info");
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private boolean processPayment(PaymentMethod method, double amount) {
        return switch (method) {
            case CASH -> new CashPayment().pay(amount);
            case CARD -> new CardPayment("1234-5678-9000-0000").pay(amount);
            case WALLET -> new WalletPayment(5000).pay(amount);
        };
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private String formatMoney(double value) {
        return currencyFormat.format(value);
    }
}
